package morimensmod.exalts;

public abstract class AbstractExalt {
    public int damage;
    public int baseDamage;

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

    public static void onBattleStart() {
        baseDamageAmplify = 0;
        baseBlockAmplify = 0;
        baseAliemusAmplify = 0;
        baseHealAmplify = 0;
    }
}
