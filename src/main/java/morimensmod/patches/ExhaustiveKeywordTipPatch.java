package morimensmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField.ExhaustiveFields;
import com.evacipated.cardcrawl.mod.stslib.patches.CommonKeywordIconsPatches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;

@SpirePatch2(clz = CommonKeywordIconsPatches.class, method = "addKeywords")
public class ExhaustiveKeywordTipPatch {
    @SpirePrefixPatch
    public static void Prefix(AbstractCard c, ArrayList<String> kws) {
        if (ExhaustiveFields.exhaustive.get(c) == 1)
            kws.add(GameDictionary.EXHAUST.NAMES[0]);
    }
}
