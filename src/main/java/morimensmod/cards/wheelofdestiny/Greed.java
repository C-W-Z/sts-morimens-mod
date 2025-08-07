package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class Greed extends AbstractWheelOfDestiny {
    public final static String ID = makeID(Greed.class.getSimpleName());

    public Greed() {
        super(ID, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 250;
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public void upp() {
        upgradeMagicNumber(Math.max(275 - timesUpgraded * 25, 50));
    }

    @Override
    public void preRemoveCardFromDeck(AbstractCard card) {
        if (card != this)
            return;
        p().gainGold(magicNumber);
    }

    @Override
    public int getPrice() {
        return 70;
    }
}
