package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import morimensmod.cards.buffs.Insight;

public class PrasnoMirror extends AbstractEasyRelic {
    public static final String ID = makeID(PrasnoMirror.class.getSimpleName());

    private static final int DRAW_PER_TURN = 1;
    private static final int INITIAL_INSIGHT = 1;
    private static final int PER_N_BATTLE = 1;
    private static final int INSIGHT_INCREASE = 1;
    private static final int MAX_INSIGHT = 4;

    public PrasnoMirror() {
        super(ID, RelicTier.RARE, LandingSound.CLINK);
        counter = INITIAL_INSIGHT;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.masterHandSize += DRAW_PER_TURN;
    }

    @Override
    public void onUnequip() {
        AbstractDungeon.player.masterHandSize -= DRAW_PER_TURN;
    }

    @Override
    public void atBattleStartPreDraw() {
        shuffleIn(new Insight(), counter);
        addToBot(new RelicAboveCreatureAction(p(), this));
    }

    @Override
    public void onVictory() {
        if (counter >= MAX_INSIGHT)
            return;
        flash();
        counter += INSIGHT_INCREASE;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], DRAW_PER_TURN, INITIAL_INSIGHT, PER_N_BATTLE, INSIGHT_INCREASE, MAX_INSIGHT);
    }
}
