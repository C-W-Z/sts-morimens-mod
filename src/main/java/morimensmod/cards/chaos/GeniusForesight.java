package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.EasyModalChoiceCard;
import morimensmod.patches.enums.CustomTags;

public class GeniusForesight extends AbstractEasyCard {
    public final static String ID = makeID(GeniusForesight.class.getSimpleName());

    public GeniusForesight() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        draw = baseDraw = 1;
        magicNumber = baseMagicNumber = 1; // 能量
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choiceCardList = new ArrayList<>();

        AbstractCard choiceA = new EasyModalChoiceCard(
                ID,
                cardImgID,
                cardStrings.EXTENDED_DESCRIPTION[0],
                cardStrings.EXTENDED_DESCRIPTION[1],
                () -> addToBot(new GainEnergyAction(magicNumber)),
                CHAOS_COLOR,
                CustomTags.COMMAND);

        choiceA.magicNumber = choiceA.baseMagicNumber = magicNumber;

        AbstractCard choiceB = new EasyModalChoiceCard(
                ID,
                cardImgID,
                cardStrings.EXTENDED_DESCRIPTION[2],
                cardStrings.EXTENDED_DESCRIPTION[3],
                () -> addToBot(new DrawCardAction(draw)),
                CHAOS_COLOR,
                CustomTags.COMMAND);

        choiceB.draw = choiceB.baseDraw = draw;

        choiceCardList.add(choiceA);
        choiceCardList.add(choiceB);
        addToBot(new EasyModalChoiceAction(choiceCardList));
    }

    @Override
    public void upp() {
        upgradeDraw(1);
        upgradeMagicNumber(1);
    }
}
