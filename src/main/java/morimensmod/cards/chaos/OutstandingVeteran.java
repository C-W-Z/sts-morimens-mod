package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.OutstandingVeteranPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class OutstandingVeteran extends AbstractEasyCard {
    public final static String ID = makeID(OutstandingVeteran.class.getSimpleName());

    public OutstandingVeteran() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.ROUSE);
        aliemus = baseAliemus = 20;
        magicNumber = baseMagicNumber = 1; // 敏捷
        secondMagic = baseSecondMagic = 1; // 一倍格擋加成
        selfRetain = true; // 保留
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        applyToSelf(new DexterityPower(p, magicNumber));
        applyToSelf(new OutstandingVeteranPower(p, secondMagic));
    }

    @Override
    public void upp() {
        upgradeAliemus(20);
        upgradeBaseCost(1);
    }
}
