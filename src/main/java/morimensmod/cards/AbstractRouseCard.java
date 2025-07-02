package morimensmod.cards;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.patches.CustomTags;

public abstract class AbstractRouseCard extends AbstractEasyCard implements SpawnModificationCard, BranchingUpgradesCard {

    int costBeforeUpgrade;

    public AbstractRouseCard(final String cardID, final int cost, final CardRarity rarity, final CardColor color) {
        super(cardID, cost, CardType.POWER, rarity, CardTarget.SELF, color);
        tags.add(CustomTags.ROUSE);
        aliemus = baseAliemus = 20;
        selfRetain = true; // 保留
        costBeforeUpgrade = cost;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
    }

    @Override
    public void upp() {
        if (isBranchUpgrade()) {
            upgradeAliemus(10);
            upgradeBaseCost(Math.max(0, costBeforeUpgrade - 1));
            isInnate = true;
        } else {
            upgradeAliemus(30);
        }
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        //Player can't already have the card.
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (c.cardID.equals(this.cardID))
                return false;
        return true;
    }
}
