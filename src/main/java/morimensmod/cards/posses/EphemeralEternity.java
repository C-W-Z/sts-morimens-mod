package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.ULTRA_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.p;

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
import morimensmod.cards.AbstractPosse;
import morimensmod.cards.chaos.Defend;
import morimensmod.cards.chaos.Strike;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class EphemeralEternity extends AbstractPosse {

    public final static String ID = makeID(EphemeralEternity.class.getSimpleName());

    // for register to CardLibrary
    public EphemeralEternity() {
        super(ID, null, PosseType.UNLIMITED);
    }

    public EphemeralEternity(AbstractAwakener p, PosseType type) {
        super(ID, p, type);
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

        if (p().getCardColor() == ULTRA_COLOR) {
            applyToSelf(new StrengthPower(p(), 2));
            applyToSelf(new LoseStrengthPower(p(), 2));
            applyToSelf(new DexterityPower(p(), 1));
            applyToSelf(new LoseDexterityPower(p(), 1));
        }
    }
}
