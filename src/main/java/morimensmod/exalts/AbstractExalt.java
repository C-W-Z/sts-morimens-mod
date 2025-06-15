package morimensmod.exalts;

public abstract class AbstractExalt {
    public int damage;
    public int baseDamage;

    public int aliemusNumber;
    public int baseAliemusNumber;

    public static int baseDamageAmplify;
    public static int baseAliemusNumberAmplify;

    public abstract void exalt();
    public abstract void overExalt();
    public abstract String getExaltTitle();
    public abstract String getExaltDescription();
    public abstract String getOverExaltTitle();
    public abstract String getOverExaltDescription();

    public static void onBattleStart() {
        baseDamageAmplify = 0;
        baseAliemusNumberAmplify = 0;
    }
}
