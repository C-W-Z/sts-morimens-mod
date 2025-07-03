package morimensmod.util;

import static morimensmod.MorimensMod.modID;

public class General {
    public static String arrToString(String[] arr) {
        return arr == null ? "" : String.join(", ", arr);
    }

    public static String removeModID(String id) {
        return id.replaceAll(modID + ":", "");
    }
}
