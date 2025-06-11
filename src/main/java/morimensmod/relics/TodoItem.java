package morimensmod.relics;

import static morimensmod.MorimensMod.makeID;

import morimensmod.characters.CharacterFile;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, CharacterFile.Enums.TODO_COLOR);
    }
}
