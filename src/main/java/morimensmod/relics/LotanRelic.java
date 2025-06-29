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
    private static final int BASE_AMPLIFY_PER_ATTACK = 2;

    public LotanRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT);
        counter = 0;
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
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STRIKE_BASE_AMPLIFY + DESCRIPTIONS[1] + BASE_AMPLIFY_PER_ATTACK + DESCRIPTIONS[2];
    }
}
