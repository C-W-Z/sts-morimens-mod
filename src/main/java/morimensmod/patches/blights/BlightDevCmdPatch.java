package morimensmod.patches.blights;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;

import basemod.devcommands.blight.BlightAdd;
import basemod.devcommands.blight.BlightRemove;
import morimensmod.util.BlightLib;

@SpirePatches2({
        @SpirePatch2(clz = BlightAdd.class, method = "extraOptions"),
        @SpirePatch2(clz = BlightRemove.class, method = "extraOptions")
})
public class BlightDevCmdPatch {
    @SpirePostfixPatch
    public static ArrayList<String> Postfix(ArrayList<String> __result) {
        for (AbstractBlight b : BlightLib.blights)
            __result.add(b.blightID.replace(' ', '_'));
        return __result;
    }
}
