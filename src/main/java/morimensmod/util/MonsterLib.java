package morimensmod.util;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeCharacterPath;
import static morimensmod.MorimensMod.makeUIPath;
import static morimensmod.util.General.getFloats;
import static morimensmod.util.General.removeModID;

import java.util.HashMap;
import java.util.function.Supplier;

import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.MonsterInfo;

import basemod.BaseMod;
import basemod.BaseMod.GetMonsterGroup;
import morimensmod.characters.Lotan;
import morimensmod.monsters.bosses.LeighBoss;
import morimensmod.monsters.bosses.LotanBoss;
import morimensmod.monsters.elites.DevouringHowl;
import morimensmod.monsters.elites.IronPickaxeLucen;
import morimensmod.monsters.elites.TheVoidClaimsAll;
import morimensmod.monsters.enemies.CollaborativeDissolute;
import morimensmod.monsters.enemies.DissolutedHumanoid;
import morimensmod.monsters.enemies.DissolutedRatKing;
import morimensmod.monsters.enemies.Fastrunner;
import morimensmod.monsters.enemies.Hardhitter;
import morimensmod.monsters.enemies.InterferenceTypeDissolute;
import morimensmod.monsters.enemies.KingOfKids;

public class MonsterLib {

    public static class MonsterEncounter {
        public GetMonsterGroup group;
        public float animScale;
        public String[] actIDs;
        public float[] weights;
        public String[] mapIcons;

        public MonsterEncounter(GetMonsterGroup group, String[] actIDs, float[] weight, float scale,
                String[] mapIcons) {
            if (actIDs.length != weight.length)
                throw new RuntimeException("actIDs.length != weight.length");
            this.group = group;
            this.animScale = scale;
            this.actIDs = actIDs;
            this.weights = weight;
            this.mapIcons = mapIcons;
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float[] weight, float scale,
                String[] mapIcons) {
            this(() -> new MonsterGroup(monsters.get()), actIDs, weight, scale, mapIcons);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float[] weight, float scale) {
            this(monsters, actIDs, weight, scale, new String[] {});
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float weight, float scale) {
            this(monsters, actIDs, getFloats(actIDs.length, weight), scale);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String actID, float weight, float scale) {
            this(monsters, new String[] { actID }, weight, scale);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float[] weight) {
            this(monsters, actIDs, weight, 1F);
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float weight) {
            this(monsters, actIDs, getFloats(actIDs.length, weight));
        }

        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String actID, float weight) {
            this(monsters, new String[] { actID }, weight);
        }

        // for Bosses
        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String[] actIDs, float scale, String[] mapIcons) {
            this(monsters, actIDs, getFloats(actIDs.length, 1F), scale, mapIcons);
        }

        // for Bosses
        public MonsterEncounter(Supplier<AbstractMonster[]> monsters, String actID, float scale, String[] mapIcons) {
            this(monsters, new String[] { actID }, new float[] { 1F }, scale, mapIcons);
        }
    }

    public static HashMap<String, MonsterEncounter> weakEncounters = new HashMap<>();
    public static HashMap<String, MonsterEncounter> strongEncounters = new HashMap<>();
    public static HashMap<String, MonsterEncounter> eliteEncounters = new HashMap<>();
    public static HashMap<String, MonsterEncounter> bosses = new HashMap<>();

    public static void initialize() {
        // don't remove these without makeID ones for compatible
        weakEncounters.put("1-1-1", new MonsterEncounter(() -> new AbstractMonster[] {
                new KingOfKids(-310, 0),
                new Hardhitter(-40, -50),
                new Fastrunner(200, 20)
        }, Exordium.ID, 0));

        weakEncounters.put("1-1-2", new MonsterEncounter(() -> new AbstractMonster[] {
                new Hardhitter(-400, 20),
                new Hardhitter(-160, -80, 2),
                new KingOfKids(85, 40),
                new Fastrunner(300, -30)
        }, Exordium.ID, 0));

        /* ================================================== */

        weakEncounters.put(makeID("1-1-1"), new MonsterEncounter(() -> new AbstractMonster[] {
                new Fastrunner(-310, 0),
                new Hardhitter(-40, 50),
                new KingOfKids(200, 0)
        }, Exordium.ID, 4));

        strongEncounters.put(makeID("1-1-2"), new MonsterEncounter(() -> new AbstractMonster[] {
                new Hardhitter(-400, 20),
                new Hardhitter(-160, -80),
                new Fastrunner(85, 40),
                new KingOfKids(300, -30)
        }, Exordium.ID, 4));

        weakEncounters.put(makeID("1-2-1"), new MonsterEncounter(() -> new AbstractMonster[] {
                new CollaborativeDissolute(-420, 10),
                new DissolutedHumanoid(-130, -40, DissolutedHumanoid.Skin.B, 1),
                new DissolutedHumanoid(220, 0, DissolutedHumanoid.Skin.C, 0)
        }, new String[] { Exordium.ID, TheCity.ID }, 4));

        weakEncounters.put(makeID("1-2-2"), new MonsterEncounter(() -> new AbstractMonster[] {
                new CollaborativeDissolute(-400, -10),
                new DissolutedRatKing(-170, 10),
                new InterferenceTypeDissolute(110, -20)
        }, new String[] { Exordium.ID, TheCity.ID, TheBeyond.ID }, 4));

        strongEncounters.put(makeID("1-3-1"), new MonsterEncounter(() -> new AbstractMonster[] {
                new DissolutedHumanoid(-420, -40, DissolutedHumanoid.Skin.B),
                new CollaborativeDissolute(-140, 50, 1),
                new CollaborativeDissolute(30, -30),
                new CollaborativeDissolute(200, 20, 1),
        }, new String[] { Exordium.ID, TheCity.ID, TheBeyond.ID }, 3));

        strongEncounters.put(makeID("1-3-2"), new MonsterEncounter(() -> new AbstractMonster[] {
                new DissolutedHumanoid(-400, 0, DissolutedHumanoid.Skin.B),
                new CollaborativeDissolute(-180, -30),
                new DissolutedRatKing(20, 20, 2),
                new DissolutedRatKing(270, -40),
        }, new String[] { Exordium.ID, TheCity.ID, TheBeyond.ID }, 4));

        eliteEncounters.put(TheVoidClaimsAll.ID, new MonsterEncounter(() -> new AbstractMonster[] {
                new TheVoidClaimsAll(-100, -50)
        }, Exordium.ID, 1, 0.8F));

        eliteEncounters.put(DevouringHowl.ID, new MonsterEncounter(() -> new AbstractMonster[] {
                new DevouringHowl(-100, 0)
        }, Exordium.ID, 1));

        eliteEncounters.put(IronPickaxeLucen.ID, new MonsterEncounter(() -> new AbstractMonster[] {
                new IronPickaxeLucen(-100, 0)
        }, Exordium.ID, 1));

        bosses.put(LotanBoss.ID, new MonsterEncounter(() -> new AbstractMonster[] {
                new LotanBoss(0, -8)
        }, TheCity.ID, 0, new String[] {
                makeCharacterPath(removeModID(Lotan.ID) + "/MapIcon.png")
        }));

        bosses.put(LeighBoss.ID, new MonsterEncounter(() -> new AbstractMonster[] {
                new LeighBoss(0, -8)
        }, TheCity.ID, 0, new String[] {
                makeCharacterPath(removeModID(LeighBoss.LeighID) + "/MapIcon.png")
        }));
    }

    public static void register() {
        weakEncounters.forEach((key, value) -> {
            BaseMod.addMonster(key, value.group);
            for (int i = 0; i < value.actIDs.length; i++)
                BaseMod.addMonsterEncounter(value.actIDs[i], new MonsterInfo(key, value.weights[i]));
        });

        strongEncounters.forEach((key, value) -> {
            BaseMod.addMonster(key, value.group);
            for (int i = 0; i < value.actIDs.length; i++)
                BaseMod.addStrongMonsterEncounter(value.actIDs[i], new MonsterInfo(key, value.weights[i]));
        });

        eliteEncounters.forEach((key, value) -> {
            BaseMod.addMonster(key, value.group);
            for (int i = 0; i < value.actIDs.length; i++)
                BaseMod.addEliteEncounter(value.actIDs[i], new MonsterInfo(key, value.weights[i]));
        });

        bosses.forEach((key, value) -> {
            BaseMod.addMonster(key, value.group);
            for (int i = 0; i < value.actIDs.length; i++)
                BaseMod.addBoss(value.actIDs[i], key, makeUIPath("Boss.png"), makeUIPath("BossOutline.png"));
        });
    }
}
