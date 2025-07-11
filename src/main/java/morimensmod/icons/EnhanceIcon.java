package morimensmod.icons;

public class EnhanceIcon extends AbstractIcon {

    public static final String ID = getID(EnhanceIcon.class);
    private static EnhanceIcon singleton;

    public EnhanceIcon() {
        super(ID);
    }

    @Override
    public EnhanceIcon get() {
        if (singleton == null)
            singleton = new EnhanceIcon();
        return singleton;
    }
}
