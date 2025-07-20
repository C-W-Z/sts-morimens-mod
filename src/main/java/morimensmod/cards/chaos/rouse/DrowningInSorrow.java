package morimensmod.cards.chaos.rouse;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.rouse.DrowningInSorrowPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class DrowningInSorrow extends AbstractRouseCard {
    public final static String ID = makeID(DrowningInSorrow.class.getSimpleName());

    public DrowningInSorrow() {
        super(ID, 1, CardRarity.UNCOMMON, CHAOS_COLOR);
        magicNumber = baseMagicNumber = DrowningInSorrowPower.POISON_PER_AMOUNT; // 每回合施加中毒
        secondMagic = baseSecondMagic = DrowningInSorrowPower.POISON_ALIEMUS_SCALE_PER_AMOUNT; // 狂氣倍數 only for display
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new DrowningInSorrowPower(p, 1));
    }
}
