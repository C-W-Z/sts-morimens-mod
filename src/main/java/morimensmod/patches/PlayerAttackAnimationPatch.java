package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import morimensmod.misc.Animator;

@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
public class PlayerAttackAnimationPatch {
    @SpirePrefixPatch
    public static void Prefix(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (!(__instance instanceof CustomPlayer))
            return;
        AbstractAnimation animation = ReflectionHacks.getPrivate(__instance, CustomPlayer.class, "animation");
        if (!(animation instanceof Animator))
            return;
        if (c.type == CardType.ATTACK) {
            ((Animator) animation).setAnimation("Attack", true);
        }
    }
}
