package morimensmod.icons;

public class AliemusIcon extends AbstractIcon {

    public static final String ID = getID(AliemusIcon.class);
    private static AliemusIcon singleton;

    public AliemusIcon() {
        super(ID);
    }

    @Override
    public AliemusIcon get() {
        if (singleton == null)
            singleton = new AliemusIcon();
        return singleton;
    }
}
