package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class Defend extends AbstractEasyCard {
    public final static String ID = makeID(Defend.class.getSimpleName());

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STARTER_DEFEND);
        block = baseBlock = 5;
        aliemus = baseAliemus = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        blck();
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeAliemus(5);
    }
}
