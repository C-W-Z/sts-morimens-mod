package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.CardLib.getAllPosses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.misc.PosseType;

public class TheGatedAnswer extends AbstractPosse {

    public final static String ID = makeID(TheGatedAnswer.class.getSimpleName());

    public TheGatedAnswer() {
        super(ID);
    }

    @Override
    public void activate() {
        addToTop(new KeyflareChangeAction(awaker, 200));

        ArrayList<AbstractPosse> posses = getAllPosses();

        for (AbstractPosse p : posses)
            p.set(awaker, PosseType.TMP);

        Collections.shuffle(posses, new Random(AbstractDungeon.miscRng.randomLong()));
        ArrayList<AbstractCard> choiceCardList = new ArrayList<>(posses.subList(0, Math.min(3, posses.size())));
        // ArrayList<AbstractCard> choiceCardList = new ArrayList<>(posses);

        addToTop(new EasyModalChoiceAction(choiceCardList));
    }
}
