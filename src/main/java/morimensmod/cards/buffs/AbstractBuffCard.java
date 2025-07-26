package morimensmod.cards.buffs;

import static morimensmod.patches.enums.ColorPatch.CardColorPatch.BUFF_COLOR;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;

public abstract class AbstractBuffCard extends AbstractEasyCard implements SpawnModificationCard {

    public AbstractBuffCard(final String cardID, final String cardImgID, final int cost, final CardRarity rarity, final CardTarget target) {
        super(cardID, cardImgID, cost, CardType.SKILL, rarity, target, BUFF_COLOR);
        tags.add(CustomTags.BUFF);
    }

    public AbstractBuffCard(final String cardID, final int cost, final CardRarity rarity, final CardTarget target) {
        this(cardID, cardID, cost, rarity, target);
    }

    @Override
    public void applyPowers() {
        super.applySuperPower();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateSuperCardDamage(mo);
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        return false;
    }

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {
        return false;
    }
}
