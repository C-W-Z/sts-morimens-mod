package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import morimensmod.monsters.bosses.HelotBoss;

public class ShacklesLadyGovernor extends AbstractStatusCard {
    public final static String ID = makeID(ShacklesLadyGovernor.class.getSimpleName());

    public ShacklesLadyGovernor() {
        super(ID, 0, CardRarity.SPECIAL, CardTarget.SELF);
        magicNumber = baseMagicNumber = 21;
        secondMagic = baseMagicNumber = 3;
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, magicNumber));
        addToBot(new ApplyPowerAction(p, null, new DexterityPower(p, -secondMagic)));

        for (AbstractMonster boss : AbstractDungeon.getMonsters().monsters)
            if (boss instanceof HelotBoss)
                ((HelotBoss) boss).dailogAction(0);
    }
}
