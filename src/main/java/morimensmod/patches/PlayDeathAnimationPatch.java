package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch2(clz = AbstractPlayer.class, method = "playDeathAnimation")
public class PlayDeathAnimationPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(AbstractPlayer __instance) {
        return SpireReturn.Return();
    }
}
