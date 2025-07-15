package morimensmod.monsters.enemies;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.shuffleIn;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.Convulsion;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;

public class InterferenceTypeDissolute extends AbstractMorimensMonster {

    public static final String ID = makeID(InterferenceTypeDissolute.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 0;
    private static final float yOffset = -15;

    private int convulsionAmt = 1;
    private int blockAmt = 5;
    private int strengthAmt = 2;

    public InterferenceTypeDissolute(float x, float y) {
        this(x, y, 0);
    }

    public InterferenceTypeDissolute(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 240F, 450F, x, y, turnOffset);

        int dmgAddition = AbstractDungeon.floorNum / 10;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addDamage(0, 0);
            addDamage(dmgAddition + 12, 1);
        } else {
            addDamage(0, 0);
            addDamage(dmgAddition + 10, 1);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION) {
            convulsionAmt = 2;
            strengthAmt = 3 + AbstractDungeon.floorNum / 17;
        } else {
            convulsionAmt = 1;
            strengthAmt = 2 + AbstractDungeon.floorNum / 17;
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            blockAmt = 12 + AbstractDungeon.floorNum / 5;
        else
            blockAmt = 8 + AbstractDungeon.floorNum / 5;
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 40 + 2 * AbstractDungeon.floorNum;
        return 30 + 2 * AbstractDungeon.floorNum;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                9, 23, 6, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_HIT_ANIM + ".png"),
                4, 5, 0, false, xOffset + 33.5F, yOffset + 15F);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                8, 5, 2, false, xOffset - 116F, yOffset + 15F);
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                6, 10, 1, false, xOffset + 120.5F, yOffset - 308.5F);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    @Override
    public void getMove(int num) {
        switch (turn % 2) {
            case 0:
                setMove((byte) 0, Intent.DEFEND_DEBUFF, 0);
                break;
            case 1:
                setAttackIntent(1, Intent.ATTACK_BUFF);
                break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(31F / 30F));
                addToBot(new GainBlockAction(this, blockAmt));
                shuffleIn(new Convulsion(), convulsionAmt);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(19F / 30F));
                attackAction(nextMove, AttackEffect.NONE);
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                break;
        }

        super.takeTurn();
    }
}
