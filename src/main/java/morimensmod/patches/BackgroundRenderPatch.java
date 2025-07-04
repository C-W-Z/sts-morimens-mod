package morimensmod.patches;

// import com.badlogic.gdx.graphics.g2d.SpriteBatch;
// import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
// import com.evacipated.cardcrawl.modthespire.lib.Matcher;
// import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
// import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
// import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
// import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
// import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
// import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
// import com.megacrit.cardcrawl.rooms.AbstractRoom;
// import java.util.ArrayList;
// import javassist.CannotCompileException;
// import javassist.CtBehavior;

// @SpirePatch2(clz = AbstractDungeon.class, method = "render")
// public class BackgroundRenderPatch {
//     @SpireInsertPatch(locator = Locator.class)
//     public static void patch(AbstractDungeon __instance, SpriteBatch sb) {

//     }

//     private static class Locator extends SpireInsertLocator {
//         public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
//             Matcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractRoom.class, "render");
//             return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
//         }
//     }
// }

// patch AbstractScene.
// public abstract void renderCombatRoomBg(SpriteBatch var1);
// public abstract void renderCombatRoomFg(SpriteBatch var1);
