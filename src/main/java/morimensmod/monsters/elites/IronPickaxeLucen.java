package morimensmod.monsters.elites;

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
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;
import morimensmod.powers.monster.ThePriceOfImpulsivenessPower;

public class IronPickaxeLucen extends AbstractMorimensMonster {

    public static final String ID = makeID(IronPickaxeLucen.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 40;
    private static final float yOffset = 0;

    private int thePriceOfImpulsivenessAmt = 1;
    private int strengthAmt = 3;
    private int weakAmt = 2;
    private int frailAmt = 2;

    public IronPickaxeLucen(float x, float y) {
        this(x, y, 0);
    }

    public IronPickaxeLucen(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 310, 450, x, y, turnOffset);

        this.type = EnemyType.ELITE;

        int dmgAddition = AbstractDungeon.actNum - 1;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_ELITE_DMG) {
            addNoDamage();
            addDamage(dmgAddition + 5, 4);
            addDamage(2 * dmgAddition + 32, 1);
        } else {
            addNoDamage();
            addDamage(dmgAddition + 4, 4);
            addDamage(2 * dmgAddition + 30, 1);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_ELITE_ACTION) {
            strengthAmt = 3 + AbstractDungeon.floorNum / 25;
            weakAmt = 2;
            frailAmt = 2;
        } else {
            strengthAmt = 2 + AbstractDungeon.floorNum / 25;
            weakAmt = 1;
            frailAmt = 1;
        }
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_ELITE_HP)
            return 230 + 5 * AbstractDungeon.floorNum;
        return 210 + 4 * AbstractDungeon.floorNum;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                4, 13, 4, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_HIT_ANIM + ".png"),
                4, 5, 0, false, xOffset - 18F, yOffset - 3F);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                5, 6, 0, false, xOffset - 103F, yOffset - 20.5F);
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                3, 14, 0, false, xOffset + 20.5F, yOffset - 21F);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        addToBot(new ApplyPowerAction(this, this, new ThePriceOfImpulsivenessPower(this, thePriceOfImpulsivenessAmt)));
    }

    @Override
    public void getMove(int num) {
        switch (turn % 3) {
            case 0: setIntent(0, Intent.BUFF);                              break;
            case 1: setIntent(1, Intent.ATTACK_DEBUFF);                     break;
            case 2: setIntent(monsterStrings.MOVES[0], 2, Intent.ATTACK);   break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(16F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                addToBot(new ApplyPowerAction(p(), this, new FrailPower(p(), frailAmt, true)));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(13F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_LIGHT);
                addToBot(new ApplyPowerAction(p(), this, new WeakPower(p(), weakAmt, true)));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(13F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_HEAVY);
                break;
        }

        super.takeTurn();
    }
}
