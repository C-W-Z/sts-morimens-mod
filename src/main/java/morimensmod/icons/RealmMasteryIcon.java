package morimensmod.icons;

public class RealmMasteryIcon extends AbstractIcon {

    public static final String ID = getID(RealmMasteryIcon.class);
    private static RealmMasteryIcon singleton;

    public RealmMasteryIcon() {
        super(ID);
    }

    @Override
    public RealmMasteryIcon get() {
        if (singleton == null)
            singleton = new RealmMasteryIcon();
        return singleton;
    }
}
