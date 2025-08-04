package morimensmod.interfaces;

public interface PassiveCard {
    boolean onInitDeck();

    default void onBattleStartPreDraw() {}
}
