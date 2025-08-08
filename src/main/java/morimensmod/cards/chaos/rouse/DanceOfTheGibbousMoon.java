package morimensmod.cards.chaos.rouse;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.cards.buffs.MarvelousCuisine;
import morimensmod.powers.rouse.DanceOfTheGibbousMoonPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class DanceOfTheGibbousMoon extends AbstractRouseCard {
    public final static String ID = makeID(DanceOfTheGibbousMoon.class.getSimpleName());

    public DanceOfTheGibbousMoon() {
        super(ID, 2, CardRarity.UNCOMMON, CHAOS_COLOR);
        magicNumber = baseMagicNumber = DanceOfTheGibbousMoonPower.PER_N_CARD;
        heal = baseHeal = DanceOfTheGibbousMoonPower.HEAL_PER_AMOUNT;
        secondMagic = baseSecondMagic = DanceOfTheGibbousMoonPower.ALIEMUS_PER_AMOUNT;
        thirdMagic = baseThirdMagic = DanceOfTheGibbousMoonPower.POISON_PER_AMOUNT;
        cardsToPreview = new MarvelousCuisine();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new DanceOfTheGibbousMoonPower(p, 1));
    }
}
