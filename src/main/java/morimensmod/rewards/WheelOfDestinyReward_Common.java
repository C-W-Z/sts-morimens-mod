package morimensmod.rewards;

import static morimensmod.MorimensMod.makeRewardPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import morimensmod.util.CardLib;
import morimensmod.util.TexLoader;

public class WheelOfDestinyReward_Common extends AbstractWheelOfDestinyReward {

    @SpireEnum
    public static RewardType WHEEL_OF_DESTINY_COMMON;

    private static final Texture ICON = TexLoader
            .getTexture(makeRewardPath(WheelOfDestinyReward_Common.class.getSimpleName() + ".png"));

    public int amount;

    public WheelOfDestinyReward_Common(int num) {
        super(ICON, "Choose 1 Wheel of Destiny", WHEEL_OF_DESTINY_COMMON);
        amount = num;
        wheels = CardLib.getRandomWheelOfDestiny(num, CardRarity.UNCOMMON);
    }
}
