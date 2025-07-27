package morimensmod.patches;

import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import javassist.CtBehavior;
import morimensmod.characters.AbstractAwakener;

@SpirePatch2(clz = UseCardAction.class, method = "update")
public class UseCardActionPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(UseCardAction __instance, AbstractCard ___targetCard) {
        if (!(p() instanceof AbstractAwakener))
            return;
        // 為了命定之劍的回環效果
        if (___targetCard.exhaustOnUseOnce || ___targetCard.exhaust)
            __instance.exhaustCard = true;
        if (__instance.exhaustCard)
            __instance.actionType = ActionType.EXHAUST;
        // 為了消耗算力獲得鑰能
        AbstractAwakener.onAfterUseCard(___targetCard);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // 找出那個 duration == 0.15F 的 if 判斷
            Matcher matcher = new Matcher.FieldAccessMatcher(UseCardAction.class, "duration");
            int[] lines = LineFinder.findInOrder(ctMethodToPatch, matcher);
            // 插入點是 if (duration == 0.15F) 之後一行
            return new int[] { lines[0] + 1 };
        }
    }
}
