package morimensmod.interfaces;

public interface PassiveCard {
    default boolean onInitDeck() { return true; }

    default void onBattleStartPreDraw() {}

    default void onVictory(boolean victory) {}
}
