package morimensmod.icons;

public class KeyflareIcon extends AbstractIcon {

    public static final String ID = getID(KeyflareIcon.class);
    private static KeyflareIcon singleton;

    public KeyflareIcon() {
        super(ID);
    }

    @Override
    public KeyflareIcon get() {
        if (singleton == null)
            singleton = new KeyflareIcon();
        return singleton;
    }
}
