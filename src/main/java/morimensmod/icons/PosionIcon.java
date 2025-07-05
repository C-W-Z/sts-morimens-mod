package morimensmod.icons;

public class PosionIcon extends AbstractIcon {

    public static final String ID = getID(PosionIcon.class);
    private static PosionIcon singleton;

    public PosionIcon() {
        super(ID);
    }

    @Override
    public PosionIcon get() {
        if (singleton == null)
            singleton = new PosionIcon();
        return singleton;
    }
}
