package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.TmpBarrierPower;

public class HiddenInTheTimeRift extends AbstractEasyCard {
    public final static String ID = makeID(HiddenInTheTimeRift.class.getSimpleName());

    public HiddenInTheTimeRift() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CHAOS_COLOR, CardImgID.Tawil.ID);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 1; // 屏障
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new TmpBarrierPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}
