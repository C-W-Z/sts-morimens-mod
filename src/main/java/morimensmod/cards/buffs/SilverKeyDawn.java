package morimensmod.cards.buffs;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.CardLib.getAllPosses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;
import morimensmod.powers.OnlyUnlimitedPosseTwicePower;

public class SilverKeyDawn extends AbstractBuffCard {
    public final static String ID = makeID(SilverKeyDawn.class.getSimpleName());

    public SilverKeyDawn() {
        super(ID, 0, CardRarity.RARE, CardTarget.NONE);
        magicNumber = baseMagicNumber = 3; // 幾個隨機鑰令中選1個
        exhaust = true;
        selfRetain = true;
        upgradedName = cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            addToBot(new ApplyPowerAction(p, null, new OnlyUnlimitedPosseTwicePower(p, 1)));

        ArrayList<AbstractPosse> posses = getAllPosses();

        for (AbstractPosse posse : posses)
            posse.set((AbstractAwakener) p, PosseType.UNLIMITED);

        Collections.shuffle(posses, new Random(AbstractDungeon.miscRng.randomLong()));
        ArrayList<AbstractCard> choiceCardList = new ArrayList<>(posses.subList(0, Math.min(magicNumber, posses.size())));

        addToBot(new EasyModalChoiceAction(choiceCardList));
    }

    @Override
    public void upp() {
        // upgradeMagicNumber(2);
    }
}
