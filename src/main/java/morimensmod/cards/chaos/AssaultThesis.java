package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class AssaultThesis extends AbstractEasyCard {
    public final static String ID = makeID(AssaultThesis.class.getSimpleName());

    public AssaultThesis() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 6; // 降低臨時力量
        draw = baseDraw = 1; // 抽牌數
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyApplyPowerAction(p, -magicNumber, (mo) -> new StrengthPower(mo, -magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new GainStrengthPower(mo, magicNumber)));
        addToBot(new DrawCardAction(p, draw));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3); // 升級後力量減少變 9
    }
}
