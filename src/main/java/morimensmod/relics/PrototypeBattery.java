package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import morimensmod.actions.AliemusChangeAction;

public class PrototypeBattery extends AbstractEasyRelic {
    public static final String ID = makeID(PrototypeBattery.class.getSimpleName());

    private static final int BASE_ALIEMUS = 5;
    private static final int ALIEMUS_PER_ENERGY = 3;

    public PrototypeBattery() {
        super(ID, RelicTier.RARE, LandingSound.SOLID);
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
