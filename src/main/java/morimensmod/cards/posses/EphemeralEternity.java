package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.ULTRA_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
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

        MultiCardPreview.add(this, new Strike(), new Defend());
        MultiCardPreview.multiCardPreview.get(this).forEach(c -> {
            CardModifierManager.addModifier(c, new ExhaustModifier());
            CardModifierManager.addModifier(c, new EtherealModifier());
        });
    }

    @Override
    public void activate() {
        addToBot(new GainEnergyAction(1));

        MultiCardPreview.multiCardPreview.get(this).forEach(c -> makeInHand(c));

        if (awaker.getCardColor() == ULTRA_COLOR) {
            applyToSelf(new StrengthPower(awaker, 2));
            applyToSelf(new LoseStrengthPower(awaker, 2));
            applyToSelf(new DexterityPower(awaker, 1));
            applyToSelf(new LoseDexterityPower(awaker, 1));
        }
    }
}
