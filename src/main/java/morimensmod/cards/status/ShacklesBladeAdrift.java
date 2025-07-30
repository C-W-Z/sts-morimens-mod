package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.monsters.bosses.HelotBoss;

public class ShacklesBladeAdrift extends AbstractStatusCard {
    public final static String ID = makeID(ShacklesBladeAdrift.class.getSimpleName());

    public ShacklesBladeAdrift() {
        super(ID, 0, CardRarity.SPECIAL, CardTarget.SELF);
        magicNumber = baseMagicNumber = 10;
        secondMagic = baseSecondMagic = 5;
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, null, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, null, new LoseStrengthPower(p, magicNumber + secondMagic)));

        for (AbstractMonster boss : AbstractDungeon.getMonsters().monsters)
            if (boss instanceof HelotBoss)
                ((HelotBoss) boss).dailogAction(3);
    }
}
