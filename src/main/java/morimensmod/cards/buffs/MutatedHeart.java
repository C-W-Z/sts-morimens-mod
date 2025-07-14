package morimensmod.cards.buffs;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actionify;
import static morimensmod.util.Wiz.isInBossCombat;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.patches.CustomTags;

public class MutatedHeart extends AbstractBuffCard implements StartupCard {
    public final static String ID = makeID(MutatedHeart.class.getSimpleName());

    public MutatedHeart() {
        super(ID, 0, CardRarity.RARE, CardTarget.NONE);
        tags.add(CustomTags.REUSE);
        draw = baseDraw = 3;
        magicNumber = baseMagicNumber = 1;
        secondMagic = baseSecondMagic = 0;
        ExhaustiveVariable.setBaseValue(this, magicNumber);
        selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int handSize = Math.max(0, p.hand.size() - 1);
        addToBot(new DiscardAction(p, p, handSize, false));
        addToBot(new DrawCardAction(handSize + draw, actionify(() -> {
            if (!isInBossCombat())
                return;
            for (AbstractCard c : DrawCardAction.drawnCards)
                c.setCostForTurn(c.costForTurn - secondMagic);
        })));
    }

    @Override
    public boolean canUpgrade() {
        return (timesUpgraded < 2);
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    @Override
    protected void uDesc() {
        if (timesUpgraded < 1)
            return;
        this.rawDescription = this.cardStrings.EXTENDED_DESCRIPTION[timesUpgraded - 1];
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!canUpgrade())
            return;
        this.upgradeName();
        this.uDesc();
        this.upp();
    }

    @Override
    public void upp() {
        if (timesUpgraded == 1) {
            isInnate = true;
            upgradeMagicNumber(1);
        }
        else if (timesUpgraded == 2)
            upgradeSecondMagic(1);
        if (timesUpgraded >= 1 && isInBossCombat())
            triggerOnBossCombat();
    }

    @Override
    public boolean atBattleStartPreDraw() {
        if (timesUpgraded >= 1 && isInBossCombat())
            triggerOnBossCombat();
        return false;
    }

    private void triggerOnBossCombat() {
        this.rawDescription = this.cardStrings.EXTENDED_DESCRIPTION[timesUpgraded + 1];
        ExhaustiveVariable.setBaseValue(this, magicNumber); // 這裡面已經有initializeDescription()了
        // this.initializeDescription();
    }
}
