package morimensmod.monsters.minions;

import static morimensmod.util.Wiz.isInCombat;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.MinionPower;

import basemod.animations.AbstractAnimation;
import morimensmod.monsters.AbstractMorimensMonster;

public abstract class AbstractMinion extends AbstractMorimensMonster {

    public AbstractMinion(String name, String id, int maxHealth, float hb_w, float hb_h, float x, float y, int turnOffset) {
        super(name, id, maxHealth, hb_w, hb_h, x, y, turnOffset);
        if (isInCombat() && !hasPower(MinionPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(this, null, new MinionPower(this)));
            onSummon();
        }
    }

    public AbstractMinion(String name, String id, int maxHealth, float hb_w, float hb_h, AbstractAnimation animation,
            float x, float y, int turnOffset) {
        super(name, id, maxHealth, hb_w, hb_h, animation, x, y, turnOffset);
        if (isInCombat() && !hasPower(MinionPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(this, null, new MinionPower(this)));
            onSummon();
        }
    }

    @Override
    public void usePreBattleAction() {
        if (!hasPower(MinionPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(this, null, new MinionPower(this)));
            onSummon();
        }
    }

    protected abstract void onSummon();
}
