package morimensmod.characters;

import morimensmod.cards.chaos.PredeterminedStrike;
import morimensmod.cards.chaos.SightUnbound;
import morimensmod.cards.chaos.rouse.EntropyUndone;
import morimensmod.cards.chaos.unique.Defend_RamonaTimeworm;
import morimensmod.cards.chaos.unique.Strike_RamonaTimeworm;
import morimensmod.config.ModSettings;
import morimensmod.exalts.ParadoxConverged;
import morimensmod.misc.Animator;
import morimensmod.relics.starter.ChaosRelic;
import morimensmod.relics.starter.RamonaTimewormRelic;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import basemod.BaseMod;
import basemod.animations.AbstractAnimation;

import static morimensmod.MorimensMod.*;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.BUFF_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.General.removeModID;

import java.util.ArrayList;

public class RamonaTimeworm extends AbstractAwakener {

    public static final String ID = makeID(RamonaTimeworm.class.getSimpleName());
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private static final String CHARSELECT_BUTTON = makeCharacterPath("RamonaTimeworm/button.png");
    private static final String CHARSELECT_PORTRAIT = makeCharacterPath("RamonaTimeworm/charBG.png");

    private static final float xOffset = 0;
    private static final float yOffset = -14;

    public RamonaTimeworm() {
        super(NAMES[0], Enums.RamonaTimeworm, getAnimation());
        exalt = new ParadoxConverged();
        rouseCard = new EntropyUndone();
        baseAliemusRegen = 0;
        baseKeyflareRegen = 60;
        deathResistance = 100;
        baseRealmMastery = 50;
    }

    private static AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.PLAYER_IDLE_ANIM,
                makeCharacterPath(removeModID(ID) + "/" + ModSettings.PLAYER_IDLE_ANIM + ".png"),
                7, 9, 2, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_HIT_ANIM,
                makeCharacterPath(removeModID(ID) + "/" + ModSettings.PLAYER_HIT_ANIM + ".png"),
                4, 5, 0, false, xOffset - 24F, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_DEFENCE_ANIM,
                makeCharacterPath(removeModID(ID) + "/" + ModSettings.PLAYER_DEFENCE_ANIM + ".png"),
                8, 6, 1, false, xOffset - 5F, yOffset - 15F);
        animator.addAnimation(
                ModSettings.PLAYER_ATTACK_ANIM,
                makeCharacterPath(removeModID(ID) + "/" + ModSettings.PLAYER_ATTACK_ANIM + ".png"),
                7, 4, 2, false, xOffset + 105F, yOffset);
        animator.setDefaultAnim(ModSettings.PLAYER_IDLE_ANIM);
        return animator;
    }

    public static void register() {
        BaseMod.addCharacter(new RamonaTimeworm(), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, Enums.RamonaTimeworm);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                NAMES[0], TEXT[0],
                120, 120, 0, 99, 5,
                this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++)
            retVal.add(Strike_RamonaTimeworm.ID);
        for (int i = 0; i < 4; i++)
            retVal.add(Defend_RamonaTimeworm.ID);
        retVal.add(SightUnbound.ID);
        retVal.add(PredeterminedStrike.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(ChaosRelic.ID);
        retVal.add(RamonaTimewormRelic.ID);
        return retVal;
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        CardLibrary.addCardsIntoPool(tmpPool, CHAOS_COLOR);
        CardLibrary.addCardsIntoPool(tmpPool, WHEEL_OF_DESTINY_COLOR);
        CardLibrary.addCardsIntoPool(tmpPool, BUFF_COLOR);
        // CardLibrary.addCardsIntoPool(tmpPool, SYMPTOM_COLOR);
        // CardLibrary.addCardsIntoPool(tmpPool, STATUS_COLOR);
        return tmpPool;
    }

    @Override
    public CardColor getRealmColor() {
        return CHAOS_COLOR;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        // System.out.println("YOU NEED TO SET getStartCardForEvent() in your " +
        // getClass().getSimpleName() + " file!");
        return new Strike_RamonaTimeworm();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new RamonaTimeworm();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE };
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public Color getCardTrailColor() {
        return new Color(1, 1, 1, 1);
    }

    @Override
    public Color getCardRenderColor() {
        return new Color(1, 1, 1, 1);
    }

    @Override
    public Color getSlashAttackColor() {
        return new Color(1, 1, 1, 1);
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass RamonaTimeworm;
    }
}
