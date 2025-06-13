package morimensmod.characters;

import morimensmod.cards.chaos.AssaultThesis;
import morimensmod.cards.chaos.Defend;
import morimensmod.cards.chaos.QueensSword;
import morimensmod.cards.chaos.Strike;
import morimensmod.relics.TodoItem;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import basemod.BaseMod;

import static morimensmod.MorimensMod.*;
import static morimensmod.util.Wiz.addCardsIntoPool;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.patches.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;

import java.util.ArrayList;

public class Ramona extends AbstractAwakener {

    public static final String ID = makeID(Ramona.class.getSimpleName());
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private static final String CHARSELECT_BUTTON = makeCharacterPath("Ramona/button.png");
    private static final String CHARSELECT_PORTRAIT = makeCharacterPath("Ramona/charBG.png");

    public Ramona() {
        super(NAMES[0], Enums.RAMONA,
                "Ramona/static.scml", "Ramona/shoulder.png",
                "Ramona/shoulder2.png", "Ramona/corpse.png");
    }

    public static void register() {
        BaseMod.addCharacter(new Ramona(), CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, Enums.RAMONA);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                NAMES[0], TEXT[0],
                60, 60, 0, 99, 5,
                this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        retVal.add(AssaultThesis.ID);
        retVal.add(QueensSword.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(TodoItem.ID);
        return retVal;
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool) {
        addCardsIntoPool(tmpPool, CHAOS_COLOR);
        addCardsIntoPool(tmpPool, WHEEL_OF_DESTINY_COLOR);
        return tmpPool;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.RAMONA_COLOR;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        System.out.println("YOU NEED TO SET getStartCardForEvent() in your " + getClass().getSimpleName() + " file!");
        return null;
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Ramona();
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
        public static AbstractPlayer.PlayerClass RAMONA;
        @SpireEnum(name = "RAMONA_COLOR")
        public static AbstractCard.CardColor RAMONA_COLOR;
        @SpireEnum(name = "RAMONA_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
