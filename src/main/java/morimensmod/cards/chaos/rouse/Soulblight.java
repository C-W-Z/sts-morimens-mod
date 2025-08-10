package morimensmod.cards.chaos.rouse;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.powers.rouse.SoulblightPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class Soulblight extends AbstractRouseCard {
    public final static String ID = makeID(Soulblight.class.getSimpleName());

    public Soulblight() {
        super(ID, 3, CardRarity.UNCOMMON, CHAOS_COLOR);
        heal = baseHeal = SoulblightPower.HEAL_PER_AMOUNT;
        magicNumber = baseMagicNumber = SoulblightPower.INVOKE_POISON_PERCENT; // 觸發中毒%
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new SoulblightPower(p, 1));
    }
}
