package morimensmod.patches;

import static morimensmod.MorimensMod.modID;

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
                        m.replace(
                            "{ " +
                            "  if ($0.card.cardID.startsWith(\"" + modID + "\")) { " +
                            "    $_ = false; " +
                            "  } else { " +
                            "    $_ = $proceed($$); " +
                            "  } " +
                            "}"
                        );
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
                        m.replace(
                            "{ " +
                            "  if ($0.card.cardID.startsWith(\"" + modID + "\")) { " +
                            "    $_ = false; " +
                            "  } else { " +
                            "    $_ = $proceed($$); " +
                            "  } " +
                            "}"
                        );
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
                        m.replace(
                            "{ " +
                            "  if ($0.card.cardID.startsWith(\"" + modID + "\")) { " +
                            "    $_ = false; " +
                            "  } else { " +
                            "    $_ = $proceed($$); " +
                            "  } " +
                            "}"
                        );
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
                        m.replace(
                            "{ " +
                            "  if ($0.card.cardID.startsWith(\"" + modID + "\")) { " +
                            "    $_ = false; " +
                            "  } else { " +
                            "    $_ = $proceed($$); " +
                            "  } " +
                            "}"
                        );
                }
            };
        }
    }

    // 不知道為什麼無效，明明都印出"return false"了，toggle button還是在
    // @SpirePatch2(clz = SingleCardViewPopup.class, method = "canToggleBetaArt")
    // public static class ReplaceCanToggleBetaArt {
    //     @SpirePrefixPatch
    //     public static SpireReturn<Boolean> Prefix(SingleCardViewPopup __instance, AbstractCard ___card) {
    //         logger.debug("ReplaceCanToggleBetaArt, card:" + ___card.name + ", ID:" + ___card.cardID);
    //         if (___card.cardID.startsWith(modID)) {
    //             logger.debug("return false");
    //             return SpireReturn.Return(false);
    //         }
    //         return SpireReturn.Continue();
    //     }
    // }
}
