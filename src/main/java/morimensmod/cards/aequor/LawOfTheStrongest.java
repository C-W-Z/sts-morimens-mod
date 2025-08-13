package morimensmod.cards.aequor;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AEQUOR_COLOR;
import static morimensmod.util.Wiz.applyToEnemy;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.getPowerAmount;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;

public class LawOfTheStrongest extends AbstractEasyCard {
    public final static String ID = makeID(LawOfTheStrongest.class.getSimpleName());

    public LawOfTheStrongest() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ALL, AEQUOR_COLOR, CardImgID.Goliath.ID);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 1; // 永力
    }

    public void use(AbstractPlayer p, AbstractMonster _m) {
        applyToSelf(new StrengthPower(p, magicNumber));

        // 移除力量
        int totalStr = 0;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.isDeadOrEscaped())
                continue;
            int str = getPowerAmount(m, StrengthPower.POWER_ID);
            if (str <= 0)
                return;
            totalStr += str;
            applyToEnemy(m, new StrengthPower(m, -str));
            applyToEnemy(m, new GainStrengthPower(m, str));
        }

        // 偷力
        if (upgraded && totalStr > 0) {
            applyToSelf(new StrengthPower(p, totalStr));
            applyToSelf(new LoseStrengthPower(p, totalStr));
        }
    }

    @Override
    public void upp() {
    }
}
