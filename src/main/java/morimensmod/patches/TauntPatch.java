package morimensmod.patches;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.WizArt.showThoughtBubble;

import java.util.List;
import java.util.stream.Collectors;

import com.evacipated.cardcrawl.mod.stslib.cards.targeting.SelfOrEnemyTargeting;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.config.ModSettings;
import morimensmod.powers.monster.TauntPower;

@SpirePatch2(clz = AbstractCard.class, method = "cardPlayable")
public class TauntPatch {

    private static final String TEXT = CardCrawlGame.languagePack.getUIString(makeID(TauntPatch.class.getSimpleName())).TEXT[0];

    @SpirePostfixPatch
    public static boolean Postfix(boolean __result, AbstractCard __instance, AbstractMonster m) {
        if (!__result || p().hoveredCard != __instance)
            return __result;

        List<AbstractMonster> tauntMonsters = AbstractDungeon.getMonsters().monsters
                .stream()
                .filter(_m -> !_m.isDeadOrEscaped() && _m.hasPower(TauntPower.POWER_ID))
                .collect(Collectors.toList());

        if (tauntMonsters.isEmpty())
            return __result;

        // AbstractMonster hoveredMonster = ReflectionHacks.getPrivate(p(), AbstractPlayer.class, "hoveredMonster");
        // if (hoveredMonster == null)
        //     return __result;

        if (__instance.target == SelfOrEnemyTargeting.SELF_OR_ENEMY) {
            SelfOrEnemyTargeting handler = (SelfOrEnemyTargeting) CustomTargeting.targetingMap.getOrDefault(__instance.target, null);
            if (!(handler.getHovered() instanceof AbstractMonster))
                return __result;
            m = (AbstractMonster) handler.getHovered();
        }
        else if (m == null || (__instance.target != CardTarget.ENEMY && __instance.target != CardTarget.SELF_AND_ENEMY))
            return __result;

        if (!tauntMonsters.contains(m)) {
            showThoughtBubble(TEXT, ModSettings.UI_THOUGHT_BUBBLE_TIME);
            __instance.cantUseMessage = null;
            return false;
        }

        return __result;
    }
}
