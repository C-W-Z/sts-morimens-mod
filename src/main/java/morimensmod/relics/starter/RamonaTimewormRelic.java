package morimensmod.relics.starter;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.*;

import morimensmod.powers.NegentropyPower;
import morimensmod.relics.AbstractEasyRelic;

public class RamonaTimewormRelic extends AbstractEasyRelic {
    public static final String ID = makeID(RamonaTimewormRelic.class.getSimpleName());

    private static final int NEGENTROPY = 1;

    public RamonaTimewormRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, AWAKENER_COLOR);
    }

    @Override
    public void onPlayerEndTurn() {
        applyToSelf(new NegentropyPower(p(), NEGENTROPY));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], NEGENTROPY);
    }
}
