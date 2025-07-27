package morimensmod.util;

import static morimensmod.MorimensMod.modID;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.AutoAdd;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.cards.symptoms.AbstractSymptomCard;

public class CardLib {

    public static ArrayList<AbstractPosse> posses = new ArrayList<>();
    public static ArrayList<AbstractCard> symptoms = new ArrayList<>();

    public static void initialize() {
        new AutoAdd(modID)
                .packageFilter(AbstractPosse.class)
                .any(AbstractPosse.class, (info, posse) -> posses.add(posse));
        new AutoAdd(modID)
                .packageFilter(AbstractSymptomCard.class)
                .any(AbstractSymptomCard.class, (info, symptom) -> symptoms.add(symptom));
    }

    public static ArrayList<AbstractPosse> getAllPosses() {
        ArrayList<AbstractPosse> pool = new ArrayList<>();
        for (AbstractPosse c : posses)
            pool.add((AbstractPosse) c.makeCopy());
        return pool;
    }

    public static ArrayList<AbstractCard> getAllPosseCards() {
        ArrayList<AbstractCard> pool = new ArrayList<>();
        for (AbstractCard c : posses)
            pool.add(c.makeCopy());
        return pool;
    }

    public static ArrayList<AbstractPosse> getAllPossesExcept(ArrayList<String> posseIDs) {
        ArrayList<AbstractPosse> pool = new ArrayList<>();
        for (AbstractPosse c : posses)
            if (!posseIDs.contains(c.cardID))
                pool.add((AbstractPosse) c.makeCopy());
        return pool;
    }

    public static ArrayList<AbstractCard> getAllSymptoms() {
        ArrayList<AbstractCard> pool = new ArrayList<>();
        for (AbstractCard c : symptoms)
            pool.add(c.makeCopy());
        return pool;
    }

    public static ArrayList<AbstractCard> getRandomSymptoms(int count) {
        ArrayList<AbstractCard> pool = new ArrayList<>();
        for (int i = 0; i < count; i++)
            pool.add(symptoms.get(AbstractDungeon.cardRng.random(symptoms.size() - 1)).makeCopy());
        return pool;
    }
}
