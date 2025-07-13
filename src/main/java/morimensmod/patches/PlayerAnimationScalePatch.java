package morimensmod.patches;

import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import morimensmod.misc.Animator;
import morimensmod.monsters.TheVoidClaimsAll;

public class PlayerAnimationScalePatch {

    private static final Logger logger = LogManager.getLogger(PlayerAnimationScalePatch.class);

    @SpirePatch2(clz = AbstractDungeon.class, method = "getMonsterForRoomCreation")
    public static class GetMonsterPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            logger.debug("GetMonsterPatch: " + AbstractDungeon.monsterList.get(0));
            if (!(p() instanceof CustomPlayer))
                return;
            AbstractAnimation animation = ReflectionHacks.getPrivate(p(), CustomPlayer.class, "animation");
            if (!(animation instanceof Animator))
                return;
            if (AbstractDungeon.monsterList.get(0).equals(TheVoidClaimsAll.ID)) {
                logger.debug("set 0.8");
                ((Animator) animation).setScale(0.8F);
            } else
                ((Animator) animation).setScale(1F);
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getEliteMonsterForRoomCreation")
    public static class GetElitePatch {
        @SpirePostfixPatch
        public static void Postfix() {
            logger.debug("GetElitePatch: " + AbstractDungeon.eliteMonsterList.get(0));
            if (!(p() instanceof CustomPlayer))
                return;
            AbstractAnimation animation = ReflectionHacks.getPrivate(p(), CustomPlayer.class, "animation");
            if (!(animation instanceof Animator))
                return;
            if (AbstractDungeon.eliteMonsterList.get(0).equals(TheVoidClaimsAll.ID)) {
                logger.debug("set 0.8");
                ((Animator) animation).setScale(0.8F);
            } else
                ((Animator) animation).setScale(1F);
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getBoss")
    public static class GetBossPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            logger.debug("GetBossPatch: " + AbstractDungeon.bossKey);
            if (!(p() instanceof CustomPlayer))
                return;
            AbstractAnimation animation = ReflectionHacks.getPrivate(p(), CustomPlayer.class, "animation");
            if (!(animation instanceof Animator))
                return;
            if (AbstractDungeon.bossKey.equals(TheVoidClaimsAll.ID)) {
                logger.debug("set 0.8");
                ((Animator) animation).setScale(0.8F);
            } else
                ((Animator) animation).setScale(1F);
        }
    }
}
