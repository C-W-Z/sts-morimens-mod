package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.actB;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.MoveFromDrawPileAndChangeCostAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.PileModalSelectCard;
import morimensmod.patches.CustomTags;

public class UnexpectedGain extends AbstractEasyCard {
    public final static String ID = makeID(UnexpectedGain.class.getSimpleName());

    public UnexpectedGain() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damage = baseDamage = 7;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 50; // 50% -1費
        secondMagic = baseSecondMagic = 0; // 50% -2費
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                dmgTop(m, AttackEffect.NONE);
            });

        ArrayList<AbstractCard> cardList = new ArrayList<>();

        float r = AbstractDungeon.cardRandomRng.random();
        final int costReduce = (r < secondMagic / 100F) ? -2 : ((r < (magicNumber + secondMagic) / 100F) ? -1 : 0);

        for (AbstractCard c : drawPile().group) {
            cardList.add(new PileModalSelectCard(c,
                    () -> addToTop(new MoveFromDrawPileAndChangeCostAction(c, costReduce))));
        }

        addToBot(new WaitAction(Settings.ACTION_DUR_MED));

        addToBot(new EasyModalChoiceAction(cardList));
    }

    @Override
    public void upp() {
        upgradeSecondMagic(50);
    }
}
