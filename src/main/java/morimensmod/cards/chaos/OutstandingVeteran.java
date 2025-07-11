package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.OutstandingVeteranPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class OutstandingVeteran extends AbstractRouseCard {
    public final static String ID = makeID(OutstandingVeteran.class.getSimpleName());

    public OutstandingVeteran() {
        super(ID, 2, CardRarity.RARE, CHAOS_COLOR);
        magicNumber = baseMagicNumber = 1; // 敏捷
        secondMagic = baseSecondMagic = 1; // 一倍格擋加成
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new DexterityPower(p, magicNumber));
        applyToSelf(new OutstandingVeteranPower(p, secondMagic));
    }
}
