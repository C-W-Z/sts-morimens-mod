package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.GiveAndTakePower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class GiveAndTake extends AbstractRouseCard {
    public final static String ID = makeID(GiveAndTake.class.getSimpleName());

    public GiveAndTake() {
        super(ID, 2, CardRarity.RARE, CHAOS_COLOR);
        magicNumber = baseMagicNumber = GiveAndTakePower.GAIN_THORNS_PER_N_ATTACK; // 每幾次攻擊獲得反擊 only for display
        secondMagic = baseSecondMagic = 1; // 每次獲得多少反擊
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new GiveAndTakePower(p, secondMagic));
    }
}
