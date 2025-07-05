package morimensmod.icons;

public class WeakIcon extends AbstractIcon {

    public static final String ID = getID(WeakIcon.class);
    private static WeakIcon singleton;

    public WeakIcon() {
        super(ID);
    }

    @Override
    public WeakIcon get() {
        if (singleton == null)
            singleton = new WeakIcon();
        return singleton;
    }
}
