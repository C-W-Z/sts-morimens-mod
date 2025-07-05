package morimensmod.icons;

public class ElationIcon extends AbstractIcon {

    public static final String ID = getID(ElationIcon.class);
    private static ElationIcon singleton;

    public ElationIcon() {
        super(ID);
    }

    @Override
    public ElationIcon get() {
        if (singleton == null)
            singleton = new ElationIcon();
        return singleton;
    }
}
