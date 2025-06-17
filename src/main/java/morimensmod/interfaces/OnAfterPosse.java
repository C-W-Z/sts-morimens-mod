package morimensmod.interfaces;

import morimensmod.characters.AbstractAwakener;
import morimensmod.misc.PosseType;

public interface OnAfterPosse {
    void onAfterPosse(AbstractAwakener awaker, int exhaustKeyflare, PosseType type);
}
