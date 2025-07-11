package morimensmod.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.interfaces.OnPowerModified;

@SpirePatch2(clz = AbstractDungeon.class, method = "onModifyPower")
public class OnPowerModifiedHookPatch {

    private static final Logger logger = LogManager.getLogger(OnPowerModifiedHookPatch.class);

    @SpirePostfixPatch
    public static void afterModifiedPower() {
        logger.debug("OnPowerModifiedHookPatch");

        if (AbstractDungeon.player != null) {
            for (AbstractRelic r : AbstractDungeon.player.relics)
                if (r instanceof OnPowerModified)
                    ((OnPowerModified) r).onPowerModified();
            for (AbstractPower p : AbstractDungeon.player.powers)
                if (p instanceof OnPowerModified)
                    ((OnPowerModified) p).onPowerModified();
            if (AbstractDungeon.player.stance instanceof OnPowerModified)
                ((OnPowerModified) AbstractDungeon.player.stance).onPowerModified();
        }
        if ((AbstractDungeon.getCurrRoom()).monsters != null)
            for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters)
                for (AbstractPower p : m.powers)
                    if (p instanceof OnPowerModified)
                        ((OnPowerModified) p).onPowerModified();
    }
}
