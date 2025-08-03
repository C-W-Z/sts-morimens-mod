package morimensmod.monsters.elites;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.addToDiscard;
import static morimensmod.util.Wiz.getPowerAmount;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.Stagger;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;
import morimensmod.powers.monster.InsistentBitesPower;
import morimensmod.powers.monster.MadnessPower;

public class DevouringHowl extends AbstractMorimensMonster {

    public static final String ID = makeID(DevouringHowl.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 0;
    private static final float yOffset = 0;

    private int insistentBitesAmt = 1;
    private int madnessAmt = 1;
    private int staggerAmt = 2;

    public DevouringHowl(float x, float y) {
        this(x, y, 0);
    }

    public DevouringHowl(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 430F, 500F, x, y, turnOffset);

        this.type = EnemyType.ELITE;

        int dmgAddition = AbstractDungeon.actNum - 1;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_ELITE_DMG) {
            addDamage(dmgAddition + 6, 4);
            addDamage(dmgAddition + 7, 2);
            addDamage(dmgAddition + 10, 3);
            addDamage(dmgAddition + 7, 2);
            addNoDamage();
        } else {
            addDamage(dmgAddition + 5, 4);
            addDamage(dmgAddition + 6, 2);
            addDamage(dmgAddition + 9, 3);
            addDamage(dmgAddition + 6, 2);
            addNoDamage();
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_ELITE_ACTION)
            staggerAmt = 3;
        else
            staggerAmt = 2;
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_ELITE_HP)
            return 240 + 5 * AbstractDungeon.floorNum;
        return 220 + 4 * AbstractDungeon.floorNum;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                9, 14, 5, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_HIT_ANIM + ".png"),
                5, 4, 0, false, xOffset + 123F, yOffset - 48F);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                11, 4, 1, false, xOffset - 71F, yOffset - 8F);
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                10, 7, 1, false, xOffset - 160.5F, yOffset - 35.5F);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    @Override
    public void getMove(int num) {
        switch (turn % 5) {
            case 0: setIntent(0, Intent.ATTACK);                                break;
            case 1: setIntent(monsterStrings.MOVES[0], 1, Intent.ATTACK_BUFF);  break;
            case 2: setIntent(2, Intent.ATTACK);                                break;
            case 3: setIntent(monsterStrings.MOVES[0], 3, Intent.ATTACK_BUFF);  break;
            case 4: setIntent(monsterStrings.MOVES[1], 4, Intent.BUFF);         break;
        }
    }

    @Override
    protected int getAttackCount(int move) {
        if (move == 1 || move == 3)
            return super.getAttackCount(move) + getPowerAmount(this, InsistentBitesPower.POWER_ID);
        return super.getAttackCount(move);
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(17F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.NONE);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(17F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.NONE);
                addToBot(new ApplyPowerAction(this, this, new InsistentBitesPower(this, insistentBitesAmt)));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(17F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.NONE);
                break;
            case 3:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(17F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.NONE);
                addToBot(new ApplyPowerAction(this, this, new InsistentBitesPower(this, insistentBitesAmt)));
                break;
            case 4:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(40F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new MadnessPower(this, madnessAmt)));
                addToDiscard(new Stagger(), staggerAmt);
                break;
        }

        super.takeTurn();
    }
}
