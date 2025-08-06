package morimensmod.rewards;

import static morimensmod.MorimensMod.makeRewardPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import morimensmod.patches.enums.RewardTypePatch;
import morimensmod.util.CardLib;
import morimensmod.util.TexLoader;

public class WheelOfDestinyReward_Common extends AbstractWheelOfDestinyReward {

    private static final Texture ICON = TexLoader
            .getTexture(makeRewardPath(WheelOfDestinyReward_Common.class.getSimpleName() + ".png"));

    public static final RewardType TYPE = RewardTypePatch.WHEEL_OF_DESTINY_COMMON;

    public int amount;

    public WheelOfDestinyReward_Common(int num) {
        super(ICON, TYPE);
        amount = num;
        wheels = CardLib.getRandomWheelOfDestiny(num, CardRarity.UNCOMMON);
    }
}
