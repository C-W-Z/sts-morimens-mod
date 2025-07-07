package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.getCleanCopy;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.CustomTags;

public class CombatStance extends AbstractEasyCard implements StartupCard {
    public final static String ID = makeID(CombatStance.class.getSimpleName());

    public CombatStance() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CustomTags.DEFEND);
        block = baseBlock = 5;
        magicNumber = baseMagicNumber = 5;
        misc = 0;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        misc = 0;
        applyPowers();
    }

    @Override
    public boolean atBattleStartPreDraw() {
        misc = 0;
        return false;
    }

    @Override // on retain/discard when player turn end
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        misc += magicNumber;
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }

    @Override
    public void applyPowers() {
        int originalBlock = getCleanCopy(this).baseBlock;
        block = baseBlock = originalBlock + misc;
        int blockAmplify = 100 + baseBlockAmplify + AbstractAwakener.baseBlockAmplify;
        baseBlock = MathUtils.ceil(baseBlock * blockAmplify / 100F);

        super.applySuperPower();

        if (baseBlock != originalBlock)
            isBlockModified = true;
    }
}
