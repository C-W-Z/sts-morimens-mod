package morimensmod.monsters;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.getPowerAmount;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.abstracts.CustomMonster;
import morimensmod.actions.NewWaitAction;
import morimensmod.misc.Animator;
import morimensmod.powers.MadnessPower;
import morimensmod.powers.TmpMadnessPower;
import morimensmod.util.ModSettings;

public class Hardhitter extends CustomMonster {

    public static final String ID = makeID(Hardhitter.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    protected ArrayList<Integer> attackCount = new ArrayList<>();

    private static final float xOffset = -32;
    private static final float yOffset = 0;

    private int turn = 0;

    public Hardhitter(float x, float y) {
        // 参数的作用分别是：
        // 名字
        // ID
        // 最大生命值，由于在之后还会设置可以随意填写
        // hitbox偏移量 - x方向
        // hitbox偏移量 - y方向
        // hitbox大小 - x方向（会影响血条宽度）
        // hitbox大小 - y方向
        // 图片
        // 怪物位置（x,y）
        super(NAME, ID, 50, 0F, 0F, 241F, 268.F, null, x, y);

        // 如果你要做进阶改变血量和伤害意图等，这样写
        if (AbstractDungeon.ascensionLevel >= 7)
            setHp(60, 70);
        else
            setHp(50, 60);

        // 怪物伤害意图的数值
        int slashDmg;
        int multiDmg;
        if (AbstractDungeon.ascensionLevel >= 2) {
            multiDmg = 7;
            slashDmg = 12;
        } else {
            multiDmg = 5;
            slashDmg = 10;
        }
        damage.add(new DamageInfo(this, 0));
        attackCount.add(0);
        damage.add(new DamageInfo(this, multiDmg));
        attackCount.add(2);
        damage.add(new DamageInfo(this, slashDmg));
        attackCount.add(1);

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
        this.animation = animator;
    }

    protected int getAttackCount(int move) {
        return attackCount.get(move) + getPowerAmount(this, MadnessPower.POWER_ID) + getPowerAmount(this, TmpMadnessPower.POWER_ID);
    }

    // 战斗开始时
    @Override
    public void usePreBattleAction() {
        turn = 0;
        // super.usePreBattleAction();
        addToBot(new ApplyPowerAction(this, this, new MadnessPower(this, 1)));
    }

    // 当怪物roll意图的时候，这里设置其意图。num是一个0~99的随机数。
    @Override
    public void getMove(int num) {
        switch (turn % 3) {
            case 0:
                setMove((byte) 0, Intent.DEFEND_BUFF, 0);
                break;
            case 1:
                setMove((byte) 1, Intent.ATTACK, damage.get(1).base, getAttackCount(1), true);
                break;
            case 2:
                setMove((byte) 2, Intent.ATTACK_DEBUFF, damage.get(2).base, getAttackCount(2), getAttackCount(2) != 1);
            default:
                break;
        }
    }

    // 执行动作
    @Override
    public void takeTurn() {
        // nextMove就是roll到的意图，0就是意图0，1就是意图1
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(0.4F));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1)));
                addToBot(new GainBlockAction(this, this, 10));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(0.5F));
                for (int i = 0; i < getAttackCount(1); i++)
                    addToBot(new DamageAction(p(), damage.get(1), AttackEffect.BLUNT_LIGHT));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(0.5F));
                for (int i = 0; i < getAttackCount(2); i++)
                    addToBot(new DamageAction(p(), damage.get(2), AttackEffect.BLUNT_HEAVY));
                addToBot(new ApplyPowerAction(p(), this, new WeakPower(p(), 1, true)));
                break;
        }

        turn++;

        // 要加一个rollmove的action，重roll怪物的意图
        addToBot(new RollMoveAction(this));
    }

    @Override
    public void changeState(String stateName) {
        ((Animator) this.animation).setAnimation(stateName);
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0)
            ((Animator) this.animation).setAnimation(ModSettings.MONSTER_HIT_ANIM);
    }
}
