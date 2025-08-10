package morimensmod.cards.symptoms;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import morimensmod.patches.enums.CustomTags;

public class SymptomBreakdown extends AbstractSymptomCard {
    public final static String ID = makeID(SymptomBreakdown.class.getSimpleName());

    public SymptomBreakdown() {
        super(ID, 1, CardTarget.ALL_ENEMY);
        sortIndex = 5;
        tags.add(CustomTags.UNREMOVABLE_IN_SHOP);
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster _m) {
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, m -> new VulnerablePower(m, magicNumber, false)));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        addToBot(new ApplyPowerAction(p(), null, new FrailPower(p(), magicNumber, true)));
    }
}
