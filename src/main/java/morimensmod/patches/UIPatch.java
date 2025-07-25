package morimensmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static morimensmod.misc.AliemusUI.*;
import static morimensmod.misc.KeyflareUI.*;

public class UIPatch {
    @SpirePatch2(clz = EnergyPanel.class, method = "renderOrb", paramtypez = { SpriteBatch.class })
    public static class RenderPatch{
        public static void Prefix(EnergyPanel __instance, SpriteBatch sb) {
            if (loadAliemusUI())
                renderAliemusUI(sb, __instance.current_x);
            if (loadKeyflareUI())
                renderKeyflareUI(sb, __instance.current_x);
        }
    }

    @SpirePatch(clz = EnergyPanel.class, method = "update")
    public static class UpdatePatch{
        public static void Prefix(EnergyPanel __instance){
            if (loadAliemusUI())
                updateAliemusUI();
            if (loadKeyflareUI())
                updateKeyflareUI();
        }
    }
}
