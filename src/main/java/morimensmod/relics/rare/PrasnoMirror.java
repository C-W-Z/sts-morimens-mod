package morimensmod.relics.rare;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;

import morimensmod.cards.buffs.Insight;
import morimensmod.relics.AbstractEasyRelic;

public class PrasnoMirror extends AbstractEasyRelic {
    public static final String ID = makeID(PrasnoMirror.class.getSimpleName());

    private static final int DRAW_PER_TURN = 1;
    private static final int INITIAL_INSIGHT = 1;
    private static final int PER_N_BATTLE = 2;
    private static final int INSIGHT_INCREASE = 1;
    private static final int MAX_INSIGHT = 4;

    public PrasnoMirror() {
        super(ID, RelicTier.RARE, LandingSound.CLINK);
        counter = 0;
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
        flash();
        shuffleIn(new Insight(), INITIAL_INSIGHT + counter * INSIGHT_INCREASE / PER_N_BATTLE);
    }

    @Override
    public void onVictory() {
        if (INITIAL_INSIGHT + counter * INSIGHT_INCREASE / PER_N_BATTLE >= MAX_INSIGHT)
            return;
        flash();
        counter++;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(
                DESCRIPTIONS[0],
                DRAW_PER_TURN,
                INITIAL_INSIGHT + counter * INSIGHT_INCREASE / PER_N_BATTLE,
                PER_N_BATTLE,
                INSIGHT_INCREASE,
                MAX_INSIGHT);
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        if (this.hb.hovered) {
            this.description = getUpdatedDescription();
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }
        super.renderTip(sb);
    }
}
