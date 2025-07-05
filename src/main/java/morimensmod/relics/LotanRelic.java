package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import morimensmod.cards.AbstractEasyCard;

public class LotanRelic extends AbstractEasyRelic {
    public static final String ID = makeID(LotanRelic.class.getSimpleName());

    private static final int STRIKE_BASE_AMPLIFY = 30;
    private static final int PER_HOW_MANY_ATTACK = 1;
    private static final int BASE_AMPLIFY_PER_ATTACK = 2;

    public LotanRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        actT(() -> AbstractEasyCard.baseStrikeDamageAmplify += STRIKE_BASE_AMPLIFY);
        addToTop(new RelicAboveCreatureAction(p(), this));
        counter = 0;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        flash();
        counter += BASE_AMPLIFY_PER_ATTACK;
        actB(() -> AbstractEasyCard.baseDamageAmplify += BASE_AMPLIFY_PER_ATTACK);
    }

    @Override
    public void onVictory() {
        counter = -1;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], STRIKE_BASE_AMPLIFY, PER_HOW_MANY_ATTACK, BASE_AMPLIFY_PER_ATTACK);
    }
}
