package morimensmod.cards.buffs;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.BUFF_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class OctahedronDice extends AbstractEasyCard {
    public final static String ID = makeID(OctahedronDice.class.getSimpleName());

    private static final int MEDIEM_DICE = 4;
    private static final int MAX_DICE = 6;
    private static final int DEBUFF_ROUND = 1;
    private static final int ENERGY_GAIN = 1;
    private static final int TMP_STR_SCALE = 2;

    public OctahedronDice() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, BUFF_COLOR);
        tags.add(CustomTags.BUFF);
        magicNumber = baseMagicNumber = 100; // 骰子點數100%臨時力量
        exhaust = true;
        selfRetain = true;
        upgradedName = cardStrings.EXTENDED_DESCRIPTION[0];
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dice = AbstractDungeon.cardRandomRng.random(1, MAX_DICE);
        int tmpStr = ((dice >= MAX_DICE) ? TMP_STR_SCALE : 1) * MathUtils.ceil(dice * magicNumber / 100F);
        applyToSelf(new StrengthPower(p, tmpStr));
        applyToSelf(new LoseStrengthPower(p, tmpStr));
        if (dice >= MEDIEM_DICE) {
            if (upgraded)
                addToBot(new AllEnemyApplyPowerAction(p, DEBUFF_ROUND, (mo) -> new WeakPower(mo, DEBUFF_ROUND, false)));
            addToBot(new AllEnemyApplyPowerAction(p, DEBUFF_ROUND, (mo) -> new VulnerablePower(mo, DEBUFF_ROUND, false)));
        }
        if (dice >= MAX_DICE)
            addToBot(new GainEnergyAction(ENERGY_GAIN));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(20);
    }
}
