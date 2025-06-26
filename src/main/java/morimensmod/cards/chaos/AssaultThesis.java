package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actionify;
import static morimensmod.util.Wiz.p;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class AssaultThesis extends AbstractEasyCard {
    public final static String ID = makeID(AssaultThesis.class.getSimpleName());

    public AssaultThesis() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 4; // 降低臨時力量
        draw = baseDraw = 1; // 抽牌數
        secondMagic = baseSecondMagic = 35; // 銀鑰能量 per 抽到牌的費用
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyApplyPowerAction(p, -magicNumber, (mo) -> new StrengthPower(mo, -magicNumber)));
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber, (mo) -> new GainStrengthPower(mo, magicNumber)));
        addToBot(new DrawCardAction(draw, actionify(() -> {
            if (upgraded && !DrawCardAction.drawnCards.isEmpty()) {
                AbstractCard drawn = DrawCardAction.drawnCards.get(0);

                int effectiveCost = drawn.costForTurn;
                if (drawn.freeToPlayOnce || effectiveCost < 0)
                    effectiveCost = 0; // 免費或 X 費當成 0

                if (effectiveCost > 0)
                    addToTop(new KeyflareChangeAction(p(), effectiveCost * secondMagic));
            }
        })));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3);
    }
}
