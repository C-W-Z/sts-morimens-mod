package morimensmod.util;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.getFloats;

import java.util.HashMap;
import java.util.function.Supplier;

import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;

import basemod.BaseMod;
import basemod.BaseMod.GetMonsterGroup;
import morimensmod.monsters.CollaborativeDissolute;
import morimensmod.monsters.DissolutedRatKing;
import morimensmod.monsters.Fastrunner;
import morimensmod.monsters.Hardhitter;
import morimensmod.monsters.InterferenceTypeDissolute;
import morimensmod.monsters.KingOfKids;
import morimensmod.monsters.TheVoidClaimsAll;

public class MonsterLib {

    public static class MonsterEncounter {
        public GetMonsterGroup group;
        public float animScale;
        public String[] actIDs;
        public float[] weights;

        public MonsterEncounter(GetMonsterGroup group, String[] actIDs, float[] weight, float scale) {
            if (actIDs.length != weight.length)
                throw new RuntimeException("actIDs.length != weight.length");
            this.group = group;
            this.animScale = scale;
            this.actIDs = actIDs;
            this.weights = weight;
        }

        public MonsterEncounter(GetMonsterGroup group, String[] actIDs, float weight) {
            this(group, actIDs, getFloats(actIDs.length, weight));
        }

        public MonsterEncounter(GetMonsterGroup group, String[] actIDs, float[] weight) {
            this(group, actIDs, weight, 1F);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float[] weight) {
            this(monsters, actIDs, weight, 1F);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float weight) {
            this(monsters, actIDs, getFloats(actIDs.length, weight));
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float[] weight, float scale) {
            this(() -> new MonsterGroup(monsters.get()), actIDs, weight, scale);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float weight, float scale) {
            this(monsters, actIDs, getFloats(actIDs.length, weight), scale);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String actID, float weight, float scale) {
            this(monsters, new String[] { actID }, weight, scale);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String actID, float weight) {
            this(monsters, new String[] { actID }, weight);
        }
    }

    public static HashMap<String, MonsterEncounter> weakEncounters = new HashMap<>();
    public static HashMap<String, MonsterEncounter> strongEncounters = new HashMap<>();
    public static HashMap<String, MonsterEncounter> eliteEncounters = new HashMap<>();
    // TODO: BossEncounters

    public static void register() {

        // don't remove these without makeID ones for compatible
        weakEncounters.put(
                "1-1-1",
                new MonsterEncounter(() -> new AbstractMonster[] {
                        new KingOfKids(-310, 0),
                        new Hardhitter(-40, -50),
                        new Fastrunner(200, 20)
                }, Exordium.ID, 4));

        weakEncounters.put(
                "1-1-2",
                new MonsterEncounter(() -> new AbstractMonster[] {
                        new Hardhitter(-400, 20),
                        new Hardhitter(-160, -80, 2),
                        new KingOfKids(85, 40),
                        new Fastrunner(300, -30)
                }, Exordium.ID, 4));

        /* ================================================== */

        weakEncounters.put(
                makeID("1-1-1"),
                new MonsterEncounter(() -> new AbstractMonster[] {
                        new KingOfKids(-310, 0),
                        new Hardhitter(-40, -50),
                        new Fastrunner(200, 20)
                }, Exordium.ID, 4));

        weakEncounters.put(
                makeID("1-1-2"),
                new MonsterEncounter(() -> new AbstractMonster[] {
                        new Hardhitter(-400, 20),
                        new Hardhitter(-160, -80, 2),
                        new KingOfKids(85, 40),
                        new Fastrunner(300, -30)
                }, Exordium.ID, 4));

        weakEncounters.put(
                makeID("1-2-2"),
                new MonsterEncounter(() -> new AbstractMonster[] {
                        new CollaborativeDissolute(-400, -10),
                        new DissolutedRatKing(-170, 10),
                        new InterferenceTypeDissolute(110, -20)
                }, new String[] { Exordium.ID, TheCity.ID }, 3));

        eliteEncounters.put(
                TheVoidClaimsAll.ID,
                new MonsterEncounter(() -> new AbstractMonster[] {
                        new TheVoidClaimsAll(-100, -50)
                }, Exordium.ID, 99, 0.8F));

        weakEncounters.forEach((key, value) -> {
            for (int i = 0; i < value.actIDs.length; i++) {
                BaseMod.addMonster(key, value.group);
                BaseMod.addMonsterEncounter(value.actIDs[i], new MonsterInfo(key, value.weights[i]));
            }
        });

        strongEncounters.forEach((key, value) -> {
            for (int i = 0; i < value.actIDs.length; i++) {
                BaseMod.addMonster(key, value.group);
                BaseMod.addStrongMonsterEncounter(value.actIDs[i], new MonsterInfo(key, value.weights[i]));
            }
        });

        eliteEncounters.forEach((key, value) -> {
            for (int i = 0; i < value.actIDs.length; i++) {
                BaseMod.addMonster(key, value.group);
                BaseMod.addEliteEncounter(value.actIDs[i], new MonsterInfo(key, value.weights[i]));
            }
        });
    }
}
