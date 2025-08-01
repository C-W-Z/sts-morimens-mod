package morimensmod.monsters.minions;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import basemod.animations.AbstractAnimation;
import morimensmod.powers.BarrierPower;

public class CasiahMinion extends AbstractMinion {

    public static final String ID = makeID(CasiahMinion.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;

    public CasiahMinion(float x, float y) {
        super(NAME, ID, 29, 220, 340, x, y, 0);
    }

    @Override
    protected void onSummon() {
        addToBot(new ApplyPowerAction(this, this, new BarrierPower(this, 3)));
    }

    @Override
    protected AbstractAnimation getAnimation() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnimation'");
    }

    @Override
    protected void getMove(int num) {
        setIntent(MOVES[0], 0, Intent.ATTACK_DEBUFF);
    }
}
