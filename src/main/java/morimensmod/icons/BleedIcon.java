package morimensmod.icons;

public class BleedIcon extends AbstractIcon {

    public static final String ID = getID(BleedIcon.class);
    private static BleedIcon singleton;

    public BleedIcon() {
        super(ID);
    }

    @Override
    public BleedIcon get() {
        if (singleton == null)
            singleton = new BleedIcon();
        return singleton;
    }
}
