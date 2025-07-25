package morimensmod.monsters.enemies;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;

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
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;

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

        int dmgAddition = AbstractDungeon.actNum - 1;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addDamage(2 * dmgAddition + 5, 1);
            addDamage(dmgAddition + 5, 2);
            addNoDamage();
        } else {
            addDamage(2 * dmgAddition + 3, 1);
            addDamage(dmgAddition + 3, 2);
            addNoDamage();
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION) {
            strengthDownAmt = 2 + AbstractDungeon.floorNum / 25;
            blockAmt = 10 + AbstractDungeon.floorNum / 3;
            strengthAmt = 2 + AbstractDungeon.floorNum / 25;
        } else {
            strengthDownAmt = 1 + AbstractDungeon.floorNum / 25;
            blockAmt = 5 + AbstractDungeon.floorNum / 3;
            strengthAmt = 1 + AbstractDungeon.floorNum / 25;
        }
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 35 + 3 * AbstractDungeon.floorNum / 2;
        return 25 + 3 * AbstractDungeon.floorNum / 2;
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
            case 0: setIntent(0, Intent.ATTACK_DEBUFF); break;
            case 1: setIntent(1, Intent.ATTACK);        break;
            case 2: setIntent(2, Intent.DEFEND_BUFF);   break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(20F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.NONE);
                addToBot(new ApplyPowerAction(p(), this, new StrengthPower(p(), -strengthDownAmt)));
                addToBot(new ApplyPowerAction(p(), this, new GainStrengthPower(p(), strengthDownAmt)));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(20F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.NONE);
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(22F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new AllEnemyGainBlockAction(this, blockAmt));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                break;
        }

        super.takeTurn();
    }
}
