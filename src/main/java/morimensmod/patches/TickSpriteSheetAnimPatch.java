package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;

import morimensmod.characters.AbstractAwakener;

@SpirePatch2(clz = CardCrawlGame.class, method = "update")
public class TickSpriteSheetAnimPatch {
    @SpirePostfixPatch
    public static void Posfix(CardCrawlGame __instance) {
        if (CardCrawlGame.dungeon == null ||
                AbstractDungeon.currMapNode == null ||
                AbstractDungeon.getCurrRoom() instanceof RestRoom ||
                !(AbstractDungeon.player instanceof AbstractAwakener) ||
                ((AbstractAwakener) AbstractDungeon.player).anim == null)
            return;
        ((AbstractAwakener) AbstractDungeon.player).anim.tick();
    }
}
