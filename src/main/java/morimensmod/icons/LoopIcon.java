package morimensmod.icons;

public class LoopIcon extends AbstractIcon {

    public static final String ID = getID(LoopIcon.class);
    private static LoopIcon singleton;

    public LoopIcon() {
        super(ID);
    }

    @Override
    public LoopIcon get() {
        if (singleton == null)
            singleton = new LoopIcon();
        return singleton;
    }
}
