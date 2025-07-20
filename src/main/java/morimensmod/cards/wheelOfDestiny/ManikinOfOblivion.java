package morimensmod.cards.wheelOfDestiny;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.characters.AbstractAwakener;
import morimensmod.powers.wheelOfDestiny.ManikinOfOblivionPower;

public class ManikinOfOblivion extends AbstractWheelOfDestiny {

    public final static String ID = makeID(ManikinOfOblivion.class.getSimpleName());

    public ManikinOfOblivion() {
        super(ID, 3, CardRarity.RARE);
        magicNumber = baseMagicNumber = 7; // 狂氣回充
        secondMagic = baseSecondMagic = 10; // 狂氣、中毒、治療提升%數
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
        if (upgraded && p() instanceof AbstractAwakener)
            actB(() -> ((AbstractAwakener) p()).aliemusRegen += magicNumber);
        return upgraded;
    }
}
