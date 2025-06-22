package morimensmod.cards.wheel_of_destiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.chaos.Strike;
import morimensmod.patches.CustomTags;
import morimensmod.powers.HandOfOblivionPower;

public class HandOfOblivion extends AbstractEasyCard implements StartupCard {
    public final static String ID = makeID(HandOfOblivion.class.getSimpleName());

    public HandOfOblivion() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, WHEEL_OF_DESTINY_COLOR);
        tags.add(CustomTags.WHEEL_OF_DESTINY);
        magicNumber = baseMagicNumber = 14; // 打擊增傷%數
        secondMagic = baseSecondMagic = 40; // 打擊增傷%數
        cardsToPreview = new Strike(); // Preview a Strike when hovering over this card.
        selfRetain = true;
        prepare = 1;
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
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractEasyCard.baseStrikeDamageAmplify += magicNumber;
                    isDone = true;
                }
            });
        return upgraded;
    }
}
