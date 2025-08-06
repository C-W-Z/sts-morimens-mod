package morimensmod.util;

import static morimensmod.MorimensMod.modID;
import static morimensmod.util.Wiz.p;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.AutoAdd;
import morimensmod.cards.posses.AbstractPosse;
import morimensmod.cards.symptoms.AbstractSymptomCard;
import morimensmod.cards.wheelofdestiny.AbstractWheelOfDestiny;

public class CardLib {

    public static ArrayList<AbstractPosse> posses = new ArrayList<>();
    public static ArrayList<AbstractCard> symptoms = new ArrayList<>();
    public static ArrayList<AbstractCard> wheelOfDestiny = new ArrayList<>();

    public static void initialize() {
        new AutoAdd(modID)
                .packageFilter(AbstractPosse.class)
                .any(AbstractPosse.class, (info, posse) -> posses.add(posse));
        new AutoAdd(modID)
                .packageFilter(AbstractSymptomCard.class)
                .any(AbstractSymptomCard.class, (info, symptom) -> symptoms.add(symptom));
        new AutoAdd(modID)
                .packageFilter(AbstractWheelOfDestiny.class)
                .any(AbstractWheelOfDestiny.class, (info, wheel) -> wheelOfDestiny.add(wheel));
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
            pool.add(symptoms.get(AbstractDungeon.cardRandomRng.random(symptoms.size() - 1)).makeCopy());
        return pool;
    }

    public static ArrayList<AbstractCard> getAllWheelOfDestiny() {
        ArrayList<AbstractCard> pool = new ArrayList<>();
        for (AbstractCard c : wheelOfDestiny)
            pool.add(c.makeCopy());
        return pool;
    }

    public static ArrayList<AbstractCard> getRandomWheelOfDestiny(int count) {
        ArrayList<AbstractCard> pool;
        if (p() != null) {
            pool = (ArrayList<AbstractCard>) wheelOfDestiny.stream()
                    .filter(c -> !p().masterDeck.group.stream().anyMatch(_c -> _c.cardID == c.cardID))
                    .map(c -> c.makeCopy())
                    .collect(Collectors.toList());
        } else {
            pool = getAllWheelOfDestiny();
        }
        if (pool.size() <= count)
            return pool;
        ArrayList<AbstractCard> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int j = AbstractDungeon.cardRandomRng.random(pool.size() - 1);
            result.add(pool.get(j).makeCopy());
            pool.remove(j);
        }
        return result;
    }

    public static ArrayList<AbstractCard> getRandomWheelOfDestiny(int count, CardRarity rarity) {
        ArrayList<AbstractCard> pool;
        if (p() != null) {
            pool = (ArrayList<AbstractCard>) wheelOfDestiny.stream()
                    .filter(c -> c.rarity == rarity
                            && !p().masterDeck.group.stream().anyMatch(_c -> _c.cardID == c.cardID))
                    .map(c -> c.makeCopy())
                    .collect(Collectors.toList());
        } else {
            pool = (ArrayList<AbstractCard>) wheelOfDestiny.stream()
                    .filter(c -> c.rarity == rarity)
                    .map(c -> c.makeCopy())
                    .collect(Collectors.toList());
        }
        if (pool.size() <= count)
            return pool;
        ArrayList<AbstractCard> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int j = AbstractDungeon.cardRandomRng.random(pool.size() - 1);
            result.add(pool.get(j).makeCopy());
            pool.remove(j);
        }
        return result;
    }
}
