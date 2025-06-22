package morimensmod.cards.wheel_of_destiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.CustomTags;
import morimensmod.powers.ManikinOfOblivionPower;

public class ManikinOfOblivion extends AbstractEasyCard implements StartupCard {

    public final static String ID = makeID(ManikinOfOblivion.class.getSimpleName());

    public ManikinOfOblivion() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF, WHEEL_OF_DESTINY_COLOR);
        tags.add(CustomTags.WHEEL_OF_DESTINY);
        magicNumber = baseMagicNumber = 7; // 狂氣回充
        secondMagic = baseSecondMagic = 10; // 狂氣、中毒、治療提升%數
        selfRetain = true;
        prepare = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ManikinOfOblivionPower(p, secondMagic));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2);
    }

    @Override
    public boolean atBattleStartPreDraw() {
        if (upgraded)
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    ((AbstractAwakener) p()).aliemusRegen += magicNumber;
                    isDone = true;
                }
            });
        return upgraded;
    }
}
