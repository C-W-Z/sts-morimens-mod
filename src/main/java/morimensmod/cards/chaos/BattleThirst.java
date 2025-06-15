package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.BattleThirstPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class BattleThirst extends AbstractEasyCard {
    public final static String ID = makeID(BattleThirst.class.getSimpleName());

    public BattleThirst() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.NONE, CHAOS_COLOR);
        tags.add(CustomTags.ROUSE);
        magicNumber = baseMagicNumber = 1; // 攻擊次數 + 1
        selfRetain = true; // 保留
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BattleThirstPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBaseCost(2); // cost 3 -> 2
    }
}
