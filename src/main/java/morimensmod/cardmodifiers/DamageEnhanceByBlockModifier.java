package morimensmod.cardmodifiers;

import basemod.abstracts.AbstractCardModifier;
// import basemod.helpers.CardModifierManager;

import static morimensmod.MorimensMod.makeID;

// import java.util.ArrayList;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DamageEnhanceByBlockModifier extends AbstractCardModifier {

    // private static final Logger logger = LogManager.getLogger(DamageEnhanceByBlockModifier.class);

    public static final String ID = makeID(DamageEnhanceByBlockModifier.class.getSimpleName());

    int percent;

    public DamageEnhanceByBlockModifier(int percent) {
        this.percent = percent;
    }

    // @Override
    // public boolean shouldApply(AbstractCard card) {
    //     ArrayList<AbstractCardModifier> thisMods = CardModifierManager.getModifiers(card, ID);
    //     if (thisMods.isEmpty())
    //         return true;
    //     if (thisMods.size() > 1)
    //         logger.error("Card: " + card.cardID + " has " + thisMods.size() + " DamageEnhanceByBlockModifiers");
    //     ((DamageEnhanceByBlockModifier) thisMods.get(0)).percent += percent;
    //     return false;
    // }

    public AbstractCardModifier makeCopy() {
        return new DamageEnhanceByBlockModifier(percent);
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public float modifyDamage(float damage, DamageType type, AbstractCard card, AbstractMonster target) {
        if (AbstractDungeon.player == null)
            return damage;
        return damage + MathUtils.ceil(AbstractDungeon.player.currentBlock * percent / 100F);
    }
}
