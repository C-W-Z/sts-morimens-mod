package morimensmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils.EventType;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import me.antileaf.signature.utils.SignatureHelper;
import me.antileaf.signature.utils.internal.SignatureHelperInternal;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.cardvars.AbstractEasyDynamicVariable;
import morimensmod.cards.chaos.QueensSword;
import morimensmod.characters.AbstractAwakener;
import morimensmod.characters.Lotan;
import morimensmod.characters.Ramona;
import morimensmod.characters.RamonaTimeworm;
import morimensmod.config.ConfigPanel;
import morimensmod.config.ModSettings;
import morimensmod.events.Junction;
import morimensmod.glowinfos.AbstractGlowInfo;
import morimensmod.icons.AbstractIcon;
import morimensmod.misc.TopPanelDeathResistanceUI;
import morimensmod.misc.TopPanelTurnUI;
import morimensmod.potions.AbstractEasyPotion;
import morimensmod.powers.AbstractPersistentPower;
import morimensmod.relics.AbstractEasyRelic;
import morimensmod.savables.SaveAwakenerFloatProperties;
import morimensmod.savables.SaveAwakenerPosse;
import morimensmod.savables.SaveAwakenerProperties;
import morimensmod.savables.SavePersistentPowers;
import morimensmod.util.CardLib;
import morimensmod.util.MonsterLib;
import morimensmod.util.PersistentPowerLib;
import morimensmod.util.ProAudio;
import morimensmod.util.TexLoader;

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
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import static morimensmod.util.Wiz.*;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AEQUOR_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.BUFF_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CARO_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.DERIVATIVE_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.POSSE_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.STATUS_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.SYMPTOM_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.ULTRA_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.General.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scannotation.AnnotationDB;

@SpireInitializer
public class MorimensMod implements
        OnCardUseSubscriber,
        // OnPlayerTurnStartPostDrawSubscriber,
        OnPlayerTurnStartSubscriber,
        PostBattleSubscriber,
        OnStartBattleSubscriber,
        PostInitializeSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        AddAudioSubscriber {

    private static final Logger logger = LogManager.getLogger(MorimensMod.class);

    public static ModInfo info;
    public static String modID;
    static {
        loadModInfo();
    }

    public static final String makeID(String idText) {
        return modID + ":" + idText;
    }

    private static final String ATTACK_S_ART  = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART   = makeImagePath("512/skill.png");
    private static final String POWER_S_ART   = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY   = makeImagePath("512/text_energy.png");
    private static final String ATTACK_L_ART  = makeImagePath("1024/attack.png");
    private static final String SKILL_L_ART   = makeImagePath("1024/skill.png");
    private static final String POWER_L_ART   = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
            Settings.GameLanguage.ZHT,
    };

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

    public static void initialize() {
        new MorimensMod();
    }

    @Override
    public void receiveEditCharacters() {
        Ramona.register();
        Lotan.register();
        RamonaTimeworm.register();
        // new AutoAdd(modID)
        //         .packageFilter(AbstractAwakener.class)
        //         .any(AbstractAwakener.class, (info, awaker) -> awaker.register());

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

    @Override
    public void receivePostInitialize() {
        // This loads the image used as an icon in the in-game mods menu.
        Texture badgeTexture = TexLoader.getTexture(makeUIPath("badge.png"));
        // Set up the mod information displayed in the in-game mods menu.
        // The information used is taken from your pom.xml file.

        // If you want to set up a config panel, that will be done here.
        // You can find information about this on the BaseMod wiki page "Mod Config and
        // Panel".
        BaseMod.registerModBadge(badgeTexture, info.Name, arrToString(info.Authors), info.Description,
                new ConfigPanel());

        CardLib.initialize();

        BaseMod.addEvent(new AddEventParams.Builder(Junction.ID, Junction.class).eventType(EventType.NORMAL).create());

        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .any(AbstractEasyCard.class, (info, var) -> {
                    if (!SignatureHelperInternal.hasSignature(var))
                        return;
                    logger.info("unlock signature:" + var.cardID);
                    SignatureHelper.unlock(var.cardID, true);
                    SignatureHelper.enable(var.cardID, true);
                });

        new AutoAdd(modID)
                .packageFilter(AbstractGlowInfo.class)
                .any(AbstractGlowInfo.class, (info, var) -> CardBorderGlowManager.addGlowInfo(var));

        new AutoAdd(modID)
                .packageFilter(AbstractPersistentPower.class)
                .any(AbstractPersistentPower.class, (info, var) -> {
                    PersistentPowerLib.addPower(var);
                });

        BaseMod.addSaveField(SavePersistentPowers.ID, new SavePersistentPowers());
        BaseMod.addSaveField(SaveAwakenerProperties.ID, new SaveAwakenerProperties());
        BaseMod.addSaveField(SaveAwakenerFloatProperties.ID, new SaveAwakenerFloatProperties());
        BaseMod.addSaveField(SaveAwakenerPosse.ID, new SaveAwakenerPosse());

        BaseMod.addTopPanelItem(new TopPanelDeathResistanceUI());
        BaseMod.addTopPanelItem(new TopPanelTurnUI());

        MonsterLib.initialize();
        MonsterLib.register();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        QueensSword.onBattleStart();
        AbstractAwakener.onBattleStart();
        AbstractEasyCard.onBattleStart();
        if (p() instanceof AbstractAwakener)
            ((AbstractAwakener) p()).getExalt().onBattleStart();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
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
        if (p() instanceof AbstractAwakener)
            ((AbstractAwakener) p()).getExalt().onCardUse(card);
    }
}
