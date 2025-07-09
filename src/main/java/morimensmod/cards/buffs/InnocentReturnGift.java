package morimensmod.cards.buffs;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.isInBossCombat;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.patches.CustomTags;

public class InnocentReturnGift extends AbstractBuffCard implements StartupCard {
    public final static String ID = makeID(InnocentReturnGift.class.getSimpleName());

    public InnocentReturnGift() {
        super(ID, 1, CardRarity.RARE, CardTarget.SELF);
        tags.add(CustomTags.REUSE);
        aliemus = baseAliemus = 30;
        magicNumber = baseMagicNumber = 1;
        ExhaustiveVariable.setBaseValue(this, magicNumber);
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
    }

    @Override
    public boolean canUpgrade() {
        return (timesUpgraded < 3);
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    public void upgrade() {
        if (!canUpgrade())
            return;
        this.upgradeName();
        if (timesUpgraded == 3)
            this.uDesc();
        this.upp();
    }

    @Override
    public void upp() {
        if (timesUpgraded == 1)
            upgradeAliemus(30);
        else if (timesUpgraded == 2)
            upgradeBaseCost(0);
        else if (timesUpgraded == 3) {
            upgradeMagicNumber(2);
            if (isInBossCombat())
                triggerOnBossCombat();
        }
    }

    @Override
    public boolean atBattleStartPreDraw() {
        if (timesUpgraded == 3 && isInBossCombat())
            triggerOnBossCombat();
        return false;
    }

    private void triggerOnBossCombat() {
        this.rawDescription = this.cardStrings.EXTENDED_DESCRIPTION[0];
        ExhaustiveVariable.setBaseValue(this, magicNumber); // 這裡面已經有initializeDescription()了
        // this.initializeDescription();
    }
}
