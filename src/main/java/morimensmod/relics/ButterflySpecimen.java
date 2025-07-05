package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class ButterflySpecimen extends AbstractEasyRelic {
    public static final String ID = makeID(ButterflySpecimen.class.getSimpleName());

    private static final int BLOCK_PER_HEAL = 4;
    private static final int DEXTERITY = 1;
    private static final int DEXTERITY_TURN = 3;

    public ButterflySpecimen() {
        super(ID, RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        flash();
        addToTop(new GainBlockAction(p(), BLOCK_PER_HEAL));
        return super.onPlayerHeal(healAmount);
    }

    @Override
    public void atBattleStart() {
        counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (counter != -1)
            counter++;
        if (counter == DEXTERITY_TURN) {
            flash();
            addToBot(new RelicAboveCreatureAction(p(), this));
            applyToSelf(new DexterityPower(p(), DEXTERITY));
            counter = -1;
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], BLOCK_PER_HEAL, DEXTERITY_TURN, DEXTERITY);
    }
}
