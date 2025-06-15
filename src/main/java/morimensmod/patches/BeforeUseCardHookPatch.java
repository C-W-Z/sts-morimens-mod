package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.interfaces.OnBeforeUseCard;

@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
public class BeforeUseCardHookPatch {
    @SpirePrefixPatch
    public static void beforeUseCard(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
        // 呼叫所有 Power 的自訂 hook
        for (AbstractPower p : __instance.powers) {
            if (p instanceof OnBeforeUseCard) {
                ((OnBeforeUseCard) p).onBeforeUseCard(c, __instance, monster);
            }
        }

        // 呼叫所有遺物的 hook
        for (AbstractRelic r : __instance.relics) {
            if (r instanceof OnBeforeUseCard) {
                ((OnBeforeUseCard) r).onBeforeUseCard(c, __instance, monster);
            }
        }

        // 呼叫姿態（Stance）
        if (__instance.stance instanceof OnBeforeUseCard) {
            ((OnBeforeUseCard)__instance.stance).onBeforeUseCard(c, __instance, monster);
        }
    }
}
