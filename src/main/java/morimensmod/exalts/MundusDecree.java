package morimensmod.exalts;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.*;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.AllEnemyApplyPowerAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import morimensmod.actions.EasyModalChoiceAction;
import morimensmod.actions.KeyflareChangeAction;
import morimensmod.actions.MundusDecreeAction;
import morimensmod.cards.PileModalSelectCard;
import morimensmod.cards.buffs.Insight;
import morimensmod.config.ModSettings;
import morimensmod.powers.PosseTwicePower;
import morimensmod.vfx.LargPortraitFlashInEffect;

public class MundusDecree extends AbstractExalt {

    public static final String ID = makeID(MundusDecree.class.getSimpleName());
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    @Override
    public void exalt() {
        atb(new VFXAction(p(), new LargPortraitFlashInEffect(removeModID(ID)), ModSettings.EXALT_PROTRAIT_DURATION, true));

        atb(new KeyflareChangeAction(p(), 200));

        atb(new WaitAction(Settings.ACTION_DUR_MED));

        ArrayList<AbstractCard> cardList = new ArrayList<>();

        // 選擇的牌要用att，才會在"靈感洗入抽牌堆"之前被放入手中
        for (AbstractCard c : drawPile().group) {
            cardList.add(new PileModalSelectCard(c, () -> att(new MundusDecreeAction(c))));
        }

        for (AbstractCard c : discardPile().group) {
            cardList.add(new PileModalSelectCard(c, () -> att(new MundusDecreeAction(c))));
        }

        // TODO: 參考
        // atb(new MultiGroupMoveAction(CardGroupType.HAND, 1, CardGroupType.DRAW_PILE, CardGroupType.DISCARD_PILE));

        atb(new EasyModalChoiceAction(cardList));

        shuffleIn(new Insight());
    }

    @Override
    public void overExalt() {
        exalt();

        // 使所有敵人虛弱易傷一回合
        atb(new AllEnemyApplyPowerAction(p(), 1, (mo) -> new VulnerablePower(mo, 1, false)));
        atb(new AllEnemyApplyPowerAction(p(), 1, (mo) -> new WeakPower(mo, 1, false)));

        // 使下次鑰令生效兩次
        applyToSelf(new PosseTwicePower(p(), 1));
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
