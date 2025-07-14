package morimensmod.util;

import static morimensmod.MorimensMod.modID;

public class General {
    public static String arrToString(String[] arr) {
        return arr == null ? "" : String.join(", ", arr);
    }

    public static String removeModID(String id) {
        return id.replaceAll(modID + ":", "");
    }

    public static float[] getFloats(int size, float value) {
        float[] result = new float[size];
        for (int i = 0; i < size; i++) {
            result[i] = value;
        }
        return result;
    }
}
