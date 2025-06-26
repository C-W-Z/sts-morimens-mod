package morimensmod.cards.status;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.STATUS_COLOR;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.interfaces.OnAfterExalt;
import morimensmod.patches.CustomTags;
import morimensmod.powers.SealPower;

public class DimensionLock extends AbstractEasyCard implements OnAfterExalt {
    public final static String ID = makeID(DimensionLock.class.getSimpleName());

    public DimensionLock() {
        super(ID, -1, CardType.STATUS, CardRarity.COMMON, CardTarget.NONE, STATUS_COLOR);
        tags.add(CustomTags.STATUS);
        magicNumber = baseMagicNumber = 2; // 封印
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void upp() {
    }

    @Override
    public void upgrade() {
    }

    @Override
    public void onAfterExalt(AbstractAwakener awaker, int exhaustAliemus, boolean overExalt) {
        addToBot(new ApplyPowerAction(awaker, null, new SealPower(awaker, magicNumber)));
    }
}
