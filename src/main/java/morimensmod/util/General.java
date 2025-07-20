package morimensmod.util;

import static morimensmod.MorimensMod.modID;

public class General {
    public static final String arrToString(String[] arr) {
        return arr == null ? "" : String.join(", ", arr);
    }

    public static final String removeModID(String id) {
        return id.replaceAll(modID + ":", "");
    }

    public static final float[] getFloats(int size, float value) {
        float[] result = new float[size];
        for (int i = 0; i < size; i++) {
            result[i] = value;
        }
        return result;
    }
}
