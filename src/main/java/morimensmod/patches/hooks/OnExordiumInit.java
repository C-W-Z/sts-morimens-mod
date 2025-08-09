package morimensmod.patches.hooks;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

import morimensmod.relics.starter.ChaosRelic;

@SpirePatches2({
        @SpirePatch2(clz = Exordium.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractPlayer.class,
                ArrayList.class }),
        @SpirePatch2(clz = Exordium.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractPlayer.class,
                SaveFile.class })
})
public class OnExordiumInit {

    private static Logger logger = LogManager.getLogger(OnExordiumInit.class);

    @SpirePostfixPatch
    public static void Postfix() {
        if (AbstractDungeon.floorNum > 1)
            return;

        logger.info("OnExordiumInit");

        ChaosRelic chaosRelic = (ChaosRelic) AbstractDungeon.player.getRelic(ChaosRelic.ID);
        if (chaosRelic != null) {
            logger.info("chaosRelic.obtainRelic");
            chaosRelic.obtainRelic();
        }

        // logger.info("add Blights");
        // if (ConfigPanel.DAMAGE_BLIGHT_LVL > 0)
        //     BlightLib.addBlight(new DamageBlight(ConfigPanel.DAMAGE_BLIGHT_LVL));
        // BlightLib.addBlight(new HealthBlight(10));
    }
}
