package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ActiveInjector extends AbstractEasyRelic {
    public static final String ID = makeID(ActiveInjector.class.getSimpleName());

    public ActiveInjector() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
