package morimensmod.exalts;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public abstract class AbstractExalt {
    public int damage;
    public int baseDamage;
    public int[] multiDamage;

    public int block;
    public int baseBlock;

    public int heal;
    public int baseHeal;

    public int aliemus;
    public int baseAliemus;

    public static int baseDamageAmplify;
    public static int baseBlockAmplify;
    public static int baseHealAmplify;
    public static int baseAliemusAmplify;

    public abstract void exalt();
    public abstract void overExalt();
    public abstract String getExaltTitle();
    public abstract String getExaltDescription();
    public abstract String getOverExaltTitle();
    public abstract String getOverExaltDescription();

    public void onInitDeck() {
        baseDamageAmplify = 0;
        baseBlockAmplify = 0;
        baseAliemusAmplify = 0;
        baseHealAmplify = 0;
    }

    public void onBattleStart() {}

    public void onCardUse(AbstractCard card) {}

    public void onPostBattle(AbstractRoom room) {}

    public void onPlayerTurnStart() {}
}
