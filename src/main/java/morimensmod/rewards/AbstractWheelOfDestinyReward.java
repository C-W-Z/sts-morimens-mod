package morimensmod.rewards;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomReward;

public abstract class AbstractWheelOfDestinyReward extends CustomReward {

    protected ArrayList<AbstractCard> wheels;

    public AbstractWheelOfDestinyReward(Texture icon, String text, RewardType type) {
        super(icon, text, type);
    }

    public boolean isValid() {
        return !wheels.isEmpty();
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(wheels, this, "Choose 1 Wheel of Destiny");
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}
