package morimensmod.rewards;

import static morimensmod.MorimensMod.makeRewardPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import morimensmod.util.CardLib;
import morimensmod.util.TexLoader;

public class WheelOfDestinyReward_Uncommon extends AbstractWheelOfDestinyReward {

    @SpireEnum
    public static RewardType WHEEL_OF_DESTINY_UNCOMMON;

    private static final Texture ICON = TexLoader
            .getTexture(makeRewardPath(WheelOfDestinyReward_Uncommon.class.getSimpleName() + ".png"));

    public int amount;

    public WheelOfDestinyReward_Uncommon(int num) {
        super(ICON, "Choose 1 Wheel of Destiny", WHEEL_OF_DESTINY_UNCOMMON);
        amount = num;
        wheels = CardLib.getRandomWheelOfDestiny(num, CardRarity.UNCOMMON);
    }
}
