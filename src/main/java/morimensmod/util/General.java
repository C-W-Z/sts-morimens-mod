package morimensmod.util;

import static morimensmod.MorimensMod.modID;

public class General {
    public static final boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

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

    public static final String normalizeWhitespace(String input) {
        // 檢查空值
        if (input == null) {
            return null;
        }

        // 先去除首尾空白，再將連續空白（包括空格、tab、換行等）替換為單個空格
        String result = input.trim()
                .replaceAll("#y", "")
                .replaceAll("#b", "")
                .replaceAll("#g", "")
                .replaceAll("#r", "")
                .replaceAll("#p", "")
                .replaceAll("[\\s\\t\\n\\r]+", " ");

        return result;
    }
}
