package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;

import basemod.BaseMod;
import morimensmod.util.MonsterLib;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: 改Patch DungeonMap，利用其中的bossHb.hovered和renderBossIcon(sb)
@SpirePatch2(clz = MapRoomNode.class, method = "render")
public class MapNodeToolTipPatch {

    private static final Logger logger = LogManager.getLogger(MapNodeToolTipPatch.class);

    private static String cacheBossKey = "";
    private static ArrayList<AbstractMonster> cacheMonsters = null;

    private static String currentID;
    private static String bossName;

    @SpirePostfixPatch
    public static void Postfix(MapRoomNode __instance, float ___SPACING_X, float ___OFFSET_X, float ___OFFSET_Y) {
        if (!__instance.hb.hovered || !(__instance.room instanceof MonsterRoomBoss))
            return;
        logger.debug("bossKey: " + AbstractDungeon.bossKey);
        // if (!MonsterLib.bosses.containsKey(AbstractDungeon.bossKey))
        //     return;
        // if (!cacheBossKey.equals(AbstractDungeon.bossKey)) {
        //     cacheBossKey = new String(AbstractDungeon.bossKey);
        //     cacheMonsters = MonsterLib.bosses.get(AbstractDungeon.bossKey).group.get().monsters;
        // }
        // ArrayList<PowerTip> tips = new ArrayList<>();
        // for (AbstractMonster m : cacheMonsters) {
        //     logger.debug("bossName: " + m.name);
        //     tips.add(new PowerTip(m.name, ""));
        // }
        logger.debug("bossName: " + getBossName());
        // tips.add(new PowerTip(getBossName(), ""));

        // float x = __instance.x * ___SPACING_X + ___OFFSET_X + __instance.offsetX;
        // float y = __instance.y * Settings.MAP_DST_Y + ___OFFSET_Y + DungeonMapScreen.offsetY + __instance.offsetY;

        logger.debug("Pos: " + (InputHelper.mX + 50.0F * Settings.scale) + ", " + InputHelper.mY);
        // TipHelper.queuePowerTips(InputHelper.mX + 50.0F * Settings.scale, InputHelper.mY, tips);
        TipHelper.renderGenericTip(
                InputHelper.mX + 50.0F * Settings.scale,
                InputHelper.mY,
                getBossName(),
                "");
    }

    private static String getBossName() {
        if (AbstractDungeon.bossKey.equals(currentID))
            return bossName;
        currentID = AbstractDungeon.bossKey;
        String encName = MonsterHelper.getEncounterName(currentID);
        String mName = BaseMod.getMonsterName(encName);
        if (!mName.isEmpty())
            bossName = mName;
        else if (!encName.isEmpty())
            bossName = encName;
        else
            bossName = currentID;

        return bossName;
    }
}
