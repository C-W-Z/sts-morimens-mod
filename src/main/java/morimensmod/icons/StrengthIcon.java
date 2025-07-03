package morimensmod.icons;

public class StrengthIcon extends AbstractIcon {

    public static final String ID = getID(StrengthIcon.class);
    private static StrengthIcon singleton;

    public StrengthIcon() {
        super(ID);
    }

    @Override
    public StrengthIcon get() {
        if (singleton == null)
            singleton = new StrengthIcon();
        return singleton;
    }
}
