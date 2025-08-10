package morimensmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.RelicType;
import basemod.interfaces.*;

import me.antileaf.signature.utils.SignatureHelper;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.chaos.QueensSword;
import morimensmod.characters.*;
import morimensmod.config.*;
import morimensmod.dynamicvariables.AbstractEasyDynamicVariable;
import morimensmod.glowinfos.AbstractGlowInfo;
import morimensmod.icons.AbstractIcon;
import morimensmod.misc.TopPanelDeathResistanceUI;
import morimensmod.misc.TopPanelTurnUI;
import morimensmod.patches.hooks.PassiveCardPatch;
import morimensmod.potions.AbstractEasyPotion;
import morimensmod.powers.AbstractPersistentPower;
import morimensmod.powers.ImmunePower;
import morimensmod.relics.AbstractEasyRelic;
import morimensmod.savables.*;
import morimensmod.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.Patcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import com.google.gson.Gson;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.scannotation.AnnotationDB;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.*;
import static morimensmod.util.General.*;
import static morimensmod.util.Wiz.*;

@SpireInitializer
public class MorimensMod implements
        AddAudioSubscriber,
        EditCardsSubscriber,
        EditCharactersSubscriber,
        EditKeywordsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        OnCardUseSubscriber,
        // OnPlayerTurnStartPostDrawSubscriber,
        OnPlayerTurnStartSubscriber,
        OnStartBattleSubscriber,
        PostBattleSubscriber,
        PostInitializeSubscriber,
        PreMonsterTurnSubscriber {

    private static final Logger logger = LogManager.getLogger(MorimensMod.class);

    public static ModInfo info;
    public static String modID;

    static {
        loadModInfo();
    }

    public static final String makeID(String idText) {
        return modID + ":" + idText;
    }

    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");
    private static final String ATTACK_L_ART = makeImagePath("1024/attack.png");
    private static final String SKILL_L_ART = makeImagePath("1024/skill.png");
    private static final String POWER_L_ART = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");

    // public static Settings.GameLanguage[] SupportedLanguages = {
    //         Settings.GameLanguage.ENG,
    //         Settings.GameLanguage.ZHT,
    //         Settings.GameLanguage.ZHS,
    // };

    private String getLangString() {
        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT)
            return Settings.language.name().toLowerCase();
        // for (Settings.GameLanguage lang : SupportedLanguages)
        // if (lang.equals(Settings.language))
        // return Settings.language.name().toLowerCase();
        return Settings.GameLanguage.ENG.name().toLowerCase();
    }

    /**
     * This determines the mod's ID based on information stored by ModTheSpire.
     */
    private static void loadModInfo() {
        Optional<ModInfo> infos = Arrays.stream(Loader.MODINFOS).filter((modInfo) -> {
            AnnotationDB annotationDB = Patcher.annotationDBMap.get(modInfo.jarURL);
            if (annotationDB == null)
                return false;
            Set<String> initializers = annotationDB.getAnnotationIndex().getOrDefault(SpireInitializer.class.getName(),
                    Collections.emptySet());
            return initializers.contains(MorimensMod.class.getName());
        }).findFirst();
        if (infos.isPresent()) {
            info = infos.get();
            modID = info.ID;

            logger.info("ModID: " + modID);
            logger.info("Name: " + info.Name);
            logger.info("Version: " + info.ModVersion);
            logger.info("Authors: " + arrToString(info.Authors));
            logger.info("Description: " + info.Description);
        } else {
            throw new RuntimeException("Failed to determine mod info/ID based on initializer.");
        }
    }

    public MorimensMod() {
        BaseMod.addColor(AWAKENER_COLOR, Color.WHITE,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(CHAOS_COLOR, ModSettings.CHOAS_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(AEQUOR_COLOR, ModSettings.AEQUOR_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(CARO_COLOR, ModSettings.CARO_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(ULTRA_COLOR, ModSettings.ULTRA_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(WHEEL_OF_DESTINY_COLOR, ModSettings.WHEEL_OF_DESTINY_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(BUFF_COLOR, ModSettings.BUFF_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(SYMPTOM_COLOR, ModSettings.SYMPTOM_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(STATUS_COLOR, ModSettings.STATUS_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(POSSE_COLOR, ModSettings.POSSE_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.addColor(DERIVATIVE_COLOR, ModSettings.DERIVATIVE_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        BaseMod.subscribe(this);
    }

    public static final String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static final String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static final String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static final String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static final String makeCharacterPath(String resourcePath) {
        return modID + "Resources/images/char/" + resourcePath;
    }

    public static final String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static final String makeUIPath(String resourcePath) {
        return modID + "Resources/images/ui/" + resourcePath;
    }

    public static final String makeVFXPath(String resourcePath) {
        return modID + "Resources/images/vfx/" + resourcePath;
    }

    public static final String makeIconPath(String resourcePath) {
        return modID + "Resources/images/icons/" + resourcePath;
    }

    public static final String makeMonsterPath(String resourcePath) {
        return modID + "Resources/images/monsters/" + resourcePath;
    }

    public static final String makeEventPath(String resourcePath) {
        return modID + "Resources/images/events/" + resourcePath;
    }

    public static final String makeRewardPath(String resourcePath) {
        return modID + "Resources/images/rewards/" + resourcePath;
    }

    public static final String makeBlightPath(String resourcePath) {
        return modID + "Resources/images/blights/" + resourcePath;
    }

    public static final String makeCrystalPath(String resourcePath) {
        return modID + "Resources/images/crystals/" + resourcePath;
    }

    public static void initialize() {
        new MorimensMod();
    }

    @Override
    public void receiveEditCharacters() {
        Ramona.register();
        Lotan.register();
        RamonaTimeworm.register();
        // new AutoAdd(modID)
        // .packageFilter(AbstractAwakener.class)
        // .any(AbstractAwakener.class, (info, awaker) -> awaker.register());

        new AutoAdd(modID)
                .packageFilter(AbstractEasyPotion.class)
                .any(AbstractEasyPotion.class, (info, potion) -> {
                    if (potion.pool == null)
                        BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor,
                                potion.ID);
                    else
                        BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor,
                                potion.ID, potion.pool);
                });

        new AutoAdd(modID)
                .packageFilter(AbstractIcon.class)
                .any(AbstractIcon.class, (info, icon) -> CustomIconHelper.addCustomIcon(icon.get()));
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyDynamicVariable.class)
                .any(DynamicVariable.class, (info, var) -> BaseMod.addDynamicVariable(var));
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();

        CardLib.initialize();
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class,
                modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Orbstrings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Stancestrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Monsterstrings.json");
        BaseMod.loadCustomStringsFile(EventStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Eventstrings.json");
        BaseMod.loadCustomStringsFile(BlightStrings.class,
                modID + "Resources/localization/" + getLangString() + "/Blightstrings.json");
    }

    @Override
    public void receiveAddAudio() {
        for (ProAudio a : ProAudio.values())
            BaseMod.addAudio(makeID(a.name()), makePath("audio/" + a.name().toLowerCase() + ".ogg"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/" + getLangString() + "/Keywordstrings.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json,
                com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void receivePostInitialize() {

        ModConfig.initialize();

        // This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TexLoader.getTexture(makeUIPath("badge.png"));
        // Set up the mod information displayed in the in-game mods menu.
        // The information used is taken from your pom.xml file.

        // If you want to set up a config panel, that will be done here.
        // You can find information about this on the BaseMod wiki page "Mod Config and
        // Panel".
        BaseMod.registerModBadge(badgeTexture, info.Name, arrToString(info.Authors), info.Description,
                new ConfigPanel());

        EventLib.register();

        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .any(AbstractEasyCard.class, (info, card) -> {
                    if (!Gdx.files.internal(card.getSignatureImgPath()).exists())
                        return;
                    logger.info("register & unlock signature:" + card.cardID);
                    SignatureHelper.register(card.cardID, new SignatureHelper.Info(
                            c -> ((AbstractEasyCard) c).getSignatureImgPath(),
                            c -> ((AbstractEasyCard) c).getSignaturePortraitImgPath(),
                            SignatureHelper.DEFAULT_STYLE));
                    SignatureHelper.unlock(card.cardID, true);
                    SignatureHelper.enable(card.cardID, true);
                });

        new AutoAdd(modID)
                .packageFilter(AbstractGlowInfo.class)
                .any(AbstractGlowInfo.class, (info, var) -> CardBorderGlowManager.addGlowInfo(var));

        new AutoAdd(modID)
                .packageFilter(AbstractPersistentPower.class)
                .any(AbstractPersistentPower.class, (info, var) -> {
                    PersistentPowerLib.addPower(var);
                });

        // BaseMod.addSaveField(PosseSelectUI.ID, PosseSelectUI.getUI());

        BaseMod.addSaveField(SavePersistentPowers.ID, new SavePersistentPowers());
        BaseMod.addSaveField(SaveAwakenerProperties.ID, new SaveAwakenerProperties());
        BaseMod.addSaveField(SaveAwakenerFloatProperties.ID, new SaveAwakenerFloatProperties());
        BaseMod.addSaveField(SaveAwakenerPosse.ID, new SaveAwakenerPosse());

        BaseMod.addTopPanelItem(new TopPanelDeathResistanceUI());
        BaseMod.addTopPanelItem(new TopPanelTurnUI());

        MonsterLib.initialize();
        MonsterLib.register();

        RewardLib.register();
    }

    public static ArrayList<AbstractCard> lastTurnCardsPlayed = new ArrayList<>();
    public static ArrayList<AbstractCard> thisTurnCardsPlayed = new ArrayList<>();

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        lastTurnCardsPlayed.clear();
        thisTurnCardsPlayed.clear();

        QueensSword.onBattleStart();
        AbstractAwakener.onBattleStart();
        if (p() instanceof AbstractAwakener)
            ((AbstractAwakener) p()).getExalt().onBattleStart();

        PassiveCardPatch.onBattleStartPreDraw();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        lastTurnCardsPlayed = thisTurnCardsPlayed;
        thisTurnCardsPlayed = new ArrayList<>();

        AbstractAwakener.onPlayerTurnStart(); // 每回合重設
        if (p() instanceof AbstractAwakener)
            ((AbstractAwakener) p()).getExalt().onPlayerTurnStart();
    }

    // @Override
    // public void receiveOnPlayerTurnStartPostDraw() {
    // AbstractAwakener.onPlayerTurnStartPostDraw(); // 每回合重設
    // }

    @Override
    public void receivePostBattle(AbstractRoom room) {
        AbstractAwakener.onPostBattle();
        AbstractEasyCard.onPostBattle();
        if (p() instanceof AbstractAwakener)
            ((AbstractAwakener) p()).getExalt().onPostBattle(room);
    }

    @Override
    public void receiveCardUsed(AbstractCard card) {
        thisTurnCardsPlayed.add(card);

        if (p() instanceof AbstractAwakener)
            ((AbstractAwakener) p()).getExalt().onCardUse(card);
    }

    @Override
    public boolean receivePreMonsterTurn(AbstractMonster monster) {
        if (monster.hasPower(ImmunePower.POWER_ID))
            return ImmunePower.onPreMonsterTurn(monster);
        return true;
    }
}
