package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.ULTRA_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.EtherealModifier;
import morimensmod.cardmodifiers.ExhaustModifier;
import morimensmod.cards.chaos.Defend;
import morimensmod.cards.chaos.Strike;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class EphemeralEternity extends AbstractPosse {

    public final static String ID = makeID(EphemeralEternity.class.getSimpleName());

    // for register to CardLibrary
    public EphemeralEternity() {
        this(null, PosseType.UNLIMITED);
    }

    public EphemeralEternity(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
        previewCards.add(new Strike());
        previewCards.add(new Defend());
        cardsToPreview = previewCards.get(0);
    }

    @Override
    public void activate() {
        atb(new GainEnergyAction(1));

        AbstractCard strike = new Strike();
        CardModifierManager.addModifier(strike, new ExhaustModifier());
        CardModifierManager.addModifier(strike, new EtherealModifier());
        atb(new MakeTempCardInHandAction(strike));

        AbstractCard defend = new Defend();
        CardModifierManager.addModifier(defend, new ExhaustModifier());
        CardModifierManager.addModifier(defend, new EtherealModifier());
        atb(new MakeTempCardInHandAction(defend));

        if (awaker.getCardColor() == ULTRA_COLOR) {
            applyToSelf(new StrengthPower(awaker, 2));
            applyToSelf(new LoseStrengthPower(awaker, 2));
            applyToSelf(new DexterityPower(awaker, 1));
            applyToSelf(new LoseDexterityPower(awaker, 1));
        }
    }
}
