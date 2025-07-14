package morimensmod.monsters;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.AllEnemyGainBlockAction;
import morimensmod.actions.NewWaitAction;
import morimensmod.misc.Animator;
import morimensmod.util.ModSettings;
import morimensmod.util.ModSettings.ASCENSION_LVL;

public class CollaborativeDissolute extends AbstractMorimensMonster {

    public static final String ID = makeID(CollaborativeDissolute.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 0;
    private static final float yOffset = 0;

    private int strengthDownAmt = 1;
    private int blockAmt = 5;
    private int strengthAmt = 1;

    public CollaborativeDissolute(float x, float y) {
        this(x, y, 0);
    }

    public CollaborativeDissolute(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 150F, 290F, x, y, turnOffset);

        int dmgAddition = AbstractDungeon.floorNum / 17;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addDamage(dmgAddition + 5, 1);
            addDamage(dmgAddition + 5, 2);
            addDamage(0, 0);
        } else {
            addDamage(dmgAddition + 3, 1);
            addDamage(dmgAddition + 3, 2);
            addDamage(0, 0);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION) {
            strengthDownAmt = 2 + AbstractDungeon.floorNum / 25;
            blockAmt = 10 + AbstractDungeon.floorNum / 2;
            strengthAmt = 2 + AbstractDungeon.floorNum / 25;
        } else {
            strengthDownAmt = 1 + AbstractDungeon.floorNum / 25;
            blockAmt = 5 + AbstractDungeon.floorNum / 2;
            strengthAmt = 1 + AbstractDungeon.floorNum / 25;
        }
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 35 + AbstractDungeon.floorNum;
        return 25 + AbstractDungeon.floorNum;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                7, 29, 2, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_HIT_ANIM + ".png"),
                5, 4, 0, false, xOffset + 44F, yOffset + 3F);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                8, 6, 2, false, xOffset + 24.5F, yOffset - 16F);
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                6, 9, 1, false, xOffset - 2F, yOffset - 64.5F);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    @Override
    public void getMove(int num) {
        switch (turn % 3) {
            case 0:
                setAttackIntent(0, Intent.ATTACK_DEBUFF);
                break;
            case 1:
                setAttackIntent(1, Intent.ATTACK);
                break;
            case 2:
                setMove((byte) 2, Intent.DEFEND_BUFF, 0);
                break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(20F / 30F));
                attackAction(nextMove, AttackEffect.NONE);
                addToBot(new ApplyPowerAction(p(), this, new StrengthPower(p(), -strengthDownAmt)));
                addToBot(new ApplyPowerAction(p(), this, new GainStrengthPower(p(), strengthDownAmt)));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(20F / 30F));
                attackAction(nextMove, AttackEffect.NONE);
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(22F / 30F));
                addToBot(new AllEnemyApplyPowerAction(this, strengthAmt, (m) -> new StrengthPower(m, strengthAmt)));
                addToBot(new AllEnemyGainBlockAction(this, blockAmt));
                break;
        }

        super.takeTurn();
    }
}
