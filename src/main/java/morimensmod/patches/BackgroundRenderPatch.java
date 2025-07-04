package morimensmod.patches;

import static morimensmod.MorimensMod.makeUIPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import morimensmod.util.TexLoader;

public class BackgroundRenderPatch {

    @SpirePatch2(clz = AbstractDungeon.class, method = "render")
    public static class _BackgroundRenderPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractDungeon __instance, SpriteBatch sb) {
            onPreRoomRender(sb);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractRoom.class, "render");
                return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
            }
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "render")
    public static class CancelOriginBackgroundRenderPatch {
        @SpireInstrumentPatch
        public static ExprEditor removeSceneRenderCalls() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws javassist.CannotCompileException {
                    if ("renderCombatRoomBg".equals(m.getMethodName())
                            || "renderCombatRoomFg".equals(m.getMethodName())) {
                        if (m.getClassName().equals("com.megacrit.cardcrawl.scenes.AbstractScene")) {
                            m.replace("{ /* removed */ }");
                        }
                    }
                }
            };
        }
    }

    public static void onPreRoomRender(SpriteBatch sb) {
        if (AbstractDungeon.rs != AbstractDungeon.RenderScene.NORMAL)
            return;

        sb.setColor(Color.WHITE);
        Texture bg = TexLoader.getTexture(makeUIPath("bg.png"));

        float screenW = Settings.WIDTH;
        float screenH = Settings.HEIGHT;

        float imgW = bg.getWidth();
        float imgH = bg.getHeight();

        float scaleX = screenW / imgW;
        float scaleY = screenH / imgH;

        float coverScale = Math.max(scaleX, scaleY);

        float scaledW = imgW * coverScale;
        float scaledH = imgH * coverScale;

        // 中心對齊
        float drawX = (screenW - scaledW) / 2f;
        float drawY = (screenH - scaledH) / 2f;

        sb.draw(bg, drawX, drawY, scaledW, scaledH);
    }
}
