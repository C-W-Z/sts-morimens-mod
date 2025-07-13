package morimensmod.characters;

import morimensmod.cards.chaos.AssaultThesis;
import morimensmod.cards.chaos.Defend;
import morimensmod.cards.chaos.QueensSword;
import morimensmod.cards.chaos.Strike;
import morimensmod.exalts.MundusDecree;
import morimensmod.misc.Animator;
import morimensmod.relics.ChaosRelic;
import morimensmod.relics.RamonaRelic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import basemod.BaseMod;
import basemod.animations.AbstractAnimation;

import static morimensmod.MorimensMod.*;
import static morimensmod.patches.ColorPatch.CardColorPatch.BUFF_COLOR;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.patches.ColorPatch.CardColorPatch.WHEEL_OF_DESTINY_COLOR;
import static morimensmod.util.General.removeModID;

import java.util.ArrayList;

public class Ramona extends AbstractAwakener {

    public static final String ID = makeID(Ramona.class.getSimpleName());
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private static final String CHARSELECT_BUTTON = makeCharacterPath("Ramona/button.png");
    private static final String CHARSELECT_PORTRAIT = makeCharacterPath("Ramona/charBG.png");

    private static final float xOffset = -22;
    private static final float yOffset = -10;

    public Ramona() {
        super(NAMES[0], Enums.RAMONA, "Ramona/main.png", "Ramona/main.png");
        exalt = new MundusDecree();
        baseAliemusRegen = 0;
        baseKeyflareRegen = 60;
        deathResistance = 100;
        baseRealmMastery = 50;

        Animator animator = new Animator();
        animator.addAnimation(
                "Idle_1",
                makeCharacterPath(removeModID(ID) + "/Idle_1.png"),
                6, 17, 1, true, xOffset, yOffset);
        animator.addAnimation(
                "Hit",
                makeCharacterPath(removeModID(ID) + "/Hit.png"),
                4, 5, 0, false, xOffset - 17.5F, yOffset);
        animator.addAnimation(
                "Defence",
                makeCharacterPath(removeModID(ID) + "/Defence.png"),
                4, 8, 1, false, xOffset + 97.5F, yOffset);
        animator.addAnimation(
                "Attack",
                makeCharacterPath(removeModID(ID) + "/Attack.png"),
                9, 3, 2, false, xOffset + 202F, yOffset);
        animator.setDefaultAnim("Idle_1");
        this.animation = animator;

        // in AbstractPlayer.render(), there's
        // if (this.atlas != null && !this.renderCorpse) {
        //     this.renderPlayerImage(sb);
        if (animation.type() != AbstractAnimation.Type.NONE)
            this.atlas = new TextureAtlas();
    }

    @Override
    public void damage(DamageInfo info) {
        int hp = currentHealth;
        int block = currentBlock;
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            if (hp == currentHealth && block > 0 && currentBlock >= 0)
                ((Animator) this.animation).setAnimation("Defence");
            else
                ((Animator) this.animation).setAnimation("Hit");
        }
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
        for (int i = 0; i < 4; i++)
            retVal.add(Strike.ID);
        for (int i = 0; i < 4; i++)
            retVal.add(Defend.ID);
        retVal.add(AssaultThesis.ID);
        retVal.add(QueensSword.ID);
        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(ChaosRelic.ID);
        retVal.add(RamonaRelic.ID);
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
    public AbstractCard.CardColor getCardColor() {
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
        return new Strike();
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
        // @SpireEnum(name = "RAMONA_COLOR")
        // public static AbstractCard.CardColor RAMONA_COLOR;
        // @SpireEnum(name = "RAMONA_COLOR")
        // @SuppressWarnings("unused")
        // public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
