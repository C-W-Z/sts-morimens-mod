package morimensmod.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import morimensmod.actions.PosseAction;
import morimensmod.exalts.AbstractExalt;
import morimensmod.misc.PosseType;
import morimensmod.misc.SpriteSheetAnimation;
import morimensmod.posses.AbstractPosse;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;

import static morimensmod.MorimensMod.*;
import static morimensmod.util.Wiz.atb;

public abstract class AbstractAwakener extends CustomPlayer {

    public static int baseAliemusRegen = 0;
    public static int aliemusRegen = baseAliemusRegen;

    public static int baseKeyflareRegen = 60;
    public static int keyflareRegen = baseKeyflareRegen;

    public static final int NORMAL_ALIEMUS_LIMIT = 100;
    protected static int aliemus = 0;
    protected static int aliemusLimit = NORMAL_ALIEMUS_LIMIT; // 普通狂氣上限 狂氣爆發
    protected static int extremeAlimus = 2 * NORMAL_ALIEMUS_LIMIT; // 雙倍上限 超限爆發
    protected static boolean exalting = false;
    protected static int exaltedThisTurn = 0; // reset at Main Mod File
    public static final int NORMAL_MAX_EXALT_PER_TURN = 1;
    protected static int maxExaltPerTurn = NORMAL_MAX_EXALT_PER_TURN; // reset at Main Mod File
    protected AbstractExalt exalt;

    public static final int NORMAL_KEYFLARE_LIMIT = 1000;
    protected static int keyflare = 0;
    protected static int posseNeededKeyflare = NORMAL_KEYFLARE_LIMIT;
    protected static int maxKeyflare = 2 * NORMAL_KEYFLARE_LIMIT;
    protected static boolean possing = false;
    protected static int regularPossedThisTurn = 0; // reset at Main Mod File
    protected static int extraPossedThisTurn = 0; // reset at Main Mod File
    protected static int unlimitedPosseThisTurn = 0; // reset at Main Mod File
    public static final int MAX_REGULAR_POSSE_PER_TURN = 1;
    public static final int MAX_EXTRA_POSSE_PER_TURN = 1;
    protected static int possedThisBattle = 0; // reset at Main Mod File
    protected AbstractPosse posse;

    // percent
    public static int baseDamageAmplify;
    public static int baseBlockAmplify;
    public static int baseHealAmplify;
    public static int baseAliemusAmplify;
    public static int basePoisonAmplify;
    public static int baseCounterAmplify;

    public static final int ENERGY_PER_TURN = 5;

    public SpriteSheetAnimation anim = null;

    public AbstractAwakener(String name, PlayerClass setClass, String characterImgPath, final String CORPSE, AbstractExalt exalt, AbstractPosse posse) {
        super(name, setClass,
                new CustomEnergyOrb(orbTextures, makeCharacterPath("ChaosRealm/orb/vfx.png"), null),
                new AbstractAnimation() {
                    @Override
                    public Type type() {
                        return Type.NONE;
                    }
                });
        initializeClass(makeCharacterPath(characterImgPath),
                makeCharacterPath("shoulder.png"),
                makeCharacterPath("shoulder.png"),
                makeCharacterPath(CORPSE),
                getLoadout(),
                0F, -20F, 300F, 350F, // Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);

        aliemus = 0;
        keyflare = 0;
        this.exalt = exalt;
        this.posse = posse;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    private static final String[] orbTextures = {
            makeCharacterPath("ChaosRealm/orb/layer1.png"),
            makeCharacterPath("ChaosRealm/orb/layer2.png"),
            makeCharacterPath("ChaosRealm/orb/layer3.png"),
            makeCharacterPath("ChaosRealm/orb/layer4.png"),
            makeCharacterPath("ChaosRealm/orb/layer4.png"),
            makeCharacterPath("ChaosRealm/orb/layer6.png"),
            makeCharacterPath("ChaosRealm/orb/layer1d.png"),
            makeCharacterPath("ChaosRealm/orb/layer2d.png"),
            makeCharacterPath("ChaosRealm/orb/layer3d.png"),
            makeCharacterPath("ChaosRealm/orb/layer4d.png"),
            makeCharacterPath("ChaosRealm/orb/layer5d.png"),
    };

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE };
    }

    // called in Main Mod File
    public static void onBattleStart() {
        aliemusLimit = NORMAL_ALIEMUS_LIMIT;
        extremeAlimus = 2 * NORMAL_ALIEMUS_LIMIT;

        posseNeededKeyflare = NORMAL_KEYFLARE_LIMIT;
        maxKeyflare = 2 * NORMAL_KEYFLARE_LIMIT;
        possedThisBattle = 0;

        aliemusRegen = baseAliemusRegen;
        keyflareRegen = baseKeyflareRegen;

        baseDamageAmplify = 0;
        baseBlockAmplify = 0;
        baseHealAmplify = 0;
        baseAliemusAmplify = 0;
        basePoisonAmplify = 0;
        baseCounterAmplify = 0;
    }

    // called in Main Mod File
    public static void onPlayerTurnStartPostDraw() {
        exaltedThisTurn = 0;
        maxExaltPerTurn = NORMAL_MAX_EXALT_PER_TURN;

        regularPossedThisTurn = 0;
        extraPossedThisTurn = 0;
        unlimitedPosseThisTurn = 0;
    }

    public static int getAliemus() {
        return aliemus;
    }

    public static int changeAliemus(int amount) {
        aliemus += amount;
        if (aliemus > extremeAlimus)
            aliemus = extremeAlimus;
        else if (aliemus < 0)
            aliemus = 0;
        return aliemus;
    }

    public static boolean isExalting() {
        return exalting;
    }

    public static boolean enoughExaltCountThisTurn() {
        return exaltedThisTurn < maxExaltPerTurn;
    }

    public static boolean enoughAliemusForExalt() {
        return aliemus >= aliemusLimit;
    }

    public static boolean enoughAliemusForOverExalt() {
        return aliemus >= extremeAlimus;
    }

    public static boolean canExalt() {
        return !isExalting() && enoughExaltCountThisTurn() && enoughAliemusForExalt();
    }

    public static boolean canOverExalt() {
        return !isExalting() && enoughExaltCountThisTurn() && enoughAliemusForOverExalt();
    }

    public static int exhaustAliemusForExalt(boolean overExalt) {
        exalting = true;
        exaltedThisTurn++;
        if (overExalt) {
            changeAliemus(-extremeAlimus);
            return extremeAlimus;
        }
        // else if (!overExalt)
        int aliemusBefore = aliemus;
        changeAliemus(-aliemusLimit);
        changeAliemus(-MathUtils.ceil(aliemus / 2F));
        return aliemusBefore - aliemus;
    }

    public void triggerExalt(boolean overExalt) {
        if (overExalt)
            exalt.overExalt();
        else
            exalt.exalt();

        exalting = false;
    }

    public static void upgradeAliemusLimit(int amount) {
        aliemusLimit += amount;
        extremeAlimus += 2 * amount;
    }

    public static String getAliemusUIText() {
        return aliemus + "/" + aliemusLimit;
    }

    public String getExaltTitle() {
        return exalt.getExaltTitle();
    }

    public String getExaltDescription() {
        return exalt.getExaltDescription();
    }

    public String getOverExaltTitle() {
        return exalt.getOverExaltTitle();
    }

    public String getOverExaltDescription() {
        return exalt.getOverExaltDescription();
    }

    public static int getKeyflare() {
        return keyflare;
    }

    public static int changeKeyflare(int amount) {
        keyflare += amount;
        if (keyflare > maxKeyflare)
            keyflare = maxKeyflare;
        else if (keyflare < 0)
            keyflare = 0;
        return keyflare;
    }

    public static boolean isPossing() {
        return possing;
    }

    public static boolean enoughRegularPosseCountThisTurn() {
        return regularPossedThisTurn < MAX_REGULAR_POSSE_PER_TURN;
    }

    public static boolean enoughExtraPosseCountThisTurn() {
        return extraPossedThisTurn < MAX_EXTRA_POSSE_PER_TURN;
    }

    public static boolean enoughLimitedPosseCountThisTurn() {
        return enoughRegularPosseCountThisTurn() || enoughExtraPosseCountThisTurn();
    }

    public static boolean enoughKeyflareForLimitedPosse() {
        return keyflare >= posseNeededKeyflare;
    }

    public static boolean canRegularePosse() {
        return !isPossing() && enoughRegularPosseCountThisTurn() && enoughKeyflareForLimitedPosse();
    }

    public static boolean canExtraPosse() {
        return !isPossing() && enoughExtraPosseCountThisTurn() && enoughKeyflareForLimitedPosse();
    }

    public static boolean canLimitedPosse() {
        return canRegularePosse() || canExtraPosse();
    }

    public static boolean canUnlimitedPosse() {
        return true;
    }

    public static int exhaustKeyflareForPosse(PosseType type) {
        possing = true;
        if (type == PosseType.REGULAR) {
            regularPossedThisTurn++;
            changeKeyflare(-posseNeededKeyflare);
            return posseNeededKeyflare;
        } else if (type == PosseType.EXTRA) {
            extraPossedThisTurn++;
            changeKeyflare(-posseNeededKeyflare);
            return posseNeededKeyflare;
        }
        // else if (type == PosseType.UNLIMITED)
        unlimitedPosseThisTurn++;
        return 0;
    }

    public void triggerPosse(PosseType type, AbstractPosse posse) {
        if (type == PosseType.REGULAR) {
            assert this.posse == posse;
            this.posse.activate();
        } else {
            posse.activate();
        }
        possing = false;
    }

    public void addRegularPosseActionToBottom() {
        atb(new PosseAction(this, PosseType.REGULAR, posse));
    }

    public void addExtraPosseActionToBottom() {
        // TODO
    }

    public static void upgradeMaxKeyflare(int amount) {
        maxKeyflare += amount;
    }

    public static String getKeyflareUIText() {
        return keyflare + "/" + posseNeededKeyflare;
    }

    public String getPosseTitle() {
        return posse.getTitle();
    }

    public String getPosseDescription() {
        return posse.getDescription();
    }
}
