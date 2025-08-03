package morimensmod.monsters.bosses;

import static morimensmod.MorimensMod.makeCharacterPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.shuffleIn;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;

import basemod.animations.AbstractAnimation;

import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.PainOfUnfulfilledDesires_Status;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractAwakenableBoss;
import morimensmod.powers.monster.BloodBarrierPower;
import morimensmod.powers.monster.PainAndPleaurePower;

public class LeighBoss extends AbstractAwakenableBoss {

    public static final String ID = makeID(LeighBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String LeighID = makeID("Leigh");
    private static final float xOffset = -20;
    private static final float yOffset = -1;

    private int strengthAmt = 5;
    private int unmetPainDamage = PainOfUnfulfilledDesires_Status.DEFAULT_DAMAGE;
    private int unmetPainAmt = 2;
    private int unmetPainAmtRoused = 1;
    private static final float BLOOD_BARRIER_PERCENT = 0.25F;
    private float bloodHealAmplify = 1F;

    public LeighBoss(float x, float y) {
        super(NAME, ID, 500, 330, x, y);

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_DMG) {
            addNoDamage();
            addDamage(15, 3); // 15x3
            addDamage(29, 1); // 29
            addDamage(40, 1); // 39
            addNoDamage();
            addDamage(16, 3);
            addDamage(30, 1);
            unmetPainDamage = PainOfUnfulfilledDesires_Status.DEFAULT_DAMAGE + 4;
        } else {
            addNoDamage();
            addDamage(13, 3);
            addDamage(27, 1);
            addDamage(35, 1);
            addNoDamage();
            addDamage(15, 3);
            addDamage(28, 1);
            unmetPainDamage = PainOfUnfulfilledDesires_Status.DEFAULT_DAMAGE;
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_BOSS_ACTION) {
            strengthAmt = 6;
            unmetPainAmt = 3;
            unmetPainAmtRoused = 2;
        } else {
            strengthAmt = 5;
            unmetPainAmt = 2;
            unmetPainAmtRoused = 1;
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            bloodHealAmplify = 2F;
        else
            bloodHealAmplify = 1F;
    }

    @Override
    protected final int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 991; // 991 x 0.9
        return 793; // 991 x 0.8
    }

    @Override
    protected final int getRousedMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 1339; // 1487 x 0.9
        return 1190;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.PLAYER_IDLE_ANIM,
                makeCharacterPath(removeModID(LeighID) + "/" + ModSettings.PLAYER_IDLE_ANIM + ".png"),
                8, 13, 3, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_HIT_ANIM,
                makeCharacterPath(removeModID(LeighID) + "/" + ModSettings.PLAYER_HIT_ANIM + ".png"),
                5, 4, 0, false, xOffset - 1F, yOffset - 8F);
        animator.addAnimation(
                ModSettings.PLAYER_DEFENCE_ANIM,
                makeCharacterPath(removeModID(LeighID) + "/" + ModSettings.PLAYER_DEFENCE_ANIM + ".png"),
                6, 5, 1, false, xOffset, yOffset - 26F);
        animator.addAnimation(
                ModSettings.PLAYER_ATTACK_ANIM,
                makeCharacterPath(removeModID(LeighID) + "/" + ModSettings.PLAYER_ATTACK_ANIM + ".png"),
                6, 4, 1, false, xOffset + 85F, yOffset - 7F);
        animator.addAnimation(
                ModSettings.PLAYER_ROUSE_ANIM,
                makeCharacterPath(removeModID(LeighID) + "/" + ModSettings.PLAYER_ROUSE_ANIM + ".png"),
                7, 11, 4, false, xOffset + 24F, yOffset - 20F);
        animator.addAnimation(
                ModSettings.PLAYER_EXALT_ANIM,
                makeCharacterPath(removeModID(LeighID) + "/" + ModSettings.PLAYER_EXALT_ANIM + ".png"),
                12, 12, 0, false, xOffset + 20F, yOffset - 9F);
        animator.setFlip(true, false);
        animator.setDefaultAnim(ModSettings.PLAYER_IDLE_ANIM);
        return animator;
    }

    @Override
    protected final int getFirstMoveID() { return 0; }

    @Override
    protected final int getRouseMoveID() { return 4; }

    @Override
    protected int getNextMoveIDExceptRouse(int _moveID) {
        // 0 -> 1 -> (1 -> 2 -> 3 loop)
        // 4 -> (3 -> 5 -> 6 loop)
        switch (_moveID) {
            default:
            case 0: return 1;
            case 1: return lastMove((byte) 0) ? 1 : 2;
            case 2: return 3;
            case 3: return hasRoused() ? 5 : 1;
            case 4: return 3;
            case 5: return 6;
            case 6: return 3;
        }
    }

    @Override
    protected void setMoveIntent(int _moveID) {
        switch (_moveID) {
            case 0: setIntent(MOVES[_moveID], _moveID, Intent.BUFF);            break;
            case 1: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_DEBUFF);   break;
            case 2: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_BUFF);     break;
            case 3: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_BUFF);     break;
            case 4: setIntent(MOVES[_moveID], _moveID, Intent.MAGIC);           break;
            case 5: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_DEBUFF);   break;
            case 6: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_BUFF);     break;
        }
    }

    @Override
    protected void takeMoveAction(int _moveID) {
        switch (_moveID) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_EXALT_ANIM));
                addToBot(new NewWaitAction(23F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new PainAndPleaurePower(this)));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(7F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_HORIZONTAL);
                shuffleIn(new PainOfUnfulfilledDesires_Status(unmetPainDamage), unmetPainAmt);
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(7F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.BLUNT_LIGHT);
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                break;
            case 3:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(7F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                bloodAction(_moveID, AttackEffect.BLUNT_HEAVY);
                break;
            case 4:
                addToBot(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                addToBot(new ChangeStateAction(this, rouseAnim));
                addToBot(new NewWaitAction(23F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                actB(() -> addToTop(new ApplyPowerAction(this, this,
                        new BloodBarrierPower(this, MathUtils.ceil(maxHealth * BLOOD_BARRIER_PERCENT)))));
                break;
            case 5:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(7F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                bloodAction(_moveID, AttackEffect.SLASH_HORIZONTAL);
                shuffleIn(new PainOfUnfulfilledDesires_Status(unmetPainDamage), unmetPainAmtRoused);
                break;
            case 6:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(7F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                bloodAction(_moveID, AttackEffect.BLUNT_LIGHT);
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                break;
            default:
                break;
        }
    }

    protected void bloodAction(int move, AttackEffect effect) {
        for (int i = 0; i < getAttackCount(move); i++)
            addToBot(new DamageCallbackAction(p(), damage.get(move), effect,
                    unblockedDamage -> addToTop(
                            new HealAction(this, this, MathUtils.ceil(unblockedDamage * bloodHealAmplify)))));
    }
}
