package morimensmod.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static morimensmod.MorimensMod.makePowerPath;
import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.MorimensMod.makeIconPath;

import java.util.HashMap;

public class TexLoader {
    private static HashMap<String, Texture> textures = new HashMap<>();

    /**
     * @param textureString - String path to the texture you want to load relative to resources,
     *                      Example: makeImagePath("missing.png")
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided
     */
    public static Texture getTexture(final String textureString) {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString, true);
            } catch (GdxRuntimeException e) {
                if (textureString.contains("/powers/")) {
                    if (textureString.contains("84.png"))
                        return getTexture(makePowerPath("missing84.png"));
                    else if (textureString.contains("32.png"))
                        return getTexture(makePowerPath("missing32.png"));
                }
                if (textureString.contains("/icons/"))
                    return getTexture(makeIconPath("missing.png"));
                return getTexture(makeUIPath("missing.png"));
            }
        }
        return textures.get(textureString);
    }

    // private static void loadTexture(final String textureString) throws GdxRuntimeException {
    //     loadTexture(textureString, false);
    // }

    private static void loadTexture(final String textureString, boolean linearFilter) throws GdxRuntimeException {
        Texture texture = new Texture(textureString);
        if (linearFilter) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        textures.put(textureString, texture);
    }

    public static boolean testTexture(String filePath) {
        return Gdx.files.internal(filePath).exists();
    }

    public static TextureAtlas.AtlasRegion getTextureAsAtlasRegion(String textureString) {
        Texture texture = getTexture(textureString);
        return ImageHelper.asAtlasRegion(texture);
    }


    @SpirePatch(clz = Texture.class, method="dispose")
    public static class DisposeListener {
        @SpirePrefixPatch
        public static void DisposeListenerPatch(final Texture __instance) {
            textures.entrySet().removeIf(entry -> {
                if (entry.getValue().equals(__instance)) System.out.println("TextureLoader | Removing Texture: " + entry.getKey());
                return entry.getValue().equals(__instance);
            });
        }
    }

    public static AtlasRegion getPowerRegion48(AbstractPower power) {
        return getTextureAsAtlasRegion(makePowerPath(power.getClass().getSimpleName() + "32.png"));
    }

    public static AtlasRegion getPowerRegion128(AbstractPower power) {
        return getTextureAsAtlasRegion(makePowerPath(power.getClass().getSimpleName() + "84.png"));
    }
}
