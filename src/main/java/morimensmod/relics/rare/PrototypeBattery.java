package morimensmod.relics.rare;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.relics.AbstractEasyRelic;

public class PrototypeBattery extends AbstractEasyRelic {
    public static final String ID = makeID(PrototypeBattery.class.getSimpleName());

    private static final int BASE_ALIEMUS = 5;
    private static final int ALIEMUS_PER_ENERGY = 3;

    public PrototypeBattery() {
        super(ID, RelicTier.RARE, LandingSound.SOLID, AWAKENER_COLOR);
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        int aliemus = BASE_ALIEMUS + ALIEMUS_PER_ENERGY * EnergyPanel.totalCount;
        addToBot(new AliemusChangeAction(p(), aliemus));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], BASE_ALIEMUS, ALIEMUS_PER_ENERGY);
    }
}
