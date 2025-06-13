package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType;

public class RealmColorPatch {
    public static class CardColorPatch {
        @SpireEnum
        public static CardColor CHAOS_COLOR;
    }

    public static class LibColorPatch {
        @SpireEnum
        public static LibraryType CHAOS_COLOR;
    }
}
