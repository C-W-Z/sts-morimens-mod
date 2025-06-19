package morimensmod.cards.buff;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.BUFF_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.getAllPosses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.AbstractPosse;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;
import morimensmod.patches.CustomTags;
import morimensmod.powers.OnlyUnlimitedPosseTwicePower;

public class SilverKeyDawn extends AbstractEasyCard {
    public final static String ID = makeID(SilverKeyDawn.class.getSimpleName());

    public SilverKeyDawn() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.NONE, BUFF_COLOR);
        tags.add(CustomTags.BUFF);
        magicNumber = baseMagicNumber = 3; // 幾個隨機鑰令中選1個
        exhaust = true;
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new OnlyUnlimitedPosseTwicePower(p, 1));

        ArrayList<AbstractPosse> posses = getAllPosses();

        for (AbstractPosse posse : posses)
            posse.set((AbstractAwakener) p, PosseType.UNLIMITED);

        Collections.shuffle(posses, new Random(AbstractDungeon.miscRng.randomLong()));
        ArrayList<AbstractCard> choiceCardList = new ArrayList<>(posses.subList(0, Math.min(magicNumber, posses.size())));

        atb(new EasyModalChoiceAction(choiceCardList));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}
