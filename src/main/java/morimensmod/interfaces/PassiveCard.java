package morimensmod.interfaces;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rewards.RewardItem;

public interface PassiveCard {
    default boolean onInitDeck() {
        return false;
    }

    default boolean onBattleStartPreDraw() {
        return false;
    }

    default boolean onVictory(boolean victory) {
        return false;
    }

    default void preRemoveCardFromDeck(AbstractCard card) {}

    default int onRestToChangeHealAmount(int healAmount) {
        return healAmount;
    }

    default ArrayList<RewardItem> onRestToObtainRewards() {
        return null;
    }
}
