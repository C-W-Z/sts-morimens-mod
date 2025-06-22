package morimensmod.characters;

import basemod.Pair;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.actions.PosseAction;
import morimensmod.exalts.AbstractExalt;
import morimensmod.misc.PosseType;
import morimensmod.misc.SpriteSheetAnimation;
import morimensmod.powers.AbstractPersistentPower;
import morimensmod.util.PersistentPowerLib;
import morimensmod.cards.posses.AbstractPosse;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static morimensmod.MorimensMod.*;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractAwakener extends CustomPlayer {

    private static final Logger logger = LogManager.getLogger(AbstractAwakener.class);

    public int baseAliemusRegen = 0;
    public int aliemusRegen = baseAliemusRegen;

    public int baseKeyflareRegen = 15;
    public int keyflareRegen = baseKeyflareRegen;

    protected static int lastUsedEnergy = 0;

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
    protected static int tmpPosseThisTurn = 0; // reset at Main Mod File
    public static final int MAX_REGULAR_POSSE_PER_TURN = 1;
    public static final int MAX_EXTRA_POSSE_PER_TURN = 1;
    protected static int possedThisBattle = 0; // reset at Main Mod File
    protected static int allPossedThisBattle = 0; // reset at Main Mod File
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

    public static ArrayList<Pair<String, Integer>> persistentPowers;

    public AbstractAwakener(String name, PlayerClass setClass, String characterImgPath, final String CORPSE) {
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

        persistentPowers = new ArrayList<>();
    }

    protected void setExaltAndPosse(AbstractExalt exalt, AbstractPosse posse) {
        this.exalt = exalt;
        this.posse = posse;
        this.posse.set(this, PosseType.REGULAR);
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
        allPossedThisBattle = 0;

        if (p() instanceof AbstractAwakener) {
            AbstractAwakener awaker = (AbstractAwakener) p();
            awaker.aliemusRegen = awaker.baseAliemusRegen;
            awaker.keyflareRegen = awaker.baseKeyflareRegen;
        }

        lastUsedEnergy = 0;

        baseDamageAmplify = 0;
        baseBlockAmplify = 0;
        baseHealAmplify = 0;
        baseAliemusAmplify = 0;
        basePoisonAmplify = 0;
        baseCounterAmplify = 0;

        for (Pair<String, Integer> pair : persistentPowers) {
            logger.debug("onBattleStart, ID: " + pair.getKey() + ", amount: " + pair.getValue());
            if (pair.getValue() != 0)
                applyToSelf(PersistentPowerLib.getPower(pair.getKey(), p(), pair.getValue()));
        }
        // persistentPowers.clear();
    }

    // called in Main Mod File
    public static void onPlayerTurnStartPostDraw() {
        exaltedThisTurn = 0;
        maxExaltPerTurn = NORMAL_MAX_EXALT_PER_TURN;

        regularPossedThisTurn = 0;
        extraPossedThisTurn = 0;
        unlimitedPosseThisTurn = 0;
        tmpPosseThisTurn = 0;
    }

    // called in Main Mod File
    public static void onPostBattle() {

        logger.debug("onPostBattle, p().powers.size: " + p().powers.size());

        persistentPowers.clear();

        for (AbstractPower p : p().powers)
            if (p instanceof AbstractPersistentPower && p.amount != 0) {
                logger.debug("onPostBattle, ID: " + p.ID + ", amount: " + p.amount);
                persistentPowers.add(new Pair<>(p.ID, p.amount));
            }
    }

    // called in UseCardActionPatch
    public static void onAfterUseCard(AbstractCard card) {
        logger.debug("lastUsedEnergy: " + lastUsedEnergy);
        if (lastUsedEnergy == 0 || !(p() instanceof AbstractAwakener))
            return;
        if (card.type == CardType.CURSE || card.type == CardType.STATUS) {
            lastUsedEnergy = 0;
            return;
        }
        AbstractAwakener awaker = (AbstractAwakener) p();
        att(new KeyflareChangeAction(awaker, lastUsedEnergy * awaker.keyflareRegen));
        lastUsedEnergy = 0;
    }

    public static int getLastUsedEnergy() {
        return lastUsedEnergy;
    }

    public static void addLastUsedEnergy(int amount) {
        lastUsedEnergy += amount;
    }

    public static int getAliemus() {
        return aliemus;
    }

    public static int setAliemus(int amount) {
        aliemus = amount;
        if (aliemus > extremeAlimus)
            aliemus = extremeAlimus;
        else if (aliemus < 0)
            aliemus = 0;
        return aliemus;
    }

    public static int changeAliemus(int amount) {
        return setAliemus(aliemus + amount);
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

    public static int setKeyflare(int amount) {
        keyflare = amount;
        if (keyflare > maxKeyflare)
            keyflare = maxKeyflare;
        else if (keyflare < 0)
            keyflare = 0;
        return keyflare;
    }

    public static int changeKeyflare(int amount) {
        return setKeyflare(keyflare + amount);
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
        if (type == PosseType.REGULAR) {
            changeKeyflare(-posseNeededKeyflare);
            return posseNeededKeyflare;
        } else if (type == PosseType.EXTRA) {
            extraPossedThisTurn++;
            changeKeyflare(-posseNeededKeyflare);
            return posseNeededKeyflare;
        } else if (type == PosseType.UNLIMITED)
            unlimitedPosseThisTurn++;
        else
            tmpPosseThisTurn++;
        return 0;
    }

    public void triggerPosse(AbstractPosse posse) {
        if (posse.getType() == PosseType.REGULAR) {
            assert this.posse == posse;
            regularPossedThisTurn++;
        } else if (posse.getType() == PosseType.EXTRA) {
            extraPossedThisTurn++;
        } else if (posse.getType() == PosseType.UNLIMITED)
            unlimitedPosseThisTurn++;
        else if (posse.getType() != PosseType.TMP)
            tmpPosseThisTurn++;

        allPossedThisBattle++;
        if (posse.getType() != PosseType.TMP)
            possedThisBattle++;

        posse.activate();
        possing = false;
    }

    public void addRegularPosseActionToBottom() {
        possing = true;
        atb(new PosseAction(this, PosseType.REGULAR, posse));
    }

    public void addExtraPosseActionToBottom() {
        possing = true;

        ArrayList<AbstractPosse> posses = getAllPosses();
        logger.debug("num_posses:" + posses.size());

        for (AbstractPosse p : posses)
            p.set(this, PosseType.EXTRA);

        ArrayList<AbstractCard> choiceCardList;
        if (getCardColor() == CHAOS_COLOR) {
            choiceCardList = new ArrayList<>(posses);
        } else {
            Collections.shuffle(posses, new Random(AbstractDungeon.miscRng.randomLong()));
            choiceCardList = new ArrayList<>(posses.subList(0, Math.min(3, posses.size())));
        }

        atb(new EasyModalChoiceAction(choiceCardList));
    }

    public static void upgradeMaxKeyflare(int amount) {
        maxKeyflare += amount;
    }

    public static int getPossedThisBattle() {
        return possedThisBattle;
    }

    public static String getKeyflareUIText() {
        return keyflare + "/" + posseNeededKeyflare;
    }

    public String getPosseTitle() {
        return posse.getUITitle();
    }

    public String getPosseDescription() {
        return posse.getUIDescription();
    }
}
