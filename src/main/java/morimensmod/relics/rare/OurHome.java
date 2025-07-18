package morimensmod.relics.rare;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.relics.AbstractEasyRelic;

public class OurHome extends AbstractEasyRelic {
    public static final String ID = makeID(OurHome.class.getSimpleName());

    private static final int BLOCK_PER_ATTACK = 1;
    private static final int TMP_STR_PER_HEAL = 2;

    public OurHome() {
        super(ID, RelicTier.RARE, LandingSound.HEAVY);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageType.THORNS)
            return;
        flash();
        addToTop(new GainBlockAction(p(), BLOCK_PER_ATTACK));
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        flash();
        applyToSelfTop(new LoseStrengthPower(p(), TMP_STR_PER_HEAL));
        applyToSelfTop(new StrengthPower(p(), TMP_STR_PER_HEAL));
        return super.onPlayerHeal(healAmount);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], BLOCK_PER_ATTACK, TMP_STR_PER_HEAL);
    }
}
