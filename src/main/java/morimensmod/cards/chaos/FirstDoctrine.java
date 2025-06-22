package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.FirstDoctrinePower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class FirstDoctrine extends AbstractEasyCard {
    public final static String ID = makeID(FirstDoctrine.class.getSimpleName());

    public FirstDoctrine() {
        super(ID, 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.ROUSE);
        aliemus = baseAliemus = 30;
        magicNumber = baseMagicNumber = 1; // 每次獲得的能量
        secondMagic = baseSecondMagic = 3; // 每回合最大觸發次數
        selfRetain = true; // 保留
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        applyToSelf(new FirstDoctrinePower(p, magicNumber, secondMagic));
    }

    @Override
    public void upp() {
        upgradeAliemus(20);
        upgradeBaseCost(2);
    }
}
