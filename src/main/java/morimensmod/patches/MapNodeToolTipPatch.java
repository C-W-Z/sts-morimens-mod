package morimensmod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.map.DungeonMap;
import basemod.BaseMod;
import morimensmod.util.MonsterLib;
import morimensmod.util.TexLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch2(clz = DungeonMap.class, method = "renderBossIcon")
public class MapNodeToolTipPatch {

    private static final Logger logger = LogManager.getLogger(MapNodeToolTipPatch.class);

    private static String currentID;
    private static String bossName;

    @SpirePostfixPatch
    public static void Postfix(DungeonMap __instance, SpriteBatch sb) {
        if (!__instance.bossHb.hovered || !AbstractDungeon.isScreenUp)
            return;

        logger.debug("bossKey: " + AbstractDungeon.bossKey);
        if (!MonsterLib.bosses.containsKey(AbstractDungeon.bossKey))
            return;
        String[] mapIcons = MonsterLib.bosses.get(AbstractDungeon.bossKey).mapIcons;
        if (mapIcons == null || mapIcons.length == 0)
            return;
        logger.debug("mapIcon: " + mapIcons[0]);
        Texture texture = TexLoader.getTexture(mapIcons[0]);
        logger.debug("Pos: " + InputHelper.mX + ", " + (InputHelper.mY + 100F * Settings.scale));

        float size = 128F * Settings.scale;
        float distance = 100 * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(texture, InputHelper.mX - size / 2F, InputHelper.mY + distance, size, size);
        FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, getBossName(),
                InputHelper.mX, InputHelper.mY + distance / 2, Settings.GOLD_COLOR);
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
