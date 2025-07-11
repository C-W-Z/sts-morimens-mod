package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.GiveAndTakePower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class GiveAndTake extends AbstractRouseCard {
    public final static String ID = makeID(GiveAndTake.class.getSimpleName());

    public GiveAndTake() {
        super(ID, 2, CardRarity.UNCOMMON, CHAOS_COLOR);
        magicNumber = baseMagicNumber = GiveAndTakePower.THORNS_PER_AMOUNT; // only for display
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new GiveAndTakePower(p, 1));
    }
}
