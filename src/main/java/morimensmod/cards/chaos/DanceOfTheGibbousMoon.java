package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.powers.rouse.DanceOfTheGibbousMoonPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class DanceOfTheGibbousMoon extends AbstractRouseCard {
    public final static String ID = makeID(DanceOfTheGibbousMoon.class.getSimpleName());

    public DanceOfTheGibbousMoon() {
        super(ID, 2, CardRarity.UNCOMMON, CHAOS_COLOR);
        magicNumber = baseMagicNumber = DanceOfTheGibbousMoonPower.PER_N_CARD;
        heal = baseHeal = DanceOfTheGibbousMoonPower.HEAL_PER_AMOUNT;
        secondMagic = baseSecondMagic = DanceOfTheGibbousMoonPower.ALIEMUS_PER_AMOUNT;
        thirdMagic = baseThirdMagic = DanceOfTheGibbousMoonPower.POISON_PER_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new DanceOfTheGibbousMoonPower(p, 1));
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
