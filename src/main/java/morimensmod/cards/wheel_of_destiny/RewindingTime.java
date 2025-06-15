package morimensmod.cards.wheel_of_destiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.RewindingTimePower;

public class RewindingTime extends AbstractEasyCard {
    public final static String ID = makeID(RewindingTime.class.getSimpleName());

    public RewindingTime() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF, WHEEL_OF_DESTINY_COLOR);
        tags.add(CustomTags.WHEEL_OF_DESTINY);
        magicNumber = baseMagicNumber = 1; // 每回合最大觸發次數
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RewindingTimePower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2); // cost 3 -> 2
    }
}
