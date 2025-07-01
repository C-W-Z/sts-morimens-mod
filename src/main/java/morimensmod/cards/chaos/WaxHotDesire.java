package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.AllEnemyRemovePowerAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class WaxHotDesire extends AbstractEasyCard {
    public final static String ID = makeID(WaxHotDesire.class.getSimpleName());

    public WaxHotDesire() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 1; // 永反
        secondMagic = baseSecondMagic = 1; // 偷力
        aliemus = baseAliemus = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster _m) {
        applyToSelf(new ThornsPower(p, magicNumber));

        // 偷力
        int n_monsters = (int) AbstractDungeon.getMonsters().monsters.stream().filter((m) -> {
            return !m.isDeadOrEscaped();
        }).count();
        int steal = (n_monsters == 1) ? (2 * secondMagic) : secondMagic;
        addToBot(new AllEnemyApplyPowerAction(p, -steal, m -> new StrengthPower(m, -steal)));
        addToBot(new AllEnemyApplyPowerAction(p, steal, m -> new GainStrengthPower(m, steal)));
        applyToSelf(new LoseStrengthPower(p, steal * n_monsters));
        applyToSelf(new StrengthPower(p, steal * n_monsters));

        // 移除反擊
        addToBot(new AllEnemyRemovePowerAction(p, ThornsPower.POWER_ID));

        if (aliemus > 0)
            addToBot(new AliemusChangeAction(p, aliemus));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        upgradeAliemus(20);
    }
}
