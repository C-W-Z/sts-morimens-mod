package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.shuffleIn;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.cards.status.Stagger;

public class BlackCandle extends AbstractEasyRelic {
    public static final String ID = makeID(BlackCandle.class.getSimpleName());

    private static final int STAGGER_NUM = 1;

    public BlackCandle() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT);
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 2;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 2;
    }

    @Override
    public void onShuffle() {
        shuffleIn(new Stagger(), STAGGER_NUM);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], STAGGER_NUM);
    }
}
