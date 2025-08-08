package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import morimensmod.cardmodifiers.AddBlockAtTurnEndModifier;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;

public class CombatStance extends AbstractEasyCard {
    public final static String ID = makeID(CombatStance.class.getSimpleName());

    public CombatStance() {
        super(ID, CardImgID.AlvaSkill, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CustomTags.DEFEND);
        block = baseBlock = 5;
        magicNumber = baseMagicNumber = 5;
        selfRetain = true;
        CardModifierManager.addModifier(this, new AddBlockAtTurnEndModifier(magicNumber));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
        CardModifierManager.addModifier(this, new AddBlockAtTurnEndModifier(1));
    }
}
