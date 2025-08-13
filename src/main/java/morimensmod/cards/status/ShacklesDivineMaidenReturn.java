package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.monsters.bosses.HelotBoss;
import morimensmod.powers.TmpBarrierPower;

public class ShacklesDivineMaidenReturn extends AbstractStatusCard {
    public final static String ID = makeID(ShacklesDivineMaidenReturn.class.getSimpleName());

    public ShacklesDivineMaidenReturn() {
        super(ID, 0, CardRarity.SPECIAL, CardTarget.SELF);
        sortIndex = 2003;
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 2;
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, null, new TmpBarrierPower(p, magicNumber)));
        actB(() -> p.energy.energy -= secondMagic);

        for (AbstractMonster boss : AbstractDungeon.getMonsters().monsters)
            if (boss instanceof HelotBoss)
                ((HelotBoss) boss).dailogAction(2);
    }
}
