package morimensmod.monsters.bosses;

import static morimensmod.MorimensMod.makeCharacterPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.shuffleIn;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;

import basemod.animations.AbstractAnimation;

import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.ShacklesBladeAdrift;
import morimensmod.cards.status.ShacklesDivineMaidenReturn;
import morimensmod.cards.status.ShacklesLadyGovernor;
import morimensmod.cards.status.ShacklesTorturedSlave;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractAwakenableBoss;

public class HelotBoss extends AbstractAwakenableBoss {

    public static final String ID = makeID(HelotBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    public static final float DIALOG_DURATION = 5F;

    public static final String HelotID = makeID("Helot");
    private static final float xOffset = -20;
    private static final float yOffset = -1;

    private int strengthAmt = 16;
    private int rouseStrengthAmt = 27;

    public HelotBoss(float x, float y) {
        super(NAME, ID, 500, 330, x, y);

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_DMG) {
            addDamage(53, 1);
            addDamage(43, 1);
            addNoDamage();
            addNoDamage();
            addDamage(75, 1);
        } else {
            addDamage(50, 1);
            addDamage(40, 1);
            addNoDamage();
            addNoDamage();
            addDamage(70, 1);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_BOSS_ACTION) {
            strengthAmt = 16;
            rouseStrengthAmt = 27;
        } else {
            strengthAmt = 12;
            rouseStrengthAmt = 24;
        }
    }

    @Override
    protected final int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 1188;
        return 951;
    }

    @Override
    protected final int getRousedMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 1740;
        return 1392;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.PLAYER_IDLE_ANIM,
                makeCharacterPath(removeModID(HelotID) + "/" + ModSettings.PLAYER_IDLE_ANIM + ".png"),
                6, 17, 1, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_HIT_ANIM,
                makeCharacterPath(removeModID(HelotID) + "/" + ModSettings.PLAYER_HIT_ANIM + ".png"),
                4, 5, 0, false, xOffset - 1F, yOffset - 8F);
        animator.addAnimation(
                ModSettings.PLAYER_DEFENCE_ANIM,
                makeCharacterPath(removeModID(HelotID) + "/" + ModSettings.PLAYER_DEFENCE_ANIM + ".png"),
                6, 6, 0, false, xOffset, yOffset - 26F);
        animator.addAnimation(
                ModSettings.PLAYER_ATTACK_ANIM,
                makeCharacterPath(removeModID(HelotID) + "/" + ModSettings.PLAYER_ATTACK_ANIM + ".png"),
                9, 3, 1, false, xOffset + 85F, yOffset - 7F);
        animator.addAnimation(
                ModSettings.PLAYER_ROUSE_ANIM,
                makeCharacterPath(removeModID(HelotID) + "/" + ModSettings.PLAYER_ROUSE_ANIM + ".png"),
                9, 8, 0, false, xOffset + 24F, yOffset - 20F);
        animator.addAnimation(
                ModSettings.PLAYER_EXALT_ANIM,
                makeCharacterPath(removeModID(HelotID) + "/" + ModSettings.PLAYER_EXALT_ANIM + ".png"),
                11, 16, 0, false, xOffset + 20F, yOffset - 9F);
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
        // (0 -> 1 -> 2 loop)
        // 3 -> (4 -> 1 -> 2 loop)
        switch (_moveID) {
            default:
            case 0: return 1;
            case 1: return 2;
            case 2: return hasRoused() ? 4 : 0;
            case 3: return 4;
            case 4: return 1;
        }
    }

    @Override
    protected void setMoveIntent(int _moveID) {
        switch (_moveID) {
            case 0: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK);  break;
            case 1: setIntent(_moveID, Intent.ATTACK);                  break;
            case 2: setIntent(MOVES[_moveID], _moveID, Intent.BUFF);    break;
            case 3: setIntent(MOVES[_moveID], _moveID, Intent.MAGIC);   break;
            case 4: setIntent(MOVES[_moveID], _moveID, Intent.ATTACK);  break;
        }
    }

    @Override
    protected void takeMoveAction(int _moveID) {
        switch (_moveID) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(10F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_HORIZONTAL);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(10F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_HORIZONTAL);
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_EXALT_ANIM));
                addToBot(new NewWaitAction(30F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                addToBot(new RemoveSpecificPowerAction(this, this, WeakPower.POWER_ID));
                addToBot(new RemoveSpecificPowerAction(this, this, VulnerablePower.POWER_ID));
                break;
            case 3:
                addToBot(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                addToBot(new ChangeStateAction(this, rouseAnim));
                addToBot(new NewWaitAction(72F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, rouseStrengthAmt)));
                shuffleIn(new ShacklesLadyGovernor());
                shuffleIn(new ShacklesTorturedSlave());
                shuffleIn(new ShacklesDivineMaidenReturn());
                shuffleIn(new ShacklesBladeAdrift());
                break;
            case 4:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(10F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
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
