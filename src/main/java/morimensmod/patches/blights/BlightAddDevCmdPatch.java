package morimensmod.patches.blights;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;

import basemod.devcommands.blight.BlightAdd;
import morimensmod.util.BlightLib;

@SpirePatch2(clz = BlightAdd.class, method = "extraOptions")
public class BlightAddDevCmdPatch {
    @SpirePostfixPatch
    public static ArrayList<String> Postfix(ArrayList<String> __result) {
        for (AbstractBlight b : BlightLib.blights)
            __result.add(b.blightID.replace(' ', '_'));
        return __result;
    }
}
