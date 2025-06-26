package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.EntropyUndonePower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class EntropyUndone extends AbstractEasyCard {
    public final static String ID = makeID(EntropyUndone.class.getSimpleName());

    public EntropyUndone() {
        super(ID, 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.ROUSE);
        aliemus = baseAliemus = 20;
        magicNumber = baseMagicNumber = 50; // 每次獲得的銀鑰能量
        secondMagic = baseSecondMagic = 3; // 每回合最大觸發次數 only for display
        thirdMagic = baseThirdMagic = 1; // 每次獲得的負熵 only for display
        selfRetain = true; // 保留
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        applyToSelf(new EntropyUndonePower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeAliemus(20);
        upgradeBaseCost(2);
    }
}
