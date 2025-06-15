package morimensmod.exalts;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.atb;
import static morimensmod.util.Wiz.discardPile;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.shuffleIn;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.MundusDecreeAction;
import morimensmod.cards.PileModalSelectCard;
import morimensmod.cards.buff.Insight;

public class MundusDecree extends AbstractExalt {

    public static final String ID = makeID(MundusDecree.class.getSimpleName());

    public MundusDecree() {
        UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);
    }

    @Override
    public void exalt() {
        ArrayList<AbstractCard> cardList = new ArrayList<>();

        for (AbstractCard c : drawPile().group) {
            System.out.println("c:" + c.name);
            cardList.add(new PileModalSelectCard(c, () -> atb(new MundusDecreeAction(c))));
        }

        for (AbstractCard c : discardPile().group) {
            System.out.println("c:" + c.name);
            cardList.add(new PileModalSelectCard(c, () -> atb(new MundusDecreeAction(c))));
        }

        atb(new EasyModalChoiceAction(cardList));

        shuffleIn(new Insight());
    }

    @Override
    public void overExalt() {
        exalt();

        // 使所有敵人虛弱易傷一回合
        atb(new AllEnemyApplyPowerAction(p(), 1, (mo) -> new WeakPower(mo, 1, false)));
        atb(new AllEnemyApplyPowerAction(p(), 1, (mo) -> new VulnerablePower(mo, 1, false)));

        // TODO: 使下次鑰令生效兩次
    }

    @Override
    public String getExaltTitle() {
        return UI_STRINGS.TEXT[0];
    }

    @Override
    public String getExaltDescription() {
        return UI_STRINGS.TEXT[1];
    }

    @Override
    public String getOverExaltTitle() {
        return UI_STRINGS.EXTRA_TEXT[0];
    }

    @Override
    public String getOverExaltDescription() {
        return UI_STRINGS.EXTRA_TEXT[1];
    }
}
