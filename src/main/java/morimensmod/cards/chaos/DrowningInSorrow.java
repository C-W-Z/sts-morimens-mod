package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.DrowningInSorrowPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class DrowningInSorrow extends AbstractEasyCard {
    public final static String ID = makeID(DrowningInSorrow.class.getSimpleName());

    public DrowningInSorrow() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.ROUSE);
        aliemus = baseAliemus = 5;
        magicNumber = baseMagicNumber = 2; // 每回合結束施加中毒
        secondMagic = baseSecondMagic = 4; // 狂氣倍數 only for display
        selfRetain = true; // 保留
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        applyToSelf(new DrowningInSorrowPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeAliemus(20);
        upgradeBaseCost(0);
    }
}
