package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;

import java.util.ArrayList;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;

import morimensmod.cards.buffs.Insight;

public class Suction extends AbstractWheelOfDestiny {
    public final static String ID = makeID(Suction.class.getSimpleName());

    public Suction() {
        super(ID, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 10;
        secondMagic = baseSecondMagic = 0;
    }

    @Override
    public void upp() {
        upgradeSecondMagic(1);
        cardsToPreview = new Insight();
    }

    @Override
    public int onRestToChangeHealAmount(int healAmount) {
        return healAmount + (int) (AbstractDungeon.player.maxHealth * magicNumber / 100F);
    }

    @Override
    public ArrayList<RewardItem> onRestToObtainRewards() {
        if (!upgraded)
            return null;
        RewardItem reward = new RewardItem();
        reward.type = RewardType.CARD;
        reward.cards.clear();
        for (int i = 0; i < secondMagic; i++)
            reward.cards.add(cardsToPreview.makeStatEquivalentCopy());
        ArrayList<RewardItem> rewards = new ArrayList<>();
        rewards.add(reward);
        return rewards;
    }
}
