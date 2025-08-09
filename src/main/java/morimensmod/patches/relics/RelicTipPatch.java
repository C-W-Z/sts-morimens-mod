package morimensmod.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch2(clz = AbstractRelic.class, method = "relicTip")
public class RelicTipPatch {
    @SpirePrefixPatch
    public static void Insert() {
        if (TipTracker.relicCounter >= 1 && !TipTracker.tips.get("RELIC_TIP"))
            TipTracker.neverShowAgain("RELIC_TIP");
    }
}
