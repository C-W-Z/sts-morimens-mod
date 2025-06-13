package morimensmod.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
// import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
// import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
// import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;

import static morimensmod.MorimensMod.*;

public abstract class AbstractAwakener extends CustomPlayer {

    public AbstractAwakener(String name, PlayerClass setClass, String spriterAnimationPath, final String SHOULDER1,
            final String SHOULDER2, final String CORPSE) {
        super(
                name,
                setClass,
                new CustomEnergyOrb(orbTextures, makeCharacterPath("ChaosRealm/orb/vfx.png"), null),
                new SpriterAnimation(makeCharacterPath(spriterAnimationPath)));
        initializeClass(
                null,
                makeCharacterPath(SHOULDER1),
                makeCharacterPath(SHOULDER2),
                makeCharacterPath(CORPSE),
                getLoadout(), 20.0F, -10.0F, 166.0F, 327.0F, new EnergyManager(5));

        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    private static final String[] orbTextures = {
            makeCharacterPath("ChaosRealm/orb/layer1.png"),
            makeCharacterPath("ChaosRealm/orb/layer2.png"),
            makeCharacterPath("ChaosRealm/orb/layer3.png"),
            makeCharacterPath("ChaosRealm/orb/layer4.png"),
            makeCharacterPath("ChaosRealm/orb/layer4.png"),
            makeCharacterPath("ChaosRealm/orb/layer6.png"),
            makeCharacterPath("ChaosRealm/orb/layer1d.png"),
            makeCharacterPath("ChaosRealm/orb/layer2d.png"),
            makeCharacterPath("ChaosRealm/orb/layer3d.png"),
            makeCharacterPath("ChaosRealm/orb/layer4d.png"),
            makeCharacterPath("ChaosRealm/orb/layer5d.png"),
    };

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        System.out.println("YOU NEED TO SET getStartCardForEvent() in your " + getClass().getSimpleName() + " file!");
        return null;
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE };
    }
}
