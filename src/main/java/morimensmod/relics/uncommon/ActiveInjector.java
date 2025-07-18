package morimensmod.relics.uncommon;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.relics.AbstractEasyRelic;

public class ActiveInjector extends AbstractEasyRelic {
    public static final String ID = makeID(ActiveInjector.class.getSimpleName());

    private static final int ENERGY = 1;

    public ActiveInjector() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += ENERGY;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= ENERGY;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], ENERGY);
    }
}
