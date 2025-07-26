package morimensmod.events;

import static morimensmod.MorimensMod.makeEventPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.CardLib.getRandomSymptoms;
import static morimensmod.util.General.normalizeWhitespace;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.getSymptomsInDeckForPurge;

import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import morimensmod.characters.AbstractAwakener;

public class PoolOfGore extends AbstractImageEvent {

    public static final String ID = makeID(PoolOfGore.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;

    private enum CurScreen {
        INTRO, PURGE, RESULT;
    }

    private CurScreen screen;
    private boolean purgeResult = false;
    private CardGroup purgeGroup;

    private int maxHPAmt;
    private static final int ALIEMUS_DEDUCT = 100;
    private static final int SYMPTOM_REMOVE = 1;
    private static final int SYMPTOM_INFECT = 2;
    private int healAmt;
    private AbstractCard symptomInfect;
    private List<String> symptomsInfect;

    public PoolOfGore() {
        super(title, DESCRIPTIONS[0], makeEventPath(removeModID(ID) + ".png"));

        screen = CurScreen.INTRO;

        maxHPAmt = MathUtils.ceil(p().maxHealth * 0.05F);
        healAmt = MathUtils.ceil((p().maxHealth - p().currentHealth) / 2F);

        boolean firstOptionDisabled = false;
        boolean thirdOptionDisabled = false;
        if (p() instanceof AbstractAwakener) {
            if (AbstractAwakener.getAliemus() <= 0)
                firstOptionDisabled = true;
            if (AbstractAwakener.getKeyflare() <= 0)
                thirdOptionDisabled = true;
        } else {
            firstOptionDisabled = true;
            thirdOptionDisabled = true;
        }

        symptomInfect = getRandomSymptoms(1).get(0);
        symptomsInfect = Arrays.asList(symptomInfect.name, symptomInfect.name);

        this.imageEventText.setDialogOption(
                String.format(OPTIONS[0], maxHPAmt, ALIEMUS_DEDUCT, SYMPTOM_REMOVE), firstOptionDisabled);
        this.imageEventText.setDialogOption(
                String.format(OPTIONS[1], maxHPAmt, healAmt, SYMPTOM_INFECT,
                        "#r" + normalizeWhitespace(symptomInfect.name).replaceAll(" ", " #r")),
                symptomInfect);
        this.imageEventText.setDialogOption(
                String.format(OPTIONS[2], maxHPAmt), thirdOptionDisabled);
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.updateBodyText(DESCRIPTIONS[buttonPressed + 1]);
                p().increaseMaxHp(maxHPAmt, true);
                switch (buttonPressed) {
                    case 0:
                        if (p() instanceof AbstractAwakener)
                            AbstractAwakener.changeAliemus(-100);
                        purgeGroup = CardGroup.getGroupWithoutBottledCards(getSymptomsInDeckForPurge());
                        if (purgeGroup.size() >= SYMPTOM_REMOVE) {
                            this.imageEventText.clearAllDialogs();
                            this.imageEventText.setDialogOption(OPTIONS[7]);
                            this.screen = CurScreen.PURGE;
                            return;
                        } else
                            break;
                    case 1:
                        p().heal(healAmt, true);
                        for (int i = 0; i < SYMPTOM_INFECT; i++) {
                            // 究極噁心玩意，到底哪個大聰明把獲得卡牌跟特效綁定在一起的
                            AbstractDungeon.effectList.add(
                                    new ShowCardAndObtainEffect(
                                            symptomInfect.makeCopy(),
                                            Settings.WIDTH / 2F,
                                            Settings.HEIGHT / 2F));
                        }
                        AbstractEvent.logMetric(title, OPTIONS[5], symptomsInfect,
                                null, null, null, null, null, null,
                                0, healAmt, 0, maxHPAmt, 0, 0);
                        break;
                    case 2:
                        if (p() instanceof AbstractAwakener)
                            AbstractAwakener.setKeyflare(0);
                        AbstractEvent.logMetricMaxHPGain(title, OPTIONS[6], maxHPAmt);
                        break;
                    default:
                        logMetricIgnored(title);
                        break;
                }
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                this.screen = CurScreen.RESULT;
                break;
            case PURGE:
                AbstractDungeon.gridSelectScreen.open(
                        purgeGroup, SYMPTOM_REMOVE, OPTIONS[8], false, false, false, true);
                this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                this.purgeResult = true;
                this.screen = CurScreen.RESULT;
                break;
            default:
                this.openMap();
                break;
        }
    }

    public void update() {
        super.update();
        purgeLogic();
        if (this.waitForInput)
            buttonEffect(GenericEventDialog.getSelectedOption());
    }

    private void purgeLogic() {
        if (this.purgeResult && !AbstractDungeon.isScreenUp
                && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 2, Settings.HEIGHT / 2));
            AbstractEvent.logMetricCardRemovalHealMaxHPUp(title, OPTIONS[4], c, 0, maxHPAmt);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.purgeResult = false;
        }
    }
}
