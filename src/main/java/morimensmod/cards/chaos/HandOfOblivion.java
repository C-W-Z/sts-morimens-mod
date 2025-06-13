package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.Strike;
import morimensmod.powers.HandOfOblivionPower;

public class HandOfOblivion extends AbstractEasyCard {
    public final static String ID = makeID(HandOfOblivion.class.getSimpleName());

    public HandOfOblivion() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        baseMagicNumber = magicNumber = 40; // 打擊增傷%數

        cardsToPreview = new Strike(); // Preview a Strike when hovering over this card.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HandOfOblivionPower(p, magicNumber));
        makeInHand(cardsToPreview);
    }

    @Override
    public void upp() {
        upgradeBaseCost(1); // cost 2 -> 1
    }
}
