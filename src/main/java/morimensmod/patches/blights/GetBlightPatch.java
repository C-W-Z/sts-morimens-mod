package morimensmod.patches.blights;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.helpers.BlightHelper;

import morimensmod.util.BlightLib;

@SpirePatch2(clz = BlightHelper.class, method = "getBlight")
public class GetBlightPatch {
    @SpirePostfixPatch
    public static AbstractBlight Postfix(AbstractBlight __result, String id) {
        if (__result != null)
            return __result;
        AbstractBlight tmp = BlightLib.getBlight(id);
        if (tmp != null)
            return tmp;
        return __result;
    }
}
