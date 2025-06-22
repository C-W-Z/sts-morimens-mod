package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.STATUS_COLOR;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class Stagger extends AbstractEasyCard {
    public final static String ID = makeID(Stagger.class.getSimpleName());

    public Stagger() {
        super(ID, 2, CardType.STATUS, CardRarity.COMMON, CardTarget.NONE, STATUS_COLOR);
        tags.add(CustomTags.STATUS);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upp() {
    }

    @Override
    public void upgrade() {
    }
}
