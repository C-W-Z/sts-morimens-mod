package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import basemod.ReflectionHacks;

public class PierceDamageAction extends AbstractGameAction {

    private DamageInfo info;
    private boolean skipWait;
    private boolean muteSfx;

    public PierceDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.skipWait = false;
        this.muteSfx = false;
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.1F;
    }

    public PierceDamageAction(AbstractCreature target, DamageInfo info) {
        this(target, info, AttackEffect.NONE);
    }

    public PierceDamageAction(AbstractCreature target, DamageInfo info, boolean superFast) {
        this(target, info, AttackEffect.NONE);
        this.skipWait = superFast;
    }

    public PierceDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect,
            boolean superFast) {
        this(target, info, effect);
        this.skipWait = superFast;
    }

    public PierceDamageAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect,
            boolean superFast, boolean muteSfx) {
        this(target, info, effect, superFast);
        this.muteSfx = muteSfx;
    }

    public void update() {
        if (this.shouldCancelAction() && this.info.type != DamageType.THORNS) {
            this.isDone = true;
        } else {
            if (this.duration == 0.1F) {
                if (this.info.type != DamageType.THORNS && (this.info.owner.isDying || this.info.owner.halfDead)) {
                    this.isDone = true;
                    return;
                }

                AbstractDungeon.effectList.add(
                        new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, this.muteSfx));
            }

            this.tickDuration();
            if (this.isDone) {

                if (this.info.output > 0 && this.target.hasPower(IntangiblePlayerPower.POWER_ID))
                    this.info.output = 1;

                this.info.type = DamageType.NORMAL;
                if (this.target.currentBlock > 0) {
                    int originalDamage = this.info.output;
                    if (this.info.output > this.target.currentBlock)
                        this.info.output = this.target.currentBlock;
                    invokeDecrementBlock(this.target, this.info, this.info.output);
                    this.info.output = originalDamage;
                }

                this.info.type = DamageType.HP_LOSS;
                this.target.damage(this.info);
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                if (!this.skipWait && !Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(0.1F));
                }
            }
        }
    }

    private static int invokeDecrementBlock(AbstractCreature creature, DamageInfo info, int damageAmount) {
        // try {
        //     Method method = AbstractCreature.class.getDeclaredMethod("decrementBlock", DamageInfo.class, int.class);
        //     method.setAccessible(true);
        //     return (int) method.invoke(creature, info, damageAmount);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     return 0;
        // }
        return ReflectionHacks.privateMethod(
                AbstractCreature.class,     // class
                "decrementBlock",           // method name
                DamageInfo.class, int.class // method parameter types
        ).invoke(creature, info, damageAmount); // 實例與參數
    }
}
