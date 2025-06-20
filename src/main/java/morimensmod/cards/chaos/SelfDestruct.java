package morimensmod.cards.chaos;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.EasyModalChoiceCard;
import morimensmod.patches.CustomTags;
import morimensmod.powers.ElationPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.*;

import java.util.ArrayList;

public class SelfDestruct extends AbstractEasyCard {
    public final static String ID = makeID(SelfDestruct.class.getSimpleName());

    public SelfDestruct() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 1; // 1回合 易傷/虛弱
        secondMagic = baseSecondMagic = 1; // 1回合 興奮
        thirdMagic = baseThirdMagic = 5; // 降低 5 臨時力量
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choiceCardList = new ArrayList<>();

        EasyModalChoiceCard elation = new EasyModalChoiceCard(
                ID,
                cardStrings.EXTENDED_DESCRIPTION[0],
                cardStrings.EXTENDED_DESCRIPTION[1],
                () -> useElation(p),
                CHAOS_COLOR,
                CustomTags.COMMAND);

        elation.magicNumber = elation.baseMagicNumber = magicNumber;
        elation.secondMagic = elation.baseSecondMagic = secondMagic;

        EasyModalChoiceCard curse = new EasyModalChoiceCard(
                ID,
                cardStrings.EXTENDED_DESCRIPTION[2],
                cardStrings.EXTENDED_DESCRIPTION[3],
                () -> useCurse(p),
                CHAOS_COLOR,
                CustomTags.COMMAND);

        curse.magicNumber = curse.baseMagicNumber = magicNumber;
        curse.thirdMagic = curse.baseThirdMagic = thirdMagic;

        choiceCardList.add(elation);
        choiceCardList.add(curse);
        atb(new EasyModalChoiceAction(choiceCardList));
    }

    private void useElation(AbstractPlayer p) {
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber,
                (mo) -> new VulnerablePower(mo, magicNumber, false)));
        applyToSelf(new ElationPower(p, secondMagic));
    }

    private void useCurse(AbstractPlayer p) {
        addToBot(new AllEnemyApplyPowerAction(p, magicNumber,
                (mo) -> new WeakPower(mo, magicNumber, false)));
        addToBot(new AllEnemyApplyPowerAction(p, -thirdMagic, (mo) -> new StrengthPower(mo, -thirdMagic)));
        addToBot(new AllEnemyApplyPowerAction(p, thirdMagic, (mo) -> new GainStrengthPower(mo, thirdMagic)));
    }

    @Override
    public void upp() {
        upgradeThirdMagic(5);
    }
}
