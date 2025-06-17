package morimensmod.interfaces;

import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public interface OnBeforePosse {
    void onBeforePosse(AbstractAwakener awaker, int exhaustKeyflare, PosseType type);
}
