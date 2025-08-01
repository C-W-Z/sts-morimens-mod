package morimensmod.monsters.bosses;

import static morimensmod.MorimensMod.makeCharacterPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.addToDiscard;
import static morimensmod.util.Wiz.makeInHand;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.shuffleIn;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;

import basemod.animations.AbstractAnimation;

import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.Joker;
import morimensmod.cards.status.ShacklesBladeAdrift;
import morimensmod.cards.status.ShacklesDivineMaidenReturn;
import morimensmod.cards.status.ShacklesLadyGovernor;
import morimensmod.cards.status.ShacklesTorturedSlave;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractAwakenableBoss;
import morimensmod.powers.DrawLessPower;

public class CasiahBoss extends AbstractAwakenableBoss {

    public static final String ID = makeID(CasiahBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final float DIALOG_DURATION = 5F;

    public static final String CasiahID = makeID("Casiah");
    private static final float xOffset = -40;
    private static final float yOffset = -3;

    private int jokerDmg = Joker.DEFAULT_DAMAGE;
    private int jokerAmtHand = 1;
    private int jokerAmtDraw = 2;
    private int jokerAmtDiscard = 2;
    private int weakAmt = 2;
    private int strengthAmt = 3;
    private int loseDrawAmtMore = 3;
    private int frailAmt = 2;
    private int loseDrawAmtLess = 1;
    private int rouseStrengthAmt = 27;

    public CasiahBoss(float x, float y) {
        super(NAME, ID, 350, 340, x, y);

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_DMG) {
            addDamage(12, 5); // 11x5
            addDamage(18, 2); // 18x2
            addNoDamage();
            addDamage(18, 2); // 18x2
            addDamage(16, 3); // 16x3
            addNoDamage();
            jokerDmg = Joker.DEFAULT_DAMAGE + 2;
        } else {
            addDamage(11, 5);
            addDamage(16, 2);
            addNoDamage();
            addDamage(16, 2);
            addDamage(14, 3);
            addNoDamage();
            jokerDmg = Joker.DEFAULT_DAMAGE;
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_BOSS_ACTION) {
            strengthAmt = 4;
            weakAmt = 3;
            frailAmt = 3;
        } else {
            strengthAmt = 3;
            weakAmt = 2;
            frailAmt = 2;
        }

        this.dialogX = -75F * Settings.scale;
        this.dialogY = 50F * Settings.scale;
    }

    @Override
    protected final int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 949;
        return 855; // 949 x 0.9
    }

    @Override
    protected final int getRousedMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 949;
        return 855; // 949 x 0.9
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.PLAYER_IDLE_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_IDLE_ANIM + ".png"),
                7, 9, 2, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_ATTACK_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_ATTACK_ANIM + ".png"),
                5, 7, 2, false, xOffset + 31F, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_DEFENCE_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_DEFENCE_ANIM + ".png"),
                6, 7, 1, false, xOffset + 42F, yOffset - 2F);
        animator.addAnimation(
                ModSettings.PLAYER_HIT_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_HIT_ANIM + ".png"),
                4, 5, 0, false, xOffset - 59F, yOffset - 14F);
        // TODO:
        animator.addAnimation(
                ModSettings.PLAYER_ROUSE_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_ROUSE_ANIM + ".png"),
                9, 8, 1, false, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_EXALT_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_EXALT_ANIM + ".png"),
                11, 15, 2, false, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_SKILL1_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_SKILL1_ANIM + ".png"),
                6, 10, 2, false, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_SKILL2_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_SKILL2_ANIM + ".png"),
                8, 6, 1, false, xOffset, yOffset);
        animator.setFlip(true, false);
        animator.setDefaultAnim(ModSettings.PLAYER_IDLE_ANIM);
        return animator;
    }

    @Override
    protected final int getFirstMoveID() { return 0; }

    @Override
    protected final int getRouseMoveID() { return 3; }

    @Override
    protected int getNextMoveIDExceptRouse(int _moveID) {
        // 0 -> (1 -> 2 -> 3 -> 4 loop)
        // 5 -> 0 -> (1 -> 2 -> 3 -> 4 loop)
        switch (_moveID) {
            default:
            case 0: return 1;
            case 1: return 2;
            case 2: return 3;
            case 3: return 4;
            case 4: return 1;
            case 5: return 0;
        }
    }

    @Override
    protected void setMoveIntent(int _moveID) {
        switch (_moveID) {
            case 0: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_DEBUFF);   break;
            case 1: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_DEBUFF);   break;
            case 2: setIntent(MOVES[_moveID], _moveID, Intent.STRONG_DEBUFF);   break;
            case 3: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_DEBUFF);   break;
            case 4: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK_DEBUFF);   break;
            case 5: setIntent(MOVES[_moveID], _moveID, Intent.MAGIC);           break;
        }
    }

    @Override
    protected void takeMoveAction(int _moveID) {
        switch (_moveID) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_EXALT_ANIM));
                addToBot(new NewWaitAction(92F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.BLUNT_LIGHT);
                makeInHand(new Joker(jokerDmg), jokerAmtHand);
                shuffleIn(new Joker(jokerDmg), jokerAmtDraw);
                addToDiscard(new Joker(jokerDmg), jokerAmtDiscard);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(12F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.BLUNT_LIGHT);
                addToBot(new ApplyPowerAction(p(), this, new WeakPower(p(), weakAmt, true)));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_SKILL1_ANIM));
                addToBot(new NewWaitAction(34F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                addToBot(new ApplyPowerAction(p(), this, new StrengthPower(p(), -strengthAmt)));
                addToBot(new ApplyPowerAction(p(), this, new DrawLessPower(p(), loseDrawAmtMore)));
                break;
            case 3:
                addToBot(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                addToBot(new ChangeStateAction(this, rouseAnim));
                addToBot(new NewWaitAction(29F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                makeInHand(new ShacklesLadyGovernor());
                makeInHand(new ShacklesTorturedSlave());
                makeInHand(new ShacklesDivineMaidenReturn());
                makeInHand(new ShacklesBladeAdrift());
                break;
            case 4:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_SKILL2_ANIM));
                addToBot(new NewWaitAction(14F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_HORIZONTAL);
                break;
            default:
                break;
        }
    }

    @Override
    protected void preBattle() {}

    @Override
    protected void onHalfDead() {}

    public void dailogAction(int dialogID) {
        AbstractDungeon.effectList.add(
                new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, DIALOG_DURATION, DIALOG[dialogID], false));
    }
}
