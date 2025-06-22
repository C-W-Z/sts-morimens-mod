package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.STATUS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class Soffocation extends AbstractEasyCard {
    public final static String ID = makeID(Soffocation.class.getSimpleName());

    public Soffocation() {
        super(ID, 1, CardType.STATUS, CardRarity.COMMON, CardTarget.NONE, STATUS_COLOR);
        tags.add(CustomTags.STATUS);
        magicNumber = baseMagicNumber = 3; // 中毒
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard)
            applyToSelf(new PoisonPower(p, p, magicNumber));
    }

    @Override
    public void upp() {
    }

    @Override
    public void upgrade() {
    }
}
