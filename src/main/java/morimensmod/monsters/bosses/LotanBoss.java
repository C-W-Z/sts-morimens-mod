package morimensmod.monsters.bosses;

import static morimensmod.MorimensMod.makeCharacterPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;

import basemod.animations.AbstractAnimation;

import morimensmod.actions.NewWaitAction;
import morimensmod.characters.Lotan;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractAwakenableBoss;
import morimensmod.powers.MadnessPower;

public class LotanBoss extends AbstractAwakenableBoss {

    public static final String ID = makeID(LotanBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 0;
    private static final float yOffset = 0;

    private int strengthAmt = 4;
    private int madnessAmt = 1;

    public LotanBoss(float x, float y) {
        super(NAME, ID, 500, 310, x, y);

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_DMG) {
            addDamage(12, 3);
            addDamage(19, 1);
            addDamage(42, 1);
            addNoDamage();
        } else {
            addDamage(9, 3);
            addDamage(16, 1);
            addDamage(39, 1);
            addNoDamage();
        }

        // TODO: 進階19加上覺醒後每回合移除3層虛弱和易傷
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_BOSS_ACTION)
            strengthAmt = 5;
        else
            strengthAmt = 4;
    }

    @Override
    protected final int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 720;
        return 650;
    }

    @Override
    protected final int getRousedMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 1080;
        return 975;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.PLAYER_IDLE_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_IDLE_ANIM + ".png"),
                9, 7, 2, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_HIT_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_HIT_ANIM + ".png"),
                5, 4, 0, false, xOffset - 55F, yOffset - 21F);
        animator.addAnimation(
                ModSettings.PLAYER_DEFENCE_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_DEFENCE_ANIM + ".png"),
                3, 9, 0, false, xOffset - 75F, yOffset - 11F);
        animator.addAnimation(
                ModSettings.PLAYER_ATTACK_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_ATTACK_ANIM + ".png"),
                9, 3, 2, false, xOffset + 199.5F, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_ROUSE_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_ROUSE_ANIM + ".png"),
                7, 10, 3, false, xOffset - 28F, yOffset);
        animator.setFlip(true, false);
        animator.setDefaultAnim(ModSettings.PLAYER_IDLE_ANIM);
        return animator;
    }

    @Override
    protected final int getFirstMoveID() { return 2; }

    @Override
    protected final int getRouseMoveID() { return 3; }

    @Override
    protected int getNextMoveIDExceptRouse(int _moveID) {
        // 2 -> 0 -> 1 loop
        // 3 -> (0 -> 1 -> 2 loop)
        switch (_moveID) {
            default:
            case 0: return 1;
            case 1: return 2;
            case 2: return 0;
            case 3: return 0;
        }
    }

    @Override
    protected void setMoveIntent(int _moveID) {
        switch (_moveID) {
            case 0: setIntent(monsterStrings.MOVES[_moveID], _moveID, Intent.ATTACK);      break;
            case 1: setIntent(monsterStrings.MOVES[_moveID], _moveID, Intent.ATTACK_BUFF); break;
            case 2: setIntent(monsterStrings.MOVES[_moveID], _moveID, Intent.ATTACK);      break;
            case 3: setIntent(monsterStrings.MOVES[_moveID], _moveID, Intent.MAGIC);       break;
        }
    }

    @Override
    protected void takeMoveAction(int _moveID) {
        switch (_moveID) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(9F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_DIAGONAL);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(9F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_HORIZONTAL);
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(9F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_DIAGONAL);
                break;
            case 3:
                addToBot(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ROUSE_ANIM));
                addToBot(new NewWaitAction(31F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new MadnessPower(this, madnessAmt)));
                break;
            default:
                break;
        }
    }

    @Override
    protected void preBattle() {}

    @Override
    protected void onHalfDead() {}
}
