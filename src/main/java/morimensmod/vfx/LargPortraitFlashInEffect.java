package morimensmod.vfx;

import static morimensmod.MorimensMod.makeVFXPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.util.TexLoader;

public class LargPortraitFlashInEffect extends AbstractGameEffect {
    private Texture img;

    private final Color color = Color.WHITE.cpy();

    private float offSet_x;

    private final float offSet_y;

    private boolean flipX = false;

    public LargPortraitFlashInEffect(String name) {
        this(name, false);
    }

    public LargPortraitFlashInEffect(String name, boolean flipX) {
        this.img = TexLoader.getTexture(makeVFXPath(name + ".png"));
        this.duration = 1.75F;
        this.offSet_x = Settings.WIDTH;
        this.offSet_y = 0.0F * Settings.scale;
        this.scale = (float) Settings.HEIGHT / this.img.getHeight();
        this.flipX = flipX;
    }

    public void update() {
        if (this.img == null) {
            this.isDone = true;
            return;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > 1.5F) {
            this.offSet_x = Settings.WIDTH * (this.duration - 1.5F) * 4.0F;
        } else {
            this.offSet_x = -200.0F * (1.5F - this.duration) * 0.5F * Settings.xScale / getScale();
        }
        if (this.duration < 0.5F)
            this.color.a = Interpolation.fade.apply(1.0F, 0.0F, 1.0F - this.duration * 2.0F);
        if (this.duration < 0.0F) {
            this.isDone = true;
            dispose();
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        if (this.img != null)
            if (this.flipX) {
                sb.draw(this.img,
                        Settings.WIDTH / 2.0F + 300.0F * Settings.scale - this.img.getWidth() / 2.0F - this.offSet_x,
                        Settings.HEIGHT / 2.0F - this.img.getHeight() / 2.0F + this.offSet_y,
                        this.img.getWidth() / 2.0F,
                        this.img.getHeight() / 2.0F,
                        this.img.getWidth(), this.img.getHeight(),
                        getScale() * this.scale * Settings.scale,
                        getScale() * this.scale * Settings.scale,
                        0.0F, 0, 0,
                        this.img.getWidth(), this.img.getHeight(),
                        false, false);
            } else {
                sb.draw(this.img,
                        Settings.WIDTH / 2.0F - 300.0F * Settings.scale - this.img.getWidth() / 2.0F + this.offSet_x,
                        Settings.HEIGHT / 2.0F - this.img.getHeight() / 2.0F + this.offSet_y,
                        this.img.getWidth() / 2.0F,
                        this.img.getHeight() / 2.0F,
                        this.img.getWidth(), this.img.getHeight(),
                        getScale() * this.scale * Settings.scale,
                        getScale() * this.scale * Settings.scale,
                        0.0F, 0, 0,
                        this.img.getWidth(), this.img.getHeight(),
                        false, false);
            }
    }

    private float getScale() {
        if (Settings.isFourByThree)
            return 1.4F;
        if (Settings.isSixteenByTen)
            return 1.12F;
        return 1.0F;
    }

    public void dispose() {
        if (this.img != null) {
            this.img.dispose();
            this.img = null;
        }
    }
}