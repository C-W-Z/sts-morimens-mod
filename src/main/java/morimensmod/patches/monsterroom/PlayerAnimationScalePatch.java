package morimensmod.patches.monsterroom;

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
import morimensmod.util.MonsterLib;

public class PlayerAnimationScalePatch {

    private static final Logger logger = LogManager.getLogger(PlayerAnimationScalePatch.class);

    @SpirePatch2(clz = AbstractDungeon.class, method = "getMonsterForRoomCreation")
    public static class GetMonsterPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            String key = AbstractDungeon.monsterList.get(0);
            logger.debug("monsterKey: " + key);
            if (!(p() instanceof CustomPlayer))
                return;
            AbstractAnimation animation = ReflectionHacks.getPrivate(p(), CustomPlayer.class, "animation");
            if (!(animation instanceof Animator))
                return;
            MonsterLib.MonsterEncounter encounter = MonsterLib.weakEncounters.get(key);
            if (encounter == null)
                encounter = MonsterLib.strongEncounters.get(key);
            if (encounter == null) {
                logger.debug("monsterKey " + key + " NOT FOUND in MonsterLib");
                return;
            }
            logger.debug("set animScale: " + encounter.animScale);
            ((Animator) animation).setScale(encounter.animScale);
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getEliteMonsterForRoomCreation")
    public static class GetElitePatch {
        @SpirePostfixPatch
        public static void Postfix() {
            String key = AbstractDungeon.eliteMonsterList.get(0);
            logger.debug("eliteKey: " + key);
            if (!(p() instanceof CustomPlayer))
                return;
            AbstractAnimation animation = ReflectionHacks.getPrivate(p(), CustomPlayer.class, "animation");
            if (!(animation instanceof Animator))
                return;
            MonsterLib.MonsterEncounter encounter = MonsterLib.eliteEncounters.get(key);
            if (encounter == null) {
                logger.debug("eliteKey " + key + " NOT FOUND in MonsterLib");
                return;
            }
            logger.debug("set animScale: " + encounter.animScale);
            ((Animator) animation).setScale(encounter.animScale);
        }
    }

    @SpirePatch2(clz = AbstractDungeon.class, method = "getBoss")
    public static class GetBossPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            // TODO
        }
    }
}
