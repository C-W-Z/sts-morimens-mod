package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.powers.FleshDetachedPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class FleshDetached extends AbstractRouseCard {
    public final static String ID = makeID(FleshDetached.class.getSimpleName());

    public FleshDetached() {
        super(ID, 2, CardRarity.COMMON, CHAOS_COLOR);
        block = baseBlock = FleshDetachedPower.BLOCK_PER_HEAL;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new FleshDetachedPower(p, 1));
    }

    @Override
    public void applyPowers() {
        // 只有直接獲得的狂氣在這裡計算加成，格擋在Power中計算加成
        int aliemusAmplify = 100 + baseAliemusAmplify + AbstractAwakener.baseAliemusAmplify;
        applyedBaseAmplifies(100, 100, 100, aliemusAmplify);
        if (aliemusAmplify != 100) {
            isAliemusModified = true;
            aliemus = baseAliemus;
        }
    }
}
