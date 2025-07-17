package morimensmod.vfx;

import static morimensmod.MorimensMod.makeVFXPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.config.ModSettings;
import morimensmod.util.TexLoader;

public class LargPortraitFlashInEffect extends AbstractGameEffect {
    private Texture img;

    private float offSet_x;

    private final float offSet_y;

    private boolean flipX = false;

    private float flashInTime = ModSettings.EXALT_PROTRAIT_FLASH_IN_TIME;
    private float stayTime = ModSettings.EXALT_PROTRAIT_STAY_TIME;
    private float fadeTime = ModSettings.EXALT_PROTRAIT_FADE_TIME;

    public LargPortraitFlashInEffect(String name) {
        this(name, false);
    }

    public LargPortraitFlashInEffect(String name, boolean flipX) {
        this.img = TexLoader.getTexture(makeVFXPath(name + ".png"));
        this.duration = flashInTime + stayTime + fadeTime;
        this.offSet_x = Settings.WIDTH;
        this.offSet_y = 0.0F * Settings.scale;
        this.scale = (float) Settings.HEIGHT / this.img.getHeight();
        this.flipX = flipX;
        this.color = Color.WHITE.cpy();
    }

    public void update() {
        if (img == null) {
            isDone = true;
            return;
        }
        duration -= Gdx.graphics.getDeltaTime();
        if (duration > stayTime + fadeTime) {
            offSet_x = Settings.WIDTH * (duration - (stayTime + fadeTime)) / flashInTime;
        } else {
            offSet_x = -100.0F * (stayTime + fadeTime - duration) * Settings.xScale;
        }
        if (duration < fadeTime)
            color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - duration / fadeTime);
        if (duration < 0.0F) {
            isDone = true;
            dispose();
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(color);
        if (img == null)
            return;
        float x = flipX
                ? Settings.WIDTH / 2.0F + 300.0F * Settings.scale - img.getWidth() / 2.0F - offSet_x
                : Settings.WIDTH / 2.0F - 300.0F * Settings.scale - img.getWidth() / 2.0F + offSet_x;
        sb.draw(img,
                x,
                Settings.HEIGHT / 2.0F - img.getHeight() / 2.0F + offSet_y,
                img.getWidth() / 2.0F,
                img.getHeight() / 2.0F,
                img.getWidth(), img.getHeight(),
                scale,
                scale,
                0.0F, 0, 0,
                img.getWidth(), img.getHeight(),
                false, false);
    }

    public void dispose() {
        if (img != null) {
            img.dispose();
            img = null;
        }
    }
}
