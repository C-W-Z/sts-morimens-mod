package morimensmod.relics.starter;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.NegentropyPower;
import morimensmod.relics.AbstractEasyRelic;

public class RamonaTimewormRelic extends AbstractEasyRelic {
    public static final String ID = makeID(RamonaTimewormRelic.class.getSimpleName());

    private static final int NEGENTROPY = 1;
    private static final int NEGENTROPY_REGEN = 1;

    public RamonaTimewormRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, AWAKENER_COLOR);
        counter = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        if (getPowerAmount(p(), NegentropyPower.POWER_ID) < NegentropyPower.INVOKE_AMOUNT)
            applyToSelf(new NegentropyPower(p(), NEGENTROPY));
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (counter >= 1 || !card.hasTag(CustomTags.LOOP))
            return;
        if (getPowerAmount(p(), NegentropyPower.POWER_ID) >= NegentropyPower.INVOKE_AMOUNT) {
            counter++;
            flash();
            applyToSelf(new NegentropyPower(p(), NEGENTROPY_REGEN));
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        counter = 0;
    }

    @Override
    public void atTurnStart() {
        counter = 0;
    }

    @Override
    public void onVictory() {
        counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], NEGENTROPY, NEGENTROPY_REGEN);
    }
}
