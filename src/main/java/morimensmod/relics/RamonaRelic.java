package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.characters.AbstractAwakener;

public class RamonaRelic extends AbstractEasyRelic {
    public static final String ID = makeID(RamonaRelic.class.getSimpleName());

    private static final int KEYFLARE = 250;
    private static final int COMMAND_NUM = 5;
    private static final int KEYFLARE_REGEN = 1;

    public RamonaRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT);
        counter = 0;
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new KeyflareChangeAction(p(), KEYFLARE));
        addToTop(new RelicAboveCreatureAction(p(), this));
        counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.purgeOnUse || !isCommandCard(card))
            return;
        counter++;
        if (counter % COMMAND_NUM != 0)
            return;

        flash();
        if (p() instanceof AbstractAwakener)
            actB(() -> ((AbstractAwakener) p()).keyflareRegen += KEYFLARE_REGEN);

        counter -= COMMAND_NUM;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + KEYFLARE + DESCRIPTIONS[1] + COMMAND_NUM + DESCRIPTIONS[2] + KEYFLARE_REGEN + DESCRIPTIONS[3];
    }
}
