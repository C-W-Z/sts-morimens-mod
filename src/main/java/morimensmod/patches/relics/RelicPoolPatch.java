package morimensmod.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.PrismaticShard;

import morimensmod.characters.AbstractAwakener;

@SpirePatch2(clz = AbstractDungeon.class, method = "initializeRelicList")
public class RelicPoolPatch {
    @SpirePrefixPatch
    public static void Prefix() {
        if (!(AbstractDungeon.player instanceof AbstractAwakener))
            return;
        AbstractDungeon.relicsToRemoveOnStart.add(PrismaticShard.ID);
    }
}
