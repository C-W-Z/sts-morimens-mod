package morimensmod.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import javassist.CtBehavior;
import morimensmod.interfaces.PassiveCard;

public class BottledTornadoParch {

    @SpirePatch2(clz = BottledTornado.class, method = "onEquip")
    public static class OnEquipPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<Void> Insert(BottledTornado __instance) {

            CardGroup group = new CardGroup(CardGroupType.UNSPECIFIED);
            AbstractDungeon.player.masterDeck.getPurgeableCards().getPowers().group.forEach(c -> {
                if (c instanceof PassiveCard)
                    return;
                group.addToBottom(c);
            });

            AbstractDungeon.gridSelectScreen.open(group, 1,
                    __instance.DESCRIPTIONS[1] + __instance.name + LocalizedStrings.PERIOD,
                    false, false, false, false);

            return SpireReturn.Return();
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher matcher = new Matcher.MethodCallMatcher(GridCardSelectScreen.class, "open");
                return LineFinder.findInOrder(ctMethodToPatch, matcher);
            }
        }
    }

    @SpirePatch2(clz = BottledTornado.class, method = "canSpawn")
    public static class CanSpawnPatch {

        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix() {
            return SpireReturn.Return(AbstractDungeon.player.masterDeck.group.stream()
                    .anyMatch(c -> c.type == CardType.POWER && !(c instanceof PassiveCard)));
        }
    }
}
