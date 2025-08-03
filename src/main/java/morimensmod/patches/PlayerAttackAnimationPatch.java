package morimensmod.patches;

import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.characters.AbstractAwakener;
import morimensmod.config.ModSettings;

@SpirePatch2(clz = AbstractPlayer.class, method = "useCard")
public class PlayerAttackAnimationPatch {
    @SpirePrefixPatch
    public static void Prefix(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (!(__instance instanceof AbstractAwakener))
            return;
        if (c.type == CardType.ATTACK)
            ((AbstractAwakener) p()).setAnimation(ModSettings.PLAYER_ATTACK_ANIM, true);
    }
}
