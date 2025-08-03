package morimensmod.patches;

import java.util.List;
import java.util.stream.Collectors;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.powers.monster.TauntPower;

@SpirePatch2(clz = AbstractCard.class, method = "cardPlayable")
public class CardPlayablePatch {

    @SpirePostfixPatch
    public static boolean Postfix(boolean __result, AbstractCard __instance, AbstractMonster m) {
        if (!__result || m == null || (__instance.target != CardTarget.ENEMY && __instance.target != CardTarget.SELF_AND_ENEMY))
            return __result;

        List<AbstractMonster> tauntMonsters = AbstractDungeon.getMonsters().monsters
                .stream()
                .filter(_m -> !_m.isDeadOrEscaped() && _m.hasPower(TauntPower.POWER_ID))
                .collect(Collectors.toList());

        if (!tauntMonsters.isEmpty() && !tauntMonsters.contains(m)) {
            __instance.cantUseMessage = null;
            return false;
        }

        return __result;
    }
}
