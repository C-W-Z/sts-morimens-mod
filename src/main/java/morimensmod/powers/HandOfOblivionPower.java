package morimensmod.powers;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.discardPile;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.exhaustPile;
import static morimensmod.util.Wiz.hand;
import static morimensmod.util.Wiz.limbo;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class HandOfOblivionPower extends AbstractEasyPower {
    public final static String POWER_ID = makeID(HandOfOblivionPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public HandOfOblivionPower(AbstractCreature owner, int percent) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, percent);
        updateDescription();
        updateExistingStrikes();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        updateExistingStrikes();
    }

    private void updateExistingStrikes() {
        for (AbstractCard c : hand().group) {
            if (!c.hasTag(CardTags.STRIKE))
                continue;
            c.baseDamage = applyedBaseDamage(c);
        }
        for (AbstractCard c : drawPile().group) {
            if (!c.hasTag(CardTags.STRIKE))
                continue;
            c.baseDamage = applyedBaseDamage(c);
        }
        for (AbstractCard c : discardPile().group) {
            if (!c.hasTag(CardTags.STRIKE))
                continue;
            c.baseDamage = applyedBaseDamage(c);
        }
        for (AbstractCard c : exhaustPile().group) {
            if (!c.hasTag(CardTags.STRIKE))
                continue;
            c.baseDamage = applyedBaseDamage(c);
        }
        for (AbstractCard c : limbo().group) {
            if (!c.hasTag(CardTags.STRIKE))
                continue;
            c.baseDamage = applyedBaseDamage(c);
        }
    }

    @Override
    public void onDrawOrDiscard() {
        for (AbstractCard c : hand().group) {
            if (!c.hasTag(CardTags.STRIKE))
                continue;
            c.baseDamage = applyedBaseDamage(c);
        }
    }

    private int applyedBaseDamage(AbstractCard c) {
        if (c.upgradedDamage) {
            AbstractCard tmp = CardLibrary.getCard(c.cardID).makeCopy();
            for (int i = 0; i < c.timesUpgraded; ++i)
                tmp.upgrade();
            return tmp.baseDamage * (100 + amount) / 100;
        }
        return CardLibrary.getCard(c.cardID).baseDamage * (100 + amount) / 100;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}