package morimensmod.monsters;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import basemod.abstracts.CustomMonster;
import basemod.animations.AbstractAnimation;
import morimensmod.config.ModSettings;
import morimensmod.misc.Animator;
import morimensmod.powers.monster.MadnessPower;
import morimensmod.powers.monster.TmpMadnessPower;

import static morimensmod.util.Wiz.getPowerAmount;
import static morimensmod.util.Wiz.p;

public abstract class AbstractMorimensMonster extends CustomMonster {

    protected ArrayList<Integer> attackCount = new ArrayList<>();
    protected int turnOffset = 0;
    protected int turn = 0;

    public AbstractMorimensMonster(String name, String id, int maxHealth, float hb_w, float hb_h,
            AbstractAnimation animation, float x, float y, int turnOffset) {
        super(name, id, maxHealth, 0F, 0.0F, hb_w, hb_h, null, x, y);
        this.animation = animation;
        this.turn = this.turnOffset = turnOffset;
    }


    public AbstractMorimensMonster(String name, String id, int maxHealth, float hb_w, float hb_h, float x, float y, int turnOffset) {
        // 名字
        // ID
        // 最大生命值，由於之後還會設定可以隨意填寫
        // hitbox偏移量 - x方向
        // hitbox偏移量 - y方向
        // hitbox大小 - x方向（會影響血條寬度）
        // hitbox大小 - y方向
        // 圖片
        // 怪物位置（x,y）
        super(name, id, maxHealth, 0F, 0.0F, hb_w, hb_h, null, x, y);
        this.animation = getAnimation();
        this.turn = this.turnOffset = turnOffset;
    }

    protected abstract AbstractAnimation getAnimation();

    protected final void addDamage(int dmg, int count) {
        damage.add(new DamageInfo(this, dmg));
        attackCount.add(count);
    }

    protected final void addNoDamage() {
        addDamage(-1, 0);
    }

    protected int getAttackCount(int move) {
        return attackCount.get(move)
                + getPowerAmount(this, MadnessPower.POWER_ID)
                + getPowerAmount(this, TmpMadnessPower.POWER_ID);
    }

    protected void setIntent(int move, Intent intent) {
        int atkCount = getAttackCount(move);
        setMove((byte) move, intent, damage.get(move).base, atkCount, atkCount > 1);
    }

    protected void setIntent(String moveName, int move, Intent intent) {
        int atkCount = getAttackCount(move);
        setMove(moveName, (byte) move, intent, damage.get(move).base, atkCount, atkCount > 1);
    }

    protected void attackAction(int move, AttackEffect effect) {
        for (int i = 0; i < getAttackCount(move); i++)
            addToBot(new DamageAction(p(), damage.get(move), effect));
    }

    // 戰鬥開始
    // @Override
    // public void usePreBattleAction() {
    //     turn = turnOffset;
    // }

    // 執行動作
    @Override
    public void takeTurn() {
        turn++;
        // 要加一個rollmove的action，重roll怪物的意圖
        addToBot(new RollMoveAction(this));
    }

    @Override
    public void changeState(String stateName) {
        if (this.animation instanceof Animator)
            ((Animator) this.animation).setAnimation(stateName);
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (!(this.animation instanceof Animator))
            return;
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0)
            ((Animator) this.animation).setAnimation(ModSettings.MONSTER_HIT_ANIM);
    }
}
