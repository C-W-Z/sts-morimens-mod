package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.ULTRA_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.getBasicDefend;
import static morimensmod.util.Wiz.getBasicStrike;
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
import morimensmod.characters.AbstractAwakener;

public class EphemeralEternity extends AbstractPosse {

    public final static String ID = makeID(EphemeralEternity.class.getSimpleName());

    public EphemeralEternity() {
        super(ID);

        MultiCardPreview.add(this, getBasicStrike(awaker), getBasicDefend(awaker));
        MultiCardPreview.multiCardPreview.get(this).forEach(c -> {
            CardModifierManager.addModifier(c, new ExhaustModifier());
            CardModifierManager.addModifier(c, new EtherealModifier());
        });
    }

    @Override
    public void activate() {
        addToBot(new GainEnergyAction(1));

        MultiCardPreview.multiCardPreview.get(this).forEach(c -> makeInHand(c));

        if (awaker instanceof AbstractAwakener && ((AbstractAwakener) awaker).getRealmColor() == ULTRA_COLOR) {
            applyToSelf(new StrengthPower(awaker, 2));
            applyToSelf(new LoseStrengthPower(awaker, 2));
            applyToSelf(new DexterityPower(awaker, 1));
            applyToSelf(new LoseDexterityPower(awaker, 1));
        }
    }
}
