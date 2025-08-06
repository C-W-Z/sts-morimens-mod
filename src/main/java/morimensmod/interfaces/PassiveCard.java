package morimensmod.interfaces;

public interface PassiveCard {
    default boolean onInitDeck() { return true; }

    default void onBattleStartPreDraw() {}

    default boolean onVictory(boolean victory) { return false; }
}
