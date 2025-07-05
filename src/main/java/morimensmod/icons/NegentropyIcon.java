package morimensmod.icons;

public class NegentropyIcon extends AbstractIcon {

    public static final String ID = getID(NegentropyIcon.class);
    private static NegentropyIcon singleton;

    public NegentropyIcon() {
        super(ID);
    }

    @Override
    public NegentropyIcon get() {
        if (singleton == null)
            singleton = new NegentropyIcon();
        return singleton;
    }
}
