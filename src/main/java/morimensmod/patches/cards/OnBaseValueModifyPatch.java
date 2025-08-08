package morimensmod.patches.cards;

import static morimensmod.util.Wiz.deck;
import static morimensmod.util.Wiz.isDefendOrAsDefend;
import static morimensmod.util.Wiz.isInCombat;
import static morimensmod.util.Wiz.isStrikeOrAsStrike;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;

public class OnBaseValueModifyPatch {

    @SpirePatch2(clz = CardModifierManager.class, method = "onModifyBaseDamage")
    public static class OnModifyBaseDamagePatch {
        @SpirePostfixPatch
        public static float Postfix(float __result, AbstractCard card, AbstractMonster mo) {
            if (!isInCombat() || deck().contains(card))
                return __result; // do NOT amplify cards in deck or outside combat
            int damageAmplify = 100 + AbstractEasyCard.baseDamageAmplify + AbstractAwakener.baseDamageAmplify;
            if (isStrikeOrAsStrike(card))
                damageAmplify += AbstractEasyCard.baseStrikeDamageAmplify;
            if (damageAmplify == 100)
                return __result;
            return MathUtils.ceil(__result * damageAmplify / 100F);
        }
    }

    @SpirePatch2(clz = CardModifierManager.class, method = "onModifyBaseBlock")
    public static class OnModifyBaseBlockPatch {
        @SpirePostfixPatch
        public static float Postfix(float __result, AbstractCard card) {
            if (!isInCombat() || deck().contains(card))
                return __result; // do NOT amplify cards in deck or outside combat
            int blockAmplify = 100 + AbstractEasyCard.baseBlockAmplify + AbstractAwakener.baseBlockAmplify;
            if (isDefendOrAsDefend(card))
                blockAmplify += AbstractEasyCard.baseDefendBlockAmplify;
            if (blockAmplify == 100)
                return __result;
            return MathUtils.ceil(__result * blockAmplify / 100F);
        }
    }
}
