package morimensmod.rewards;

import static morimensmod.MorimensMod.makeRewardPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;

import morimensmod.util.CardLib;
import morimensmod.util.TexLoader;

public class WheelOfDestinyReward_Random extends AbstractWheelOfDestinyReward {

    @SpireEnum
    public static RewardType WHEEL_OF_DESTINY_RANDOM;

    private static final Texture ICON = TexLoader
            .getTexture(makeRewardPath(WheelOfDestinyReward_Uncommon.class.getSimpleName() + ".png"));

    public int amount;

    public WheelOfDestinyReward_Random(int num) {
        super(ICON, "Choose 1 Wheel of Destiny", WHEEL_OF_DESTINY_RANDOM);
        amount = num;
        wheels = CardLib.getRandomWheelOfDestiny(num);
    }
}
