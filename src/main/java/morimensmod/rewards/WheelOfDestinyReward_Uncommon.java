package morimensmod.rewards;

import static morimensmod.MorimensMod.makeRewardPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import morimensmod.patches.enums.RewardTypePatch;
import morimensmod.util.CardLib;
import morimensmod.util.TexLoader;

public class WheelOfDestinyReward_Uncommon extends AbstractWheelOfDestinyReward {

    private static final Texture ICON = TexLoader
            .getTexture(makeRewardPath(WheelOfDestinyReward_Uncommon.class.getSimpleName() + ".png"));

    public static final RewardType TYPE = RewardTypePatch.WHEEL_OF_DESTINY_UNCOMMON;

    public int amount;

    public WheelOfDestinyReward_Uncommon(int num) {
        super(ICON, "Choose 1 Wheel of Destiny", TYPE);
        amount = num;
        wheels = CardLib.getRandomWheelOfDestiny(num, CardRarity.UNCOMMON);
    }
}
