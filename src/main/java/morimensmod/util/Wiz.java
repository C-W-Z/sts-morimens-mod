package morimensmod.util;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField.ExhaustiveFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.cards.posses.AbstractPosse;
import morimensmod.patches.enums.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.POSSE_COLOR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

public class Wiz {

    // private static final Logger logger = LogManager.getLogger(Wiz.class);

    // The wonderful Wizard of Oz allows access to most easy compilations of data,
    // or functions.

    public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
        cardsList.forEach(c -> consumer.accept(c));
    }

    public static ArrayList<AbstractCard> getAllCardsInCardGroups(boolean includeHand, boolean includeExhaust) {
        ArrayList<AbstractCard> masterCardsList = new ArrayList<>();
        masterCardsList.addAll(AbstractDungeon.player.drawPile.group);
        masterCardsList.addAll(AbstractDungeon.player.discardPile.group);
        if (includeHand)
            masterCardsList.addAll(AbstractDungeon.player.hand.group);
        if (includeExhaust)
            masterCardsList.addAll(AbstractDungeon.player.exhaustPile.group);
        return masterCardsList;
    }

    public static void forAllMonstersLiving(Consumer<AbstractMonster> consumer) {
        getEnemies().forEach(mo -> consumer.accept(mo));
    }

    public static void forAllMonstersLivingTop(Consumer<AbstractMonster> consumer) {
        ArrayList<AbstractMonster> enemies = getEnemies();
        Collections.reverse(enemies);
        enemies.forEach(mo -> consumer.accept(mo));
    }

    public static ArrayList<AbstractMonster> getEnemies() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        return monsters;
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred) {
        return getCardsMatchingPredicate(pred, false);
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred, boolean allcards) {
        if (allcards)
            return (ArrayList<AbstractCard>) CardLibrary.getAllCards().stream().filter(pred)
                    .collect(Collectors.toList());
        else {
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            cardsList.addAll(AbstractDungeon.srcCommonCardPool.group);
            cardsList.addAll(AbstractDungeon.srcUncommonCardPool.group);
            cardsList.addAll(AbstractDungeon.srcRareCardPool.group);
            cardsList.removeIf(c -> !pred.test(c));
            return cardsList;
        }
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred, boolean allCards) {
        return getRandomItem(getCardsMatchingPredicate(pred, allCards));
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred) {
        return returnTrulyRandomPrediCardInCombat(pred, false);
    }

    public static <T> T getRandomItem(ArrayList<T> list, Random rng) {
        return list.isEmpty() ? null : list.get(rng.random(list.size() - 1));
    }

    public static <T> T getRandomItem(ArrayList<T> list) {
        return getRandomItem(list, AbstractDungeon.cardRandomRng);
    }

    public static boolean actuallyHovered(Hitbox hb) {
        return InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y
                && InputHelper.mY < hb.y + hb.height;
    }

    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public static boolean isInBossCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
    }

    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public static void vfx(AbstractGameEffect gameEffect) {
        atb(new VFXAction(gameEffect));
    }

    public static void vfx(AbstractGameEffect gameEffect, float duration) {
        atb(new VFXAction(gameEffect, duration));
    }

    public static void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    public static void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    public static void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    public static void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public static void shuffleInTop(AbstractCard c, int i) {
        att(new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    public static void shuffleInTop(AbstractCard c) {
        shuffleInTop(c, 1);
    }

    public static void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public static void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    public static void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelfTop(AbstractPower po) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void thornDmg(AbstractCreature m, int amount, AbstractGameAction.AttackEffect AtkFX) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AtkFX));
    }

    public static void thornDmg(AbstractCreature m, int amount) {
        thornDmg(m, amount, AbstractGameAction.AttackEffect.NONE);
    }

    public static void discard(int amount, boolean isRandom) {
        atb(new DiscardAction(p(), p(), amount, isRandom));
    }

    public static void discard(int amount) {
        discard(amount, false);
    }

    public static AbstractGameAction actionify(Runnable todo) {
        return new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                todo.run();
            }
        };
    }

    public static void actB(Runnable todo) {
        atb(actionify(todo));
    }

    public static void actT(Runnable todo) {
        att(actionify(todo));
    }

    public static AbstractGameAction multiAction(AbstractGameAction... actions) {
        return actionify(() -> {
            ArrayList<AbstractGameAction> actionsList = (ArrayList<AbstractGameAction>) Arrays.asList(actions);
            Collections.reverse(actionsList);
            for (AbstractGameAction action : actions)
                att(action);
        });
    }

    public static void playAudio(ProAudio a) {
        CardCrawlGame.sound.play(makeID(a.name()));
    }

    public static AbstractMonster getRandomEnemy() {
        return AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    }

    public static int getLogicalCardCost(AbstractCard c) {
        if (!c.freeToPlay()) {
            if (c.cost <= -1) {
                return 0;
            }
            return c.costForTurn;
        }
        return 0;
    }

    public static boolean isAttacking(AbstractCreature m) {
        if (m instanceof AbstractMonster) {
            return ((AbstractMonster) m).getIntentBaseDmg() >= 0;
        }
        return false;
    }

    public static void removePower(AbstractPower p, boolean top) {
        if (top) {
            att(new RemoveSpecificPowerAction(p.owner, p.owner, p));
        } else {
            atb(new RemoveSpecificPowerAction(p.owner, p.owner, p));
        }
    }

    public static void removePower(AbstractPower p) {
        removePower(p, false);
    }

    public static void reducePower(AbstractPower p, int amount) {
        atb(new ReducePowerAction(p.owner, p.owner, p, amount));
    }

    public static void reducePower(AbstractPower p) {
        reducePower(p, 1);
    }

    public static int getPowerAmount(AbstractCreature ac, String powerId) {
        AbstractPower pow = ac.getPower(powerId);
        if (pow == null)
            return 0;
        return pow.amount;
    }

    public static AbstractPlayer p() {
        return AbstractDungeon.player;
    }

    public static CardGroup hand() {
        return AbstractDungeon.player.hand;
    }

    public static CardGroup drawPile() {
        return AbstractDungeon.player.drawPile;
    }

    public static CardGroup discardPile() {
        return AbstractDungeon.player.discardPile;
    }

    public static CardGroup exhaustPile() {
        return AbstractDungeon.player.exhaustPile;
    }

    public static CardGroup limbo() {
        return AbstractDungeon.player.limbo;
    }

    public static CardGroup deck() {
        return AbstractDungeon.player.masterDeck;
    }

    public static boolean isInDeck(UUID uuid) {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
            if (card.uuid.equals(uuid))
                return true;
        return false;
    }

    public static boolean isKilled(AbstractCreature target) {
        return (target.isDying || target.currentHealth <= 0) && !target.halfDead
                && !target.hasPower(MinionPower.POWER_ID);
    }

    public static boolean isCommandCard(AbstractCard card) {
        return card.hasTag(CustomTags.COMMAND);
    }

    public static boolean isNonExhaustCommandCard(AbstractCard card) {
        return !card.exhaust && ExhaustiveFields.exhaustive.get(card) < 0 && isCommandCard(card);
    }

    public static boolean isStrikeOrAsStrike(AbstractCard card) {
        return card.hasTag(CardTags.STRIKE);
    }

    public static boolean isDefendOrAsDefend(AbstractCard card) {
        return card.hasTag(CustomTags.DEFEND);
    }

    public static AbstractCard getCleanCopy(AbstractCard card) {
        AbstractCard c = CardLibrary.getCopy(card.cardID);
        for (int i = 0; i < card.timesUpgraded; i++)
            c.upgrade();
        return c;
    }

    public static ArrayList<AbstractPosse> getAllPosses() {
        ArrayList<AbstractPosse> pool = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards())
            if (c.color == POSSE_COLOR)
                pool.add((AbstractPosse) c.makeCopy());
        return pool;
    }

    public static ArrayList<AbstractPosse> getAllPossesExcept(ArrayList<String> posseIDs) {
        ArrayList<AbstractPosse> pool = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards())
            if (c.color == POSSE_COLOR && !posseIDs.contains(c.cardID))
                pool.add((AbstractPosse) c.makeCopy());
        return pool;
    }

    // Wiz.* must used after receiveEditStrings() !
    private static final UIStrings MODIFIER_STRINGS = CardCrawlGame.languagePack.getUIString(makeID("CardModifiers"));

    public static boolean hasModifier(String rawDescription) {
        for (String text : MODIFIER_STRINGS.TEXT)
            if (rawDescription.contains(text))
                return true;
        return false;
    }
}
