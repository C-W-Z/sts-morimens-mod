package morimensmod.cards.chaos.rouse;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractRouseCard;
import morimensmod.cards.CardImgID;
import morimensmod.powers.rouse.BattleThirstPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class BattleThirst extends AbstractRouseCard {
    public final static String ID = makeID(BattleThirst.class.getSimpleName());

    public BattleThirst() {
        super(ID, 2, CardRarity.RARE, CHAOS_COLOR, CardImgID.Lotan.ID);
        magicNumber = baseMagicNumber = 1; // 攻擊次數 + 1
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        applyToSelf(new BattleThirstPower(p, magicNumber));
    }
}
