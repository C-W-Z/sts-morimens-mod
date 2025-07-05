package morimensmod.icons;

public class SealIcon extends AbstractIcon {

    public static final String ID = getID(SealIcon.class);
    private static SealIcon singleton;

    public SealIcon() {
        super(ID);
    }

    @Override
    public SealIcon get() {
        if (singleton == null)
            singleton = new SealIcon();
        return singleton;
    }
}
