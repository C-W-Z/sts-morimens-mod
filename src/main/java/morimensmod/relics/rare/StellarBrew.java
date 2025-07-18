package morimensmod.relics.rare;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.AWAKENER_COLOR;
import static morimensmod.util.Wiz.isCommandCard;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.relics.AbstractEasyRelic;

public class StellarBrew extends AbstractEasyRelic {
    public static final String ID = makeID(StellarBrew.class.getSimpleName());

    private static final int COMMAND_NUM = 8;

    public StellarBrew() {
        super(ID, RelicTier.RARE, LandingSound.CLINK, AWAKENER_COLOR);
        counter = 0;
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.purgeOnUse || !isCommandCard(card))
            return;
        counter++;
        if (counter < COMMAND_NUM)
            return;
        counter -= COMMAND_NUM;

        flash();
        AbstractMonster m = null;
        if (action.target instanceof AbstractMonster) {
            m = (AbstractMonster) action.target;
        }

        AbstractCard tmp = card.makeSameInstanceOf();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
        tmp.target_y = (float) Settings.HEIGHT / 2.0F;
        if (m != null) {
            tmp.calculateCardDamage(m);
        }

        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], COMMAND_NUM);
    }
}
