package morimensmod.icons;

public class BarrierIcon extends AbstractIcon {

    public static final String ID = getID(BarrierIcon.class);
    private static BarrierIcon singleton;

    public BarrierIcon() {
        super(ID);
    }

    @Override
    public BarrierIcon get() {
        if (singleton == null)
            singleton = new BarrierIcon();
        return singleton;
    }
}
