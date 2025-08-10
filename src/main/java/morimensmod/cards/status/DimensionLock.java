package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterExalt;
import morimensmod.powers.SealPower;

public class DimensionLock extends AbstractStatusCard implements OnAfterExalt {
    public final static String ID = makeID(DimensionLock.class.getSimpleName());

    public DimensionLock() {
        super(ID, -2, CardRarity.COMMON, CardTarget.NONE);
        sortIndex = 20;
        magicNumber = baseMagicNumber = 2; // 封印
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onAfterExalt(AbstractAwakener awaker, int exhaustAliemus, boolean overExalt) {
        addToBot(new ApplyPowerAction(awaker, null, new SealPower(awaker, magicNumber)));
    }
}
