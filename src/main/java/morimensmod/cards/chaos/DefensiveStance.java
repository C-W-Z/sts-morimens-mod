package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class DefensiveStance extends AbstractEasyCard {
    public final static String ID = makeID(DefensiveStance.class.getSimpleName());

    private static final int BLOCK_SCALE = 2;

    public DefensiveStance() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, null, p.currentBlock * (BLOCK_SCALE - 1)));
    }

    @Override
    public void upp() {
        selfRetain = true;
    }
}
