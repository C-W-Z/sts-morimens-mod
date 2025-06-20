package morimensmod.cards.posses;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.getAllPosses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public class TheGatedAnswer extends AbstractPosse {

    public final static String ID = makeID(TheGatedAnswer.class.getSimpleName());

    // for register to CardLibrary
    public TheGatedAnswer() {
        this(null, PosseType.UNLIMITED);
    }

    public TheGatedAnswer(AbstractAwakener awaker, PosseType type) {
        super(ID, awaker, type);
    }

    @Override
    public void activate() {
        addToTop(new KeyflareChangeAction(awaker, 200));

        ArrayList<AbstractPosse> posses = getAllPosses();

        for (AbstractPosse p : posses)
            p.set((AbstractAwakener) awaker, PosseType.TMP);

        Collections.shuffle(posses, new Random(AbstractDungeon.miscRng.randomLong()));
        ArrayList<AbstractCard> choiceCardList = new ArrayList<>(posses.subList(0, Math.min(3, posses.size())));
        // ArrayList<AbstractCard> choiceCardList = new ArrayList<>(posses);

        addToTop(new EasyModalChoiceAction(choiceCardList));
    }
}
