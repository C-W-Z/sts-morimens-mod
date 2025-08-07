package morimensmod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

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

    default boolean onRemoveCardFromDeck(AbstractCard card) {
        return false;
    }

    default int onRestToChangeHealAmount(int healAmount) {
        return healAmount;
    }

    default boolean onRest() {
        return false;
    }
}
