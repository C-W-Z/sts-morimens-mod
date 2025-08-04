package morimensmod.cards.wheelOfDestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.chaos.Strike;
import morimensmod.powers.wheelOfDestiny.HandOfOblivionPower;

public class HandOfOblivion extends AbstractWheelOfDestiny implements StartupCard {
    public final static String ID = makeID(HandOfOblivion.class.getSimpleName());

    public HandOfOblivion() {
        super(ID, 2, CardRarity.UNCOMMON);
        magicNumber = baseMagicNumber = 28; // 打擊增傷%數
        secondMagic = baseSecondMagic = 40; // 打擊增傷%數
        cardsToPreview = new Strike(); // Preview a Strike when hovering over this card.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HandOfOblivionPower(p, secondMagic));
        makeInHand(cardsToPreview);
    }

    @Override
    public void upp() {
        upgradeBaseCost(1); // cost 2 -> 1
    }

    @Override
    public boolean atBattleStartPreDraw() {
        if (upgraded)
            actB(() -> AbstractEasyCard.baseStrikeDamageAmplify += magicNumber);
        return upgraded;
    }
}
