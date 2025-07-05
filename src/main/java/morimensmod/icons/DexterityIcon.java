package morimensmod.icons;

public class DexterityIcon extends AbstractIcon {

    public static final String ID = getID(DexterityIcon.class);
    private static DexterityIcon singleton;

    public DexterityIcon() {
        super(ID);
    }

    @Override
    public DexterityIcon get() {
        if (singleton == null)
            singleton = new DexterityIcon();
        return singleton;
    }
}
