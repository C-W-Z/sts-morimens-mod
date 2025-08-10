package morimensmod.cards.chaos.rouse;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.rouse.FleshDetachedPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class FleshDetached extends AbstractRouseCard {
    public final static String ID = makeID(FleshDetached.class.getSimpleName());

    public FleshDetached() {
        super(ID, 2, CardRarity.UNCOMMON, CHAOS_COLOR);
        block = baseBlock = FleshDetachedPower.BLOCK_PER_HEAL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new FleshDetachedPower(p, 1));
    }
}
