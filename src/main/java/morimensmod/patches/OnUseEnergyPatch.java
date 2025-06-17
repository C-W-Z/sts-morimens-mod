package morimensmod.patches;

import static morimensmod.util.Wiz.att;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.characters.AbstractAwakener;

@SpirePatch2(clz = EnergyManager.class, method = "use")
public class OnUseEnergyPatch {
    @SpirePostfixPatch
    public static void Posfix(EnergyManager __instance, int e) {
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null
                && !(AbstractDungeon.getCurrRoom() instanceof RestRoom)
                && AbstractDungeon.player instanceof AbstractAwakener)
            att(new KeyflareChangeAction((AbstractAwakener) AbstractDungeon.player, Math.abs(e) * AbstractAwakener.keyflareRegen));
    }
}
