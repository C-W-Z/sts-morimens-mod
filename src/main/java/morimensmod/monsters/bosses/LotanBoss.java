package morimensmod.monsters.bosses;

import static morimensmod.MorimensMod.makeCharacterPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
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
import morimensmod.powers.monster.CeaselessFightingSpiritPower;
import morimensmod.powers.monster.MadnessPower;
import morimensmod.vfx.CetaceanEffect;

public class LotanBoss extends AbstractAwakenableBoss {

    public static final String ID = makeID(LotanBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);

    private static final float xOffset = -1;
    private static final float yOffset = -12;

    private LVL lvl;

    private int strengthAmt = 4;
    private int madnessAmt = 1;
    private int ceaselessFightingSpiritAmt = 0;

    public enum LVL {
        MEDIUM,
        HARD
    }

    protected static String getName(LVL lvl) {
        switch (lvl) {
            case HARD:
                return monsterStrings.NAME;
            default:
                return monsterStrings.DIALOG[0];
        }
    }

    public LotanBoss(float x, float y, LVL lvl) {
        super(getName(lvl), ID, 500, 310, x, y);
        this.lvl = lvl;
        switch (lvl) {
            case MEDIUM:
                if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_DMG) {
                    addDamage(10, 3);
                    addDamage(18, 1);
                    addDamage(42, 1);
                    addNoDamage();
                } else {
                    addDamage(9, 3);
                    addDamage(15, 1);
                    addDamage(38, 1);
                    addNoDamage();
                }
                if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_BOSS_ACTION) {
                    strengthAmt = 5;
                    ceaselessFightingSpiritAmt = 0;
                } else {
                    strengthAmt = 4;
                    ceaselessFightingSpiritAmt = 0;
                }
                break;
            case HARD:
                if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_DMG) {
                    addDamage(15, 3); // 14x3
                    addDamage(32, 1); // 32
                    addDamage(51, 1); // 51
                    addNoDamage();
                } else {
                    addDamage(14, 3);
                    addDamage(30, 1);
                    addDamage(48, 1);
                    addNoDamage();
                }
                if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_BOSS_ACTION) {
                    strengthAmt = 5;
                    ceaselessFightingSpiritAmt = 1;
                } else {
                    strengthAmt = 4;
                    ceaselessFightingSpiritAmt = 0;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected final int getMaxHP() {
        switch (lvl) {
            default:
            case MEDIUM:
                if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
                    return 732;
                return 650;
            case HARD:
                if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
                    return 1083; // 1083
                return 975; // 1083 x 0.9
        }
    }

    @Override
    protected final int getRousedMaxHP() {
        switch (lvl) {
            default:
            case MEDIUM:
                if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
                    return 1097;
                return 975;
            case HARD:
                if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
                    return 1625; // 1625
                return 1463; // 1625 x 0.9
        }
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
        animator.addAnimation(
                ModSettings.PLAYER_EXALT_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_EXALT_ANIM + ".png"),
                15, 10, 9, false, xOffset + 45F, yOffset - 80F);
        animator.addAnimation(
                ModSettings.PLAYER_SKILL1_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_SKILL1_ANIM + ".png"),
                10, 4, 0, false, xOffset + 54F, yOffset - 102F);
        animator.addAnimation(
                ModSettings.PLAYER_SKILL2_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_SKILL2_ANIM + ".png"),
                6, 7, 0, false, xOffset + 96F, yOffset - 106F);
        animator.setFlip(true, false);
        animator.setDefaultAnim(ModSettings.PLAYER_IDLE_ANIM);
        return animator;
    }

    @Override
    protected final int getFirstMoveID() {
        switch (lvl) {
            case HARD:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    protected final int getRouseMoveID() { return 3; }

    @Override
    protected int getNextMoveIDExceptRouse(int _moveID) {
        // 0 -> 1 -> 2 loop; HARD: 2 -> 0 -> 1 loop
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
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_SKILL1_ANIM));
                addToBot(new NewWaitAction(10F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_DIAGONAL);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_SKILL2_ANIM));
                addToBot(new NewWaitAction(23F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(_moveID, AttackEffect.SLASH_HORIZONTAL);
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_EXALT_ANIM));
                addToBot(new NewWaitAction(52F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new VFXAction(new CetaceanEffect(p().hb.cX, p().hb.cY - p().hb.height / 2, true),
                        4F / ModSettings.SPRITE_SHEET_ANIMATION_FPS * (Settings.FAST_MODE ? 0.5F : 1F)));
                attackAction(_moveID, AttackEffect.NONE);
                break;
            case 3:
                addToBot(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                addToBot(new ChangeStateAction(this, rouseAnim));
                addToBot(new NewWaitAction(31F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new MadnessPower(this, madnessAmt)));
                if (ceaselessFightingSpiritAmt > 0)
                    addToBot(new ApplyPowerAction(this, this, new CeaselessFightingSpiritPower(this, ceaselessFightingSpiritAmt)));
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
