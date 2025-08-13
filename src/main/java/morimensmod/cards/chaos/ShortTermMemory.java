package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;

public class ShortTermMemory extends AbstractEasyCard {
    public final static String ID = makeID(ShortTermMemory.class.getSimpleName());

    public ShortTermMemory() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CHAOS_COLOR, CardImgID.Nautila.ID);
        tags.add(CustomTags.COMMAND);
        block = baseBlock = 4;
        aliemus = baseAliemus = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeAliemus(10);
    }
}
