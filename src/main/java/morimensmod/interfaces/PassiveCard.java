package morimensmod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface PassiveCard {
    default boolean onInitDeck() { return true; }

    default void onBattleStartPreDraw() {}

    default boolean onVictory(boolean victory) { return false; }

    default boolean onRemoveCardFromDeck(AbstractCard card) { return false; }
}
