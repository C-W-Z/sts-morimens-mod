package morimensmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.ReflectionHacks;
import morimensmod.characters.Lotan;
import morimensmod.characters.Ramona;
import morimensmod.characters.RamonaTimeworm;
import morimensmod.misc.PosseSelectUI;

import static morimensmod.misc.AliemusUI.*;
import static morimensmod.misc.KeyflareUI.*;

import java.util.ArrayList;

public class UIPatch {
    @SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypez = { SpriteBatch.class })
    public static class RenderPatch {
        public static void Prefix(EnergyPanel _inst, SpriteBatch sb) {
            if (loadAliemusUI())
                renderAliemusUI(sb, _inst.current_x);
            if (loadKeyflareUI())
                renderKeyflareUI(sb, _inst.current_x);
        }
    }

    @SpirePatch(clz = EnergyPanel.class, method = "update")
    public static class UpdatePatch {
        public static void Prefix(EnergyPanel _inst) {
            if (loadAliemusUI())
                updateAliemusUI();
            if (loadKeyflareUI())
                updateKeyflareUI();
        }
    }

    public static final ArrayList<PlayerClass> playerClasses = new ArrayList<>();

    static {
        playerClasses.add(Ramona.Enums.RAMONA);
        playerClasses.add(RamonaTimeworm.Enums.RamonaTimeworm);
        playerClasses.add(Lotan.Enums.LOTAN);
    }

    public static boolean canRenderPosseSelectUI() {
        return playerClasses.contains(CardCrawlGame.chosenCharacter)
                && ((Boolean) ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen,
                        CharacterSelectScreen.class, "anySelected")).booleanValue();
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class RenderButtonPatch {
        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (canRenderPosseSelectUI())
                PosseSelectUI.getUI().render(sb);
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class UpdateButtonPatch {
        public static void Prefix(CharacterSelectScreen _inst) {
            if (canRenderPosseSelectUI())
                PosseSelectUI.getUI().update();
        }
    }
}
