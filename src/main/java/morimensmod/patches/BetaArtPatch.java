package morimensmod.patches;

import static morimensmod.MorimensMod.modID;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

// 不能用PrefixPatch，因為Basemod有用PostfixPatch會把SpireReturn的回傳值替換掉
@SpirePatch2(clz = SingleCardViewPopup.class, method = "canToggleBetaArt")
public class BetaArtPatch {
    @SpirePostfixPatch
    public static boolean Postfix(boolean __result, SingleCardViewPopup __instance, AbstractCard ___card) {
        if (___card.cardID.startsWith(modID))
            return false;
        return __result;
    }
}
