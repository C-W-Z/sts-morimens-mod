package morimensmod.cards;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.patches.enums.CustomTags;

public abstract class AbstractRouseCard extends AbstractEasyCard implements SpawnModificationCard {

    int costBeforeUpgrade;

    public AbstractRouseCard(final String cardID, final int cost, final CardRarity rarity, final CardColor color) {
        super(cardID, cost, CardType.POWER, rarity, CardTarget.SELF, color);
        tags.add(CustomTags.ROUSE);
        aliemus = baseAliemus = 10;
        selfRetain = true; // 保留
        costBeforeUpgrade = cost;
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
    public void upp() {
        if (timesUpgraded == 1)
            upgradeAliemus(20);
        else if (timesUpgraded == 2)
            upgradeBaseCost(Math.max(0, costBeforeUpgrade - 1));
        else if (timesUpgraded == 3) {
            upgradeAliemus(20);
            isInnate = true;
        }
    }

    @Override
    public void applyPowers() {
        // 只有直接獲得的狂氣在這裡計算加成，回血、回狂和中毒等在Power中計算加成
        applyAliemusAmplify();
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        // Player can't already have the card.
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (c.cardID.equals(this.cardID))
                return false;
        return true;
    }
}
