package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.discardPile;
import static morimensmod.util.Wiz.powerAmount;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.MoveFromDrawPileAndChangeCostAction;
import morimensmod.actions.MundusDecreeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.PileModalSelectCard;
import morimensmod.patches.CustomTags;
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

        ArrayList<AbstractCard> cardList = new ArrayList<>();

        if (powerAmount(p, NegentropyPower.POWER_ID) >= NegentropyPower.INVOKE_AMOUNT) {
            addToBot(new ReducePowerAction(p, p, NegentropyPower.POWER_ID, NegentropyPower.INVOKE_AMOUNT));

            for (AbstractCard c : drawPile().group) {
                cardList.add(new PileModalSelectCard(c, () -> addToTop(new MundusDecreeAction(c))));
            }

            for (AbstractCard c : discardPile().group) {
                cardList.add(new PileModalSelectCard(c, () -> addToTop(new MundusDecreeAction(c))));
            }
        }
        else
        {
            for (AbstractCard c : drawPile().group) {
                cardList.add(new PileModalSelectCard(c,
                        () -> addToTop(new MoveFromDrawPileAndChangeCostAction(c, -thirdMagic))));
            }
        }

        addToBot(new EasyModalChoiceAction(cardList));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
