package morimensmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;

import javassist.CtBehavior;
import morimensmod.cards.buffs.Insight;
import morimensmod.cards.buffs.SilverKeyDawn;

@SpirePatch2(clz = Merchant.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { float.class, float.class,
        int.class })
public class ShopColorlessCardPatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(Merchant __instance, ArrayList<AbstractCard> ___cards2) {
        ___cards2.clear();
        ___cards2.add(new Insight());
        ___cards2.add(new SilverKeyDawn());
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ShopScreen.class, "init");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
