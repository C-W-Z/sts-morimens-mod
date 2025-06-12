package morimensmod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import me.antileaf.signature.utils.SignatureHelper;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.Defend;
import morimensmod.cards.Strike;
import morimensmod.cards.Ramona.QueensSword;
import morimensmod.cards.cardvars.AbstractEasyDynamicVariable;
import morimensmod.cards.democards.simple.DrawAndShiv;
import morimensmod.characters.Ramona;
import morimensmod.potions.AbstractEasyPotion;
import morimensmod.relics.AbstractEasyRelic;
import morimensmod.util.ProAudio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class MorimensMod implements
        OnStartBattleSubscriber,
        PostInitializeSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        AddAudioSubscriber {

    public static final Logger logger = LogManager.getLogger(MorimensMod.class);

    public static final String modID = "morimensmod";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(1, 1, 1, 1); // This should be changed eventually

    private static final String ATTACK_S_ART = makeImagePath("512/attack.png");
    private static final String SKILL_S_ART = makeImagePath("512/skill.png");
    private static final String POWER_S_ART = makeImagePath("512/power.png");
    private static final String CARD_ENERGY_S = makeImagePath("512/energy.png");
    private static final String TEXT_ENERGY = makeImagePath("512/text_energy.png");
    private static final String ATTACK_L_ART = makeImagePath("1024/attack.png");
    private static final String SKILL_L_ART = makeImagePath("1024/skill.png");
    private static final String POWER_L_ART = makeImagePath("1024/power.png");
    private static final String CARD_ENERGY_L = makeImagePath("1024/energy.png");
    private static final String CHARSELECT_BUTTON = makeCharacterPath("Ramona/button.png");
    private static final String CHARSELECT_PORTRAIT = makeCharacterPath("Ramona/charBG.png");

    public static Settings.GameLanguage[] SupportedLanguages = {
        Settings.GameLanguage.ZHT,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "zht";
    }

    public MorimensMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(Ramona.Enums.RAMONA_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCharacterPath(String resourcePath)
    {
        return modID + "Resources/images/char/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        MorimensMod thismod = new MorimensMod();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Ramona(Ramona.NAMES[1], Ramona.Enums.RAMONA),
            CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, Ramona.Enums.RAMONA);

        new AutoAdd(modID)
            .packageFilter(AbstractEasyPotion.class)
            .any(AbstractEasyPotion.class, (info, potion) -> {
                if (potion.pool == null)
                    BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor, potion.ID);
                else
                    BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor, potion.ID, potion.pool);
            });
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
            .any(DynamicVariable.class, (info, var) ->
                BaseMod.addDynamicVariable(var));
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, modID + "Resources/localization/" + getLangString() + "/Orbstrings.json");
        BaseMod.loadCustomStringsFile(StanceStrings.class, modID + "Resources/localization/" + getLangString() + "/Stancestrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");
    }

    @Override
    public void receiveAddAudio() {
        for (ProAudio a : ProAudio.values())
            BaseMod.addAudio(makeID(a.name()), makePath("audio/" + a.name().toLowerCase() + ".ogg"));
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/" + getLangString() + "/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receivePostInitialize() {
        SignatureHelper.unlock(Strike.ID, true);
        SignatureHelper.unlock(Defend.ID, true);
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom arg0) {
        QueensSword.attackTimesThisCombat = QueensSword.INIT_ATK_TIMES;  // 每場戰鬥重新設定為3
    }
}
