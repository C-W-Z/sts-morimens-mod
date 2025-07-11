package morimensmod.interfaces;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface OnBeforeDamaged {
    default int onBeforeDamaged(DamageInfo info, int damageAmount) {
        return damageAmount;
    }
}
