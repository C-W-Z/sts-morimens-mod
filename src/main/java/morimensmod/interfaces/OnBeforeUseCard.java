package morimensmod.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface OnBeforeUseCard {
    void onBeforeUseCard(AbstractCard card, AbstractPlayer player, AbstractMonster monster);
}
