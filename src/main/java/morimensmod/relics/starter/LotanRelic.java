package morimensmod.relics.starter;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.relics.AbstractEasyRelic;

public class LotanRelic extends AbstractEasyRelic {
    public static final String ID = makeID(LotanRelic.class.getSimpleName());

    private static final int STRIKE_BASE_AMPLIFY = 20;
    private static final int BASE_AMPLIFY_PER_ATTACK = 2;

    public LotanRelic() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, AWAKENER_COLOR);
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
        if (info.type == DamageType.THORNS)
            return;
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
        return String.format(DESCRIPTIONS[0], STRIKE_BASE_AMPLIFY, BASE_AMPLIFY_PER_ATTACK);
    }
}
