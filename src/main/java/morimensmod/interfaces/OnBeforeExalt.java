package morimensmod.interfaces;

import morimensmod.characters.AbstractAwakener;

public interface OnBeforeExalt {
    void onBeforeExalt(AbstractAwakener awaker, int exhaustAliemus, boolean overExalt);
}
