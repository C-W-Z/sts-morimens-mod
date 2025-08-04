package morimensmod.cards.wheelOfDestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.PassiveCard;
import morimensmod.powers.wheelOfDestiny.WheelUnseenPower;

public class WheelUnseen extends AbstractWheelOfDestiny implements PassiveCard {
    public final static String ID = makeID(WheelUnseen.class.getSimpleName());

    public WheelUnseen() {
        super(ID, -2, CardRarity.RARE);
        magicNumber = baseMagicNumber = 14; // 銀鑰充能
        secondMagic = baseSecondMagic = WheelUnseenPower.KEYFLARE_LIMIT; // only for display
        thirdMagic = baseThirdMagic = WheelUnseenPower.HAND_CARD_LIMIT; // only for display
    }

    @Override
    public void onBattleStartPreDraw() {
        applyToSelf(new WheelUnseenPower(p(), 1));
        if (upgraded && p() instanceof AbstractAwakener)
            actB(() -> ((AbstractAwakener) p()).keyflareRegen += magicNumber);
    }
}
