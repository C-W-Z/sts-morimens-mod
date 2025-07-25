package morimensmod.cards.chaos;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;

public class OuterSurgery extends AbstractEasyCard {
    public final static String ID = makeID(OuterSurgery.class.getSimpleName());

    public OuterSurgery() {
        super(ID, CardImgID.DollSkill, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.HEALING);
        heal = baseHeal = 3;
        magicNumber = baseMagicNumber = 1; // 虛弱
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, heal));
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new WeakPower(mo, magicNumber, false)));
    }

    @Override
    public void upp() {
        upgradeHeal(1);
        upgradeMagicNumber(1);
    }
}
