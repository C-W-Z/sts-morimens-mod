package morimensmod.cards.chaos.rouse;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.characters.AbstractAwakener;
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

    @Override
    public void applyPowers() {
        // 只有直接獲得的狂氣在這裡計算加成，回血、回狂和中毒在Power中計算加成
        int aliemusAmplify = 100 + baseAliemusAmplify + AbstractAwakener.baseAliemusAmplify;
        applyedBaseAmplifies(100, 100, 100, aliemusAmplify);
        if (aliemusAmplify != 100) {
            isAliemusModified = true;
            aliemus = baseAliemus;
        }
    }
}
