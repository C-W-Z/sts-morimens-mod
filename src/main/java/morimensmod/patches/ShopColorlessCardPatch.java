package morimensmod.patches;

import static morimensmod.util.Wiz.p;

import java.util.ArrayList;
import java.util.Collections;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;

import javassist.CtBehavior;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;

@SpirePatch2(clz = Merchant.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { float.class, float.class,
        int.class })
public class ShopColorlessCardPatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(Merchant __instance, ArrayList<AbstractCard> ___cards2) {
        if (!(p() instanceof AbstractAwakener))
            return;

        ___cards2.clear();

        ArrayList<AbstractCard> unrare_buffs = new ArrayList<>();
        ArrayList<AbstractCard> rare_buffs = new ArrayList<>();

        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (!c.hasTag(CustomTags.BUFF))
                continue;
            if (c.rarity == CardRarity.RARE)
                rare_buffs.add(c.makeCopy());
            else
                unrare_buffs.add(c.makeCopy());
        }

        Collections.sort(unrare_buffs);
        Collections.sort(rare_buffs);
        ___cards2.add(unrare_buffs.get(AbstractDungeon.cardRng.random(unrare_buffs.size() - 1)));
        ___cards2.add(rare_buffs.get(AbstractDungeon.cardRng.random(rare_buffs.size() - 1)));
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.MethodCallMatcher(ShopScreen.class, "init");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
