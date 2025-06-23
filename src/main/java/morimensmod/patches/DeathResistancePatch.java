package morimensmod.patches;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;

import javassist.CtBehavior;
import morimensmod.characters.AbstractAwakener;

@SpirePatch2(clz = AbstractPlayer.class, method = "damage")
public class DeathResistancePatch {

    private static final String TEXT = CardCrawlGame.languagePack.getUIString(makeID(DeathResistancePatch.class.getSimpleName())).TEXT[0];

    @SpireInsertPatch(locator = Locator.class)
    public static SpireReturn<Void> interceptDeath(AbstractPlayer __instance, DamageInfo info) {
        if (__instance instanceof AbstractAwakener && ((AbstractAwakener) __instance).tryResistDeath()) {
            // 阻止死亡邏輯
            __instance.currentHealth = 1;
            __instance.healthBarUpdatedEvent();

            AbstractDungeon.effectList.add(
                    new TextAboveCreatureEffect(
                            p().hb.cX - p().animX,
                            p().hb.cY + p().hb.height / 2.0F,
                            TEXT,
                            Color.RED));
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher matcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "isDead");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }
    }
}
