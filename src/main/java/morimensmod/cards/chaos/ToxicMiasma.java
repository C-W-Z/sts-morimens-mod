package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.makeInHand;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.actions.AllEnemyScalePoisonAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.cards.status.Suffocation;
import morimensmod.patches.enums.CustomTags;

public class ToxicMiasma extends AbstractEasyCard {
    public final static String ID = makeID(ToxicMiasma.class.getSimpleName());

    private static final int POISON_SCALE = 2;

    public ToxicMiasma() {
        super(ID, CardImgID.NymphaeaSkill, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ALL_ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        magicNumber = baseMagicNumber = 1; // 幾張窒息
        cardsToPreview = new Suffocation();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AllEnemyScalePoisonAction(p, POISON_SCALE));
        makeInHand(cardsToPreview, magicNumber);
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}
