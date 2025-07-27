package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.getPowerAmount;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.MoveFromDrawPileAndChangeCostAction;
import morimensmod.actions.MundusDecreeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.NegentropyPower;

public class SightUnbound extends AbstractEasyCard {
    public final static String ID = makeID(SightUnbound.class.getSimpleName());

    public SightUnbound() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CustomTags.LOOP);
        magicNumber = baseMagicNumber = 1; // 力量
        secondMagic = baseSecondMagic = 1; // 選1張牌
        thirdMagic = baseThirdMagic = 1; // 費用-1
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));

        if (getPowerAmount(p, NegentropyPower.POWER_ID) >= NegentropyPower.INVOKE_AMOUNT) {
            addToBot(new ReducePowerAction(p, p, NegentropyPower.POWER_ID, NegentropyPower.INVOKE_AMOUNT));

            addToBot(new MundusDecreeAction());
        } else {
            addToBot(new MoveFromDrawPileAndChangeCostAction(-thirdMagic));
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
