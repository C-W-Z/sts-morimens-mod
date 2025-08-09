package morimensmod.patches.hooks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.blights.DamageBlight;
import morimensmod.relics.starter.ChaosRelic;

@SpirePatch2(clz = NeowRoom.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { boolean.class })
public class OnEntryNeowRoom {

    @SpirePostfixPatch
    public static void Postfix(NeowRoom __instance) {
        ChaosRelic chaosRelic = (ChaosRelic) AbstractDungeon.player.getRelic(ChaosRelic.ID);
        if (chaosRelic != null) {
            chaosRelic.obtainRelic();
        }

        AbstractDungeon.effectsQueue.add(new AbstractGameEffect() {
            @Override
            public void update() {
                this.isDone = AbstractDungeon.player.hasBlight(DamageBlight.ID);
                if (!this.isDone && AbstractDungeon.player != null && AbstractDungeon.getCurrRoom() != null) {
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
