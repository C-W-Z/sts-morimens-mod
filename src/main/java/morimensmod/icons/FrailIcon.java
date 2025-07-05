package morimensmod.icons;

public class FrailIcon extends AbstractIcon {

    public static final String ID = getID(FrailIcon.class);
    private static FrailIcon singleton;

    public FrailIcon() {
        super(ID);
    }

    @Override
    public FrailIcon get() {
        if (singleton == null)
            singleton = new FrailIcon();
        return singleton;
    }
}
