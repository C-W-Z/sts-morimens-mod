package morimensmod.events;

import static morimensmod.MorimensMod.makeEventPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.CardLib.getRandomSymptoms;
import static morimensmod.util.General.normalizeWhitespace;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import morimensmod.cards.buffs.Insight;
import morimensmod.characters.AbstractAwakener;
import morimensmod.config.ModSettings;

public class Junction extends AbstractImageEvent {

    public static final String ID = makeID(Junction.class.getSimpleName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    private static final String title = eventStrings.NAME;

    private enum CurScreen {
        INTRO, RESULT;
    }

    private CurScreen screen;

    private int healAmt;
    private AbstractCard obtainCard;
    private static final int N_SYMPTOM = 1;
    private ArrayList<AbstractCard> obtainSymptoms;

    public Junction() {
        super(title, DESCRIPTIONS[0], makeEventPath(removeModID(ID) + ".png"));

        screen = CurScreen.INTRO;

        healAmt = MathUtils.ceil((p().maxHealth - p().currentHealth) / 2F);
        this.imageEventText.setDialogOption(String.format(OPTIONS[0], healAmt));

        boolean canRouse = true;

        if (p() instanceof AbstractAwakener) {
            AbstractAwakener awaker = (AbstractAwakener) p();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                if (c.cardID.equals(awaker.rouseCard.cardID)) {
                    canRouse = false;
                    break;
                }
        } else {
            canRouse = false;
        }

        if (canRouse) {
            AbstractAwakener awaker = (AbstractAwakener) p();
            obtainCard = awaker.rouseCard.makeCopy();
        } else {
            obtainCard = new Insight();
        }

        if (AbstractDungeon.ascensionLevel >= ModSettings.ASCENSION_LVL.BAD_EVENT) {
            obtainSymptoms = getRandomSymptoms(N_SYMPTOM);
            this.imageEventText.setDialogOption(
                    String.format(
                            OPTIONS[2],
                            "#y" + normalizeWhitespace(obtainCard.name).replaceAll(" ", " #y"),
                            N_SYMPTOM),
                    obtainCard);
        } else {
            this.imageEventText.setDialogOption(
                    String.format(OPTIONS[1], "#y" + normalizeWhitespace(obtainCard.name).replaceAll(" ", " #y")),
                    obtainCard);
        }
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                switch (buttonPressed) {
                    case 0:
                        p().heal(healAmt, true);
                        AbstractEvent.logMetricHeal(title, OPTIONS[4], healAmt);
                        break;
                    case 1:
                        // 究極噁心玩意，到底哪個大聰明把獲得卡牌跟特效綁定在一起的
                        AbstractDungeon.effectList.add(
                                new ShowCardAndObtainEffect(obtainCard, Settings.WIDTH / 2F, Settings.HEIGHT / 2F));
                        if (AbstractDungeon.ascensionLevel >= ModSettings.ASCENSION_LVL.BAD_EVENT) {
                            List<String> tempList = new ArrayList<>();
                            tempList.add(obtainCard.cardID);
                            for (AbstractCard s : obtainSymptoms) {
                                AbstractDungeon.effectList.add(
                                        new ShowCardAndObtainEffect(s, Settings.WIDTH / 2F, Settings.HEIGHT / 2F));
                                tempList.add(s.cardID);
                            }
                            AbstractEvent.logMetricObtainCards(title, OPTIONS[6], tempList);
                        } else {
                            AbstractEvent.logMetricObtainCard(title, OPTIONS[5], obtainCard);
                        }
                        break;
                    default:
                        logMetricIgnored(title);
                        break;
                }
                this.imageEventText.clearAllDialogs();
                this.imageEventText.setDialogOption(OPTIONS[3]);
                this.screen = CurScreen.RESULT;
                break;
            default:
                this.openMap();
                break;
        }
    }
}
