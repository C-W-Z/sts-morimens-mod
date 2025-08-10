package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.monsters.bosses.HelotBoss;
import morimensmod.powers.LoseAllBlockPower;

public class ShacklesTorturedSlave extends AbstractStatusCard {
    public final static String ID = makeID(ShacklesTorturedSlave.class.getSimpleName());

    public ShacklesTorturedSlave() {
        super(ID, 0, CardRarity.SPECIAL, CardTarget.SELF);
        sortIndex = 2002;
        heal = baseHeal = 10;
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, null, heal));
        addToBot(new ApplyPowerAction(p, null, new LoseAllBlockPower(p, 1)));

        for (AbstractMonster boss : AbstractDungeon.getMonsters().monsters)
            if (boss instanceof HelotBoss)
                ((HelotBoss) boss).dailogAction(1);
    }
}
