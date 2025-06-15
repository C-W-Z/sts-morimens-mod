package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        // 降低所有敵人的力量 6（這回合）
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                // 給負面力量（減攻擊力
                addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -magicNumber), -magicNumber));
                // 同時添加 GainStrengthPower 讓效果只持續一回合
                addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, magicNumber), magicNumber));
            }
        }

        // 抽一張牌
        addToBot(new DrawCardAction(p, draw));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(3); // 升級後力量減少變 9
    }
}
