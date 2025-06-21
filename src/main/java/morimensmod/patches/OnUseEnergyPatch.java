package morimensmod.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;

import morimensmod.characters.AbstractAwakener;

@SpirePatch2(clz = EnergyManager.class, method = "use")
public class OnUseEnergyPatch {

    private static final Logger logger = LogManager.getLogger(OnUseEnergyPatch.class);

    @SpirePostfixPatch
    public static void Posfix(EnergyManager __instance, int e) {
        if (CardCrawlGame.dungeon == null ||
                AbstractDungeon.currMapNode == null ||
                AbstractDungeon.getCurrRoom() instanceof RestRoom ||
                !(AbstractDungeon.player instanceof AbstractAwakener))
            return;
        AbstractAwakener.addLastUsedEnergy(Math.abs(e));
        logger.debug("energy use: " + e);
    }
}
