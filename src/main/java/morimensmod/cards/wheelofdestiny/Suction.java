package morimensmod.cards.wheelofdestiny;

import static morimensmod.MorimensMod.makeID;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import morimensmod.cards.buffs.Insight;

public class Suction extends AbstractWheelOfDestiny {
    public final static String ID = makeID(Suction.class.getSimpleName());

    public Suction() {
        super(ID, CardRarity.COMMON);
        magicNumber = baseMagicNumber = 10;
    }

    @Override
    public void upp() {
    }

    @Override
    public int onRestToChangeHealAmount(int healAmount) {
        return healAmount + (int) (AbstractDungeon.player.maxHealth * magicNumber / 100F);
    }

    @Override
    public boolean onRest() {
        if (!upgraded)
            return false;
        AbstractDungeon.effectList.add(
                new ShowCardAndObtainEffect(new Insight(), Settings.WIDTH / 2F, Settings.HEIGHT / 2F));
        return true;
    }
}
