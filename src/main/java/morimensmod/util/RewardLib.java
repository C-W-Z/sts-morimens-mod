package morimensmod.util;

import com.megacrit.cardcrawl.rewards.RewardSave;

import basemod.BaseMod;
import morimensmod.rewards.WheelOfDestinyReward_Random;

public class RewardLib {

    public static void register() {
        BaseMod.registerCustomReward(
                WheelOfDestinyReward_Random.TYPE,
                rewardSave -> { // this handles what to do when this quest type is loaded.
                    return new WheelOfDestinyReward_Random(rewardSave.amount);
                },
                customReward -> { // this handles what to do when this quest type is saved.
                    return new RewardSave(customReward.type.toString(), null,
                            ((WheelOfDestinyReward_Random) customReward).amount, 0);
                });
    }
}
