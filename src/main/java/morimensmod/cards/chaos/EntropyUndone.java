package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.rouse.EntropyUndonePower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class EntropyUndone extends AbstractRouseCard {
    public final static String ID = makeID(EntropyUndone.class.getSimpleName());

    public EntropyUndone() {
        super(ID, 3, CardRarity.UNCOMMON, CHAOS_COLOR);
        magicNumber = baseMagicNumber = EntropyUndonePower.KEYFLARE_PER_COMMAND; // 每次獲得的銀鑰能量 only for display
        secondMagic = baseSecondMagic = EntropyUndonePower.MAX_USE_PER_TURN; // 每回合最大觸發次數 only for display
        thirdMagic = baseThirdMagic = EntropyUndonePower.NEGENTROPY_GAIN; // 每次獲得的負熵 only for display
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new EntropyUndonePower(p, 1));
    }
}
