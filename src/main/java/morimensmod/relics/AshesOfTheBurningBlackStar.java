package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.deck;
import static morimensmod.util.Wiz.isCommandCard;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class AshesOfTheBurningBlackStar extends AbstractEasyRelic {
    public static final String ID = makeID(AshesOfTheBurningBlackStar.class.getSimpleName());

    private static final int COMMAND_TO_DELETE = 2;
    private static final int STR = 3;

    private boolean cardsSelected = true;

    public AshesOfTheBurningBlackStar() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public void onEquip() {
        cardsSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : (deck().getPurgeableCards()).group)
            if (isCommandCard(card))
                tmp.addToTop(card);
        if (tmp.group.isEmpty()) {
            cardsSelected = true;
            return;
        }
        if (tmp.group.size() <= COMMAND_TO_DELETE) {
            deleteCards(tmp.group);
        } else {
            AbstractDungeon.gridSelectScreen.open(
                    deck().getPurgeableCards(),
                    COMMAND_TO_DELETE,
                    String.format(DESCRIPTIONS[1], COMMAND_TO_DELETE),
                    false, false, false, true);
        }
    }

    @Override
    public void update() {
        super.update();
        if (!cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == COMMAND_TO_DELETE)
            deleteCards(AbstractDungeon.gridSelectScreen.selectedCards);
    }

    public void deleteCards(ArrayList<AbstractCard> group) {
        cardsSelected = true;
        float displayCount = 0.0F;
        for (AbstractCard card : group) {
            card.untip();
            card.unhover();
            AbstractDungeon.topLevelEffects.add(
                    new PurgeCardEffect(card, Settings.WIDTH / 3.0F + displayCount, Settings.HEIGHT / 2.0F));
            displayCount += Settings.WIDTH / 6.0F;
            deck().removeCard(card);
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }

    @Override
    public void atBattleStart() {
        flash();
        applyToSelf(new StrengthPower(p(), STR));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], COMMAND_TO_DELETE, STR);
    }
}
