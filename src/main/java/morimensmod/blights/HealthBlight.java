package morimensmod.blights;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class HealthBlight extends AbstractMorimensBlight {

    public static final String ID = makeID(HealthBlight.class.getSimpleName());
    public static final BlightStrings TEXT = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = TEXT.NAME;
    public static final String[] DESCRIPTION = TEXT.DESCRIPTION;

    public static final int[] HEALTH_AMPLIFY = { 0, 25, 75, 125, 200, 275, 350, 450, 600, 750, 1000 };
    public static final int MAX_LVL = HEALTH_AMPLIFY.length - 1;

    public HealthBlight() {
        this(0);
    }

    public HealthBlight(int amount) {
        super(ID, NAME, DESCRIPTION[0], true);
        counter = amount;
        updateDescription();
    }

    public void limitCounter() {
        if (counter > MAX_LVL)
            counter = MAX_LVL;
    }

    @Override
    public int monsterHealthAmplify() {
        limitCounter();
        return HEALTH_AMPLIFY[counter];
    }

    @Override
    public void setCounter(int counter) {
        super.setCounter(counter);
        limitCounter();
        updateDescription();
    }

    @Override
    public void incrementUp() {
        flash();
        this.counter++;
        limitCounter();
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = String.format(DESCRIPTION[0], monsterHealthAmplify());
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }
}
