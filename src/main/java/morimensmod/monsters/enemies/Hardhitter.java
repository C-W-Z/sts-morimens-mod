package morimensmod.monsters.enemies;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;

public class Hardhitter extends AbstractMorimensMonster {

    public static final String ID = makeID(Hardhitter.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = -32;
    private static final float yOffset = 0;

    private int strengthAmt = 2;
    private int blockAmt = 5;

    public Hardhitter(float x, float y) {
        this(x, y, 0);
    }

    public Hardhitter(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 240F, 270F, x, y, turnOffset);

        int dmgAddition = AbstractDungeon.actNum - 1;

        // 怪物伤害意图的数值
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addNoDamage();
            addDamage(dmgAddition + 5, 2);
            addDamage(dmgAddition + 6, 1);
        } else {
            addNoDamage();
            addDamage(dmgAddition + 3, 2);
            addDamage(dmgAddition + 4, 1);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION)
            strengthAmt = 3 + AbstractDungeon.floorNum / 25;
        else
            strengthAmt = 2 + AbstractDungeon.floorNum / 25;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            blockAmt = 8 + AbstractDungeon.floorNum / 5;
        else
            blockAmt = 5 + AbstractDungeon.floorNum / 5;
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 40 + AbstractDungeon.floorNum;
        return 30 + AbstractDungeon.floorNum;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        // xOffset是Idle_1和另一張圖置中疊在一起之後，另一張要水平移動多少距離才會和Idle_1水平位置重合
        // yOffset是Idle_1和另一張貼圖齊底部疊在一起後，另一張要垂直移動多少才會和Idle_1高度相同
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                13, 16, 11, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_HIT_ANIM + ".png"),
                5, 4, 0, false, xOffset + 20F, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                7, 5, 0, false, xOffset - 19F, yOffset - 14F);
        // 這個xOffset不知道為什麼特別奇怪
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                6, 7, 0, false, xOffset + 29F, yOffset);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    // 战斗开始时
    // @Override
    // public void usePreBattleAction() {
    //     super.usePreBattleAction();
    //     addToBot(new ApplyPowerAction(this, this, new MadnessPower(this, 1)));
    // }

    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        switch (turn % 3) {
            case 0: setIntent(0, Intent.DEFEND_DEBUFF); break;
            case 1: setIntent(1, Intent.ATTACK);        break;
            case 2: setIntent(2, Intent.ATTACK_DEBUFF); break;
        }
    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(12F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                addToBot(new GainBlockAction(this, this, blockAmt));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(15F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_LIGHT);
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(15F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_HEAVY);
                addToBot(new ApplyPowerAction(p(), this, new WeakPower(p(), 1, true)));
                break;
        }

        super.takeTurn();
    }
}
