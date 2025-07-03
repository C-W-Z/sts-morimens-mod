package morimensmod.icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeIconPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.MorimensMod.makeID;

public abstract class AbstractIcon extends AbstractCustomIcon {

    protected static String getID(Class<? extends AbstractIcon> clz) {
        return makeID(clz.getSimpleName().replace("Icon", ""));
    }

    public AbstractIcon(String name) {
        super(name, TexLoader.getTextureAsAtlasRegion(makeIconPath(removeModID(name) + ".png")));
    }

    public abstract AbstractIcon get();
}
