package morimensmod.cards;

import basemod.AutoAdd;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class PileModalSelectCard extends AbstractEasyCard {

    private Runnable onUseOrChosen;

    public static String getCardImgID(AbstractCard card) {
        if (card instanceof AbstractEasyCard)
            return ((AbstractEasyCard) card).cardImgID;
        return card.cardID;
    }

    public PileModalSelectCard(AbstractCard card, Runnable onUseOrChosen) {
        super(card.cardID, getCardImgID(card), card.cost, card.type, card.rarity, card.target, card.color);
        this.onUseOrChosen = onUseOrChosen;

        for(int i = 0; i < this.timesUpgraded; ++i)
            card.upgrade();

        this.name = card.name;
        this.target = card.target;
        this.upgraded = card.upgraded;
        this.timesUpgraded = card.timesUpgraded;
        this.baseDamage = card.baseDamage;
        this.baseBlock = card.baseBlock;
        this.baseMagicNumber = card.baseMagicNumber;
        this.cost = card.cost;
        this.costForTurn = card.costForTurn;
        this.isCostModified = card.isCostModified;
        this.isCostModifiedForTurn = card.isCostModifiedForTurn;
        this.inBottleLightning = card.inBottleLightning;
        this.inBottleFlame = card.inBottleFlame;
        this.inBottleTornado = card.inBottleTornado;
        this.isSeen = card.isSeen;
        this.isLocked = card.isLocked;
        this.misc = card.misc;
        this.freeToPlayOnce = card.freeToPlayOnce;

        this.uuid = card.uuid;

        if (card instanceof AbstractEasyCard) {
            AbstractEasyCard c = (AbstractEasyCard) card;
            this.baseHeal = this.heal = c.baseHeal;
            this.baseDraw = this.draw = c.baseDraw;
            this.baseAttackCount = this.attackCount = c.baseAttackCount;
            this.baseSecondMagic = this.secondMagic = c.baseSecondMagic;
            this.baseThirdMagic = this.thirdMagic = baseThirdMagic;
            this.baseAliemus = this.aliemus = c.baseAliemus;
        }
    }

    @Override
    public void onChoseThisOption() {
        onUseOrChosen.run();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        onUseOrChosen.run();
    }

    @Override
    public void upp() {

    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    @Override
    public void upgrade() {
    }

    @Override
    public AbstractCard makeCopy() {
        return new PileModalSelectCard(this, onUseOrChosen);
    }
}
