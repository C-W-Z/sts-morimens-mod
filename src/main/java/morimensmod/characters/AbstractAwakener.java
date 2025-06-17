package morimensmod.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import morimensmod.exalts.AbstractExalt;
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

public abstract class AbstractAwakener extends CustomPlayer {

    public static int aliemusRegen = 0;
    public static int baseAliemusRegen = 5;

    public static int keyflareRegen = 0;
    public static int baseKeyflareRegen = 0;

    public static final int NORMAL_ALIEMUS_LIMIT = 100;
    public static int aliemus = 0;
    public static int aliemusLimit = NORMAL_ALIEMUS_LIMIT; // 普通狂氣上限 狂氣爆發
    public static int extremeAlimus = 2 * NORMAL_ALIEMUS_LIMIT; // 雙倍上限 超限爆發
    public static boolean exalting = false;
    public static int exaltedThisTurn = 0; // reset at Main Mod File
    public static int maxExaltPerTurn = 1; // reset at Main Mod File
    public AbstractExalt exalt;

    public static final int NORMAL_KEYFLARE_LIMIT = 1000;
    public static int keyflare = 0;
    public static int posseKeyflare = NORMAL_KEYFLARE_LIMIT;
    public static int maxKeyflare = 2 * NORMAL_KEYFLARE_LIMIT;
    public static boolean possing = false;
    public static int possedThisTurn = 0;           // reset at Main Mod File
    public static int extraPossedThisTurn = 0;      // reset at Main Mod File
    public static int unlimitedPosseThisTurn = 0;   // reset at Main Mod File
    public static int maxPoseePerTurn = 1;          // reset at Main Mod File
    public static int maxExtraPoseePerTurn = 1;     // reset at Main Mod File
    public static int possedThisBattle = 0;         // reset at Main Mod File
    public AbstractPosse posse;

    // percent
    public static int baseDamageAmplify;
    public static int baseBlockAmplify;
    public static int baseHealAmplify;
    public static int baseAliemusAmplify;
    public static int basePoisonAmplify;
    public static int baseCounterAmplify;

    public static final int ENERGY_PER_TURN = 5;

    public SpriteSheetAnimation anim = null;

    public AbstractAwakener(String name, PlayerClass setClass, String characterImgPath, final String CORPSE) {
        super(name, setClass,
                new CustomEnergyOrb(orbTextures, makeCharacterPath("ChaosRealm/orb/vfx.png"), null),
                new AbstractAnimation() {
                    @Override
                    public Type type() {
                        return Type.NONE;
                    }
                }
        );
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

        posseKeyflare = NORMAL_KEYFLARE_LIMIT;
        maxKeyflare = 2 * NORMAL_KEYFLARE_LIMIT;

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
        maxExaltPerTurn = 1;
    }
}
