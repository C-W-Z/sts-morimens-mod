package morimensmod.patches;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.shop.Merchant;
import com.megacrit.cardcrawl.shop.ShopScreen;

import basemod.ReflectionHacks;
import javassist.CtBehavior;
import morimensmod.characters.AbstractAwakener;
import morimensmod.util.CardLib;

public class ShopPatch {

    @SpirePatch2(clz = Merchant.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { float.class, float.class,
            int.class })
    public static class MerchantPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(Merchant __instance, ArrayList<AbstractCard> ___cards1,
                ArrayList<AbstractCard> ___cards2) {
            if (!(p() instanceof AbstractAwakener))
                return;

            // replace Power (Rouse) card to Wheel of Destiny
            ArrayList<AbstractCard> wheels = CardLib.getRandomWheelOfDestiny(1, CardRarity.COMMON);
            if (!wheels.isEmpty())
                ___cards1.set(___cards1.size() - 1, wheels.get(0));

            ___cards2.clear();

            ___cards2.add(CardLib.getRandomUnrareBuffCard());
            ___cards2.add(CardLib.getRandomBuffCard(CardRarity.RARE));
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(ShopScreen.class, "init");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch2(clz = ShopScreen.class, method = "initCards")
    public static class ShopScreenPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(ShopScreen __instance) {
            if (!(p() instanceof AbstractAwakener))
                return;

            for (AbstractCard c : __instance.coloredCards) {
                if (c.color == WHEEL_OF_DESTINY_COLOR) {
                    float tmpPrice = AbstractCard.getPrice(CardRarity.RARE)
                            * AbstractDungeon.merchantRng.random(0.9F, 1.1F);
                    c.price = (int) tmpPrice;
                    for (AbstractRelic r : AbstractDungeon.player.relics)
                        r.onPreviewObtainCard(c);
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.FieldAccessMatcher(ShopScreen.class, "colorlessCards");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch2(clz = ShopScreen.class, method = "purchaseCard")
    public static class PurchaseCardPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(ShopScreen __instance, AbstractCard hoveredCard,
                float ___notHoveredTimer, float ___speechTimer) {
            if (!(p() instanceof AbstractAwakener))
                return SpireReturn.Continue();

            AbstractCard c;
            if (__instance.colorlessCards.contains(hoveredCard)) {
                AbstractCard.CardRarity tmpRarity = AbstractCard.CardRarity.UNCOMMON;
                if (AbstractDungeon.merchantRng.random() < AbstractDungeon.colorlessRareChance)
                    tmpRarity = AbstractCard.CardRarity.RARE;
                c = CardLib.getRandomBuffCard(tmpRarity);
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    r.onPreviewObtainCard(c);
                __instance.colorlessCards.set(__instance.colorlessCards.indexOf(hoveredCard), c);
            } else {
                c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), hoveredCard.type, false)
                        .makeCopy();
                while (c.color == AbstractCard.CardColor.COLORLESS)
                    c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), hoveredCard.type, false)
                            .makeCopy();
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    r.onPreviewObtainCard(c);
                __instance.coloredCards.set(__instance.coloredCards.indexOf(hoveredCard), c);
            }
            ReflectionHacks.privateMethod(ShopScreen.class, "setPrice", AbstractCard.class).invoke(__instance, c);
            c.current_x = hoveredCard.current_x;
            c.current_y = hoveredCard.current_y;
            c.target_x = c.current_x;
            c.target_y = c.current_y;
            hoveredCard = null;
            InputHelper.justClickedLeft = false;
            ___notHoveredTimer = 1.0F;
            ___speechTimer = MathUtils.random(40.0F, 60.0F);
            __instance.playBuySfx();
            __instance.createSpeech(ShopScreen.getBuyMsg());
            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
                return new int[] { lines[0] + 1 };
            }
        }
    }
}
