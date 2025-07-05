package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.*;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class Neurotoxin extends AbstractEasyRelic {
    public static final String ID = makeID(Neurotoxin.class.getSimpleName());

    private static final int POISON_PER_STRIKE = 1;

    public Neurotoxin() {
        super(ID, RelicTier.COMMON, LandingSound.SOLID);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.hasTag(CardTags.STRIKE))
            return;
        flash();
        addToBot(new AllEnemyApplyPowerAction(p(), POISON_PER_STRIKE, (mo) -> new PoisonPower(mo, p(), POISON_PER_STRIKE)));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], POISON_PER_STRIKE);
    }
}
