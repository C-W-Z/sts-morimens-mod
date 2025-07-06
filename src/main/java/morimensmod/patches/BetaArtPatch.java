package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class BetaArtPatch {

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class, CardGroup.class })
    public static class OpenPatch1 {
        @SpireInstrumentPatch
        public static ExprEditor Patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("canToggleBetaArt"))
                        m.replace("{ $_ = false; }");
                }
            };
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "open", paramtypez = { AbstractCard.class })
    public static class OpenPatch2 {
        @SpireInstrumentPatch
        public static ExprEditor Patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("canToggleBetaArt"))
                        m.replace("{ $_ = false; }");
                }
            };
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "update")
    public static class UpdatePatch {
        @SpireInstrumentPatch
        public static ExprEditor Patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("canToggleBetaArt"))
                        m.replace("{ $_ = false; }");
                }
            };
        }
    }

    @SpirePatch2(clz = SingleCardViewPopup.class, method = "render")
    public static class RenderPatch {
        @SpireInstrumentPatch
        public static ExprEditor Patch() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("canToggleBetaArt"))
                        m.replace("{ $_ = false; }");
                }
            };
        }
    }
}
