package morimensmod.patches;

import static morimensmod.MorimensMod.makeID;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import javassist.CtBehavior;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LockPlayerActionPatch {

    private static final Logger logger = LogManager.getLogger(LockPlayerActionPatch.class);

    @SpirePatch2(clz = AbstractPlayer.class, method = "playCard")
    public static class LockPlayInputPatch {

        private static final UIStrings TEXT = CardCrawlGame.languagePack
                .getUIString(makeID(LockPlayInputPatch.class.getSimpleName()));

        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> patch(AbstractPlayer __instance) {
            // logger.debug("LockPlayInputPatch:" + AbstractAwakener.lockPlayerActions);
            if (AbstractAwakener.lockPlayerActions > 0) {
                logger.debug("LockPlayInputPatch Success: " + AbstractAwakener.lockPlayerActions);
                // showThoughtBubble(TEXT.TEXT[0], 3.0F);
                __instance.releaseCard();
                return SpireReturn.Return(); // 阻止打牌
            }
            if (__instance.hoveredCard instanceof AbstractEasyCard
                    && ((AbstractEasyCard) __instance.hoveredCard).willLockPlayerActions) {
                AbstractAwakener.lockPlayerActions++;
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                // 定位到 queueContains(this.hoveredCard) 的 if
                Matcher matcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "queueContains");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    // @SpirePatch2(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCard.class,
    //         AbstractCreature.class })
    // public static class LockPlayWhenCardUsed {
    //     @SpirePostfixPatch
    //     public static void patch(UseCardAction __instance, AbstractCard card, AbstractCreature target) {
    //         if (card instanceof AbstractEasyCard && ((AbstractEasyCard) card).willLockPlayerActions) {
    //             logger.debug("LockPlayWhenCardUsed:" + card.cardID + ", Before lockPlayerActions:" + AbstractAwakener.lockPlayerActions);
    //             AbstractAwakener.lockPlayerActions++;
    //             logger.debug("LockPlayWhenCardUsed:" + card.cardID + ", After lockPlayerActions:" + AbstractAwakener.lockPlayerActions);
    //         }
    //     }
    // }
}
