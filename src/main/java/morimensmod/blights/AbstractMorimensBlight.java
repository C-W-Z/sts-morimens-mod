package morimensmod.blights;

import static morimensmod.MorimensMod.makeBlightPath;
import static morimensmod.util.General.removeModID;

import com.megacrit.cardcrawl.blights.AbstractBlight;

import morimensmod.util.TexLoader;

public abstract class AbstractMorimensBlight extends AbstractBlight {

    public AbstractMorimensBlight(String ID, String name, String description, boolean unique) {
        super(ID, name, description, "maze.png", unique);
        this.img = TexLoader.getTexture(makeBlightPath(removeModID(ID) + ".png"));
        this.outlineImg = TexLoader.getTexture(makeBlightPath(removeModID(ID) + "Outline.png"));
    }

    public int monsterDamageAmplify() {
        return 0;
    }

    public int monsterHealthAmplify() {
        return 0;
    }
}
