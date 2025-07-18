package morimensmod.relics.boss;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.shuffleIn;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.cards.status.Stagger;
import morimensmod.relics.AbstractEasyRelic;

public class BlackCandle extends AbstractEasyRelic {
    public static final String ID = makeID(BlackCandle.class.getSimpleName());

    private static final int ENERGY = 2;
    private static final int STAGGER_NUM = 1;

    public BlackCandle() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
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
    public void onShuffle() {
        shuffleIn(new Stagger(), STAGGER_NUM);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], ENERGY, STAGGER_NUM);
    }
}
