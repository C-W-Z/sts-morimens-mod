package morimensmod.cards.wheel_of_destiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.ManikinOfOblivionPower;

public class ManikinOfOblivion extends AbstractEasyCard {
    public final static String ID = makeID(ManikinOfOblivion.class.getSimpleName());

    public ManikinOfOblivion() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF, WHEEL_OF_DESTINY_COLOR);
        tags.add(CustomTags.WHEEL_OF_DESTINY);
        magicNumber = baseMagicNumber = 10; // 狂氣、中毒、治療提升%數
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ManikinOfOblivionPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2);
    }
}
