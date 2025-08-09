package morimensmod.patches.hooks;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.blights.DamageBlight;
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
        logger.info("OnExordiumInit");

        ChaosRelic chaosRelic = (ChaosRelic) AbstractDungeon.player.getRelic(ChaosRelic.ID);
        if (chaosRelic != null) {
            logger.info("chaosRelic.obtainRelic");
            chaosRelic.obtainRelic();
        }

        logger.info("add Blights");
        AbstractDungeon.effectsQueue.add(new AbstractGameEffect() {
            @Override
            public void update() {
                this.isDone = AbstractDungeon.player.hasBlight(DamageBlight.ID);
                if (!this.isDone && AbstractDungeon.getCurrRoom() != null) {
                    AbstractDungeon.getCurrRoom().spawnBlightAndObtain(
                            Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, new DamageBlight());
                    this.isDone = true;
                }
            }

            @Override
            public void render(SpriteBatch sb) {}

            @Override
            public void dispose() {}
        });
    }
}
