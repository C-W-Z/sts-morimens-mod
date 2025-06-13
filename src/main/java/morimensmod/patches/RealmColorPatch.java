package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType;

public class RealmColorPatch {
    public static class CardColorPatch {
        @SpireEnum
        public static CardColor CHAOS_COLOR;
        @SpireEnum
        public static CardColor AEQUOR_COLOR;
        @SpireEnum
        public static CardColor CARO_COLOR;
        @SpireEnum
        public static CardColor ULTRA_COLOR;
    }

    public static class LibColorPatch {
        @SpireEnum
        public static LibraryType CHAOS_COLOR;
        @SpireEnum
        public static LibraryType AEQUOR_COLOR;
        @SpireEnum
        public static LibraryType CARO_COLOR;
        @SpireEnum
        public static LibraryType ULTRA_COLOR;
    }
}
