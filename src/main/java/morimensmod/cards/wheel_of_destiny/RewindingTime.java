package morimensmod.cards.wheel_of_destiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.CustomTags;
import morimensmod.powers.RewindingTimePower;

public class RewindingTime extends AbstractEasyCard implements StartupCard {
    public final static String ID = makeID(RewindingTime.class.getSimpleName());

    public RewindingTime() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF, WHEEL_OF_DESTINY_COLOR);
        tags.add(CustomTags.WHEEL_OF_DESTINY);
        magicNumber = baseMagicNumber = 14; // 銀鑰充能
        secondMagic = baseSecondMagic = 1; // 每回合最大觸發次數
        selfRetain = true;
        prepare = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RewindingTimePower(p, secondMagic));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2); // cost 3 -> 2
    }

    @Override
    public boolean atBattleStartPreDraw() {
        if (upgraded && p() instanceof AbstractAwakener)
            actB(() -> ((AbstractAwakener) p()).keyflareRegen += magicNumber);
        return upgraded;
    }
}
