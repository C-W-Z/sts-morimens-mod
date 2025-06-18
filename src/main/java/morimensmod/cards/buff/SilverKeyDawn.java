package morimensmod.cards.buff;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.BUFF_COLOR;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.getAllPosses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

public class SilverKeyDawn extends AbstractEasyCard {

    private static final Logger logger = LogManager.getLogger(SilverKeyDawn.class);

    public final static String ID = makeID(SilverKeyDawn.class.getSimpleName());

    public SilverKeyDawn() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.NONE, BUFF_COLOR);
        tags.add(CustomTags.BUFF);
        magicNumber = baseMagicNumber = 3; // 幾個隨機鑰令中選1個
        exhaust = true;
        selfRetain = true;

        willLockPlayerActions = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractPosse> posses = getAllPosses();

        for (AbstractPosse posse : posses)
            posse.set((AbstractAwakener) p, PosseType.UNLIMITED, 2);

        Collections.shuffle(posses, new Random(AbstractDungeon.miscRng.randomLong()));
        ArrayList<AbstractCard> choiceCardList = new ArrayList<>(posses.subList(0, Math.min(magicNumber, posses.size())));

        atb(new EasyModalChoiceAction(choiceCardList));

        logger.debug("Before lockPlayerActions: ", AbstractAwakener.lockPlayerActions);
        AbstractAwakener.lockPlayerActions--;
        logger.debug("After lockPlayerActions: ", AbstractAwakener.lockPlayerActions);
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }
}
