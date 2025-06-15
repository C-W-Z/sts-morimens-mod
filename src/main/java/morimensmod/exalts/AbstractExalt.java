package morimensmod.exalts;

public abstract class AbstractExalt {
    public int damage;
    public int baseDamage;

    public abstract void exalt();
    public abstract void overExalt();
    public abstract String getExaltTitle();
    public abstract String getExaltDescription();
    public abstract String getOverExaltTitle();
    public abstract String getOverExaltDescription();
}
