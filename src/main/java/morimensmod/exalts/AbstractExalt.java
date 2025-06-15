package morimensmod.exalts;

import com.megacrit.cardcrawl.localization.UIStrings;

public abstract class AbstractExalt {

    protected UIStrings UI_STRINGS;

    public int damage;
    public int baseDamage;

    public abstract void exalt();
    public abstract void overExalt();
    public abstract String getExaltTitle();
    public abstract String getExaltDescription();
    public abstract String getOverExaltTitle();
    public abstract String getOverExaltDescription();
}
