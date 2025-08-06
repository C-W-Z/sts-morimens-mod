package morimensmod.rewards;

import static morimensmod.MorimensMod.makeRewardPath;

import com.badlogic.gdx.graphics.Texture;

import morimensmod.patches.enums.RewardTypePatch;
import morimensmod.util.CardLib;
import morimensmod.util.TexLoader;

public class WheelOfDestinyReward_Random extends AbstractWheelOfDestinyReward {

    private static final Texture ICON = TexLoader
            .getTexture(makeRewardPath(WheelOfDestinyReward_Uncommon.class.getSimpleName() + ".png"));

    public static final RewardType TYPE = RewardTypePatch.WHEEL_OF_DESTINY_RANDOM;

    public int amount;

    public WheelOfDestinyReward_Random(int num) {
        super(ICON, "Choose 1 Wheel of Destiny", TYPE);
        amount = num;
        wheels = CardLib.getRandomWheelOfDestiny(num);
    }
}
