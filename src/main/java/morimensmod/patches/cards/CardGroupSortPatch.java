package morimensmod.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.cards.posses.AbstractPosse;

@SpirePatch2(cls = "com.megacrit.cardcrawl.cards.CardGroup$CardNameComparator", method = "compare", paramtypez = {
        AbstractCard.class, AbstractCard.class })
public class CardGroupSortPatch {
    @SpirePrefixPatch
    public static SpireReturn<Integer> Prefix(AbstractCard c1, AbstractCard c2) {
        if (c1 instanceof AbstractPosse && c2 instanceof AbstractPosse)
            return SpireReturn.Return(c1.compareTo(c2));
        return SpireReturn.Continue();
    }
}
