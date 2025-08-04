package morimensmod.patches.hooks;

import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.PassiveCard;

@SpirePatch2(clz = CardGroup.class, method = "initializeDeck")
public class OnInitializeDeckPatch {

    private static final Logger logger = LogManager.getLogger(OnInitializeDeckPatch.class);
    protected static final ArrayList<PassiveCard> cards = new ArrayList<>();

    @SpirePostfixPatch
    public static void Postfix(CardGroup __instance, CardGroup masterDeck) {
        if (__instance != drawPile())
            return;

        AbstractAwakener.onInitializeDeck();
        AbstractEasyCard.onInitializeDeck();
        if (p() instanceof AbstractAwakener)
            ((AbstractAwakener) p()).getExalt().onInitDeck();

        cards.clear();
        logger.debug("onInitDeck");
        __instance.group.forEach(c -> {
            if (c instanceof PassiveCard) {
                if (((PassiveCard) c).onInitDeck())
                    AbstractDungeon.effectList.add(0, new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                cards.add((PassiveCard) c);
            }
        });
        __instance.group.removeIf(c -> c instanceof PassiveCard);
    }

    // Called in Main Mod File
    public static void onBattleStartPreDraw() {
        logger.debug("onBattleStartPreDraw");
        cards.forEach(c -> c.onBattleStartPreDraw());
        cards.clear();
    }
}
