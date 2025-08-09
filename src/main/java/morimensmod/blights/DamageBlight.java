package morimensmod.blights;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class DamageBlight extends AbstractMorimensBlight {

    public static final String ID = makeID(DamageBlight.class.getSimpleName());
    public static final BlightStrings TEXT = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = TEXT.NAME;
    public static final String[] DESCRIPTION = TEXT.DESCRIPTION;

    public static final int[] DAMAGE_AMPLIFY = { 0, 5, 15, 25, 40, 55, 70, 90, 120, 150, 200 };
    public static final int MAX_LVL = DAMAGE_AMPLIFY.length - 1;

    public DamageBlight() {
        this(0);
    }

    public DamageBlight(int amount) {
        super(ID, NAME, DESCRIPTION[0], true);
        counter = amount;
        updateDescription();
    }

    public void limitCounter() {
        if (counter > MAX_LVL)
            counter = MAX_LVL;
    }

    @Override
    public int monsterDamageAmplify() {
        limitCounter();
        return DAMAGE_AMPLIFY[counter];
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
        this.description = String.format(DESCRIPTION[0], monsterDamageAmplify());
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }
}
