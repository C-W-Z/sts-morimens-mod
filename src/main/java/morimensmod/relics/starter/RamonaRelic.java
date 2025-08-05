package morimensmod.relics.starter;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.actions.KeyflareRegenChangeAction;
import morimensmod.relics.AbstractEasyRelic;

public class RamonaRelic extends AbstractEasyRelic {
    public static final String ID = makeID(RamonaRelic.class.getSimpleName());

    private static final int KEYFLARE = 250;
    private static final int COMMAND_NUM = 1;
    private static final int KEYFLARE_REGEN = 1;

    public RamonaRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, AWAKENER_COLOR);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new KeyflareChangeAction(p(), KEYFLARE));
        addToTop(new RelicAboveCreatureAction(p(), this));
        // counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.purgeOnUse || !isCommandCard(card))
            return;
        // counter++;
        // if (counter < COMMAND_NUM)
        //     return;
        // counter -= COMMAND_NUM;

        flash();
        addToBot(new KeyflareRegenChangeAction(p(), KEYFLARE_REGEN));
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], KEYFLARE, COMMAND_NUM, KEYFLARE_REGEN);
    }
}
