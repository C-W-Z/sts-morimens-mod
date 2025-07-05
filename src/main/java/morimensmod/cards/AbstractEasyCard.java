package morimensmod.cards;

import me.antileaf.signature.card.AbstractSignatureCard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;

import static morimensmod.MorimensMod.makeImagePath;
import static morimensmod.util.Wiz.*;
import static morimensmod.util.General.*;

import morimensmod.cardmodifiers.ChangeCostUntilUseModifier;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.CustomTags;
import morimensmod.util.CardArtRoller;

import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class AbstractEasyCard extends AbstractSignatureCard {

    protected final CardStrings cardStrings;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int thirdMagic;
    public int baseThirdMagic;
    public boolean upgradedThirdMagic;
    public boolean isThirdMagicModified;

    public int attackCount;
    public int baseAttackCount;
    public boolean upgradedAttackCount;
    public boolean isAttackCountModified;

    // public int heal;
    // public int baseHeal;
    public boolean upgradedHeal;
    public boolean isHealModified;

    // public int draw;
    // public int baseDraw;
    public boolean upgradedDraw;
    public boolean isDrawModified;

    public int aliemus;
    public int baseAliemus;
    public boolean upgradedAliemus;
    public boolean isAliemusModified;

    // percent
    public static int baseDamageAmplify;
    public static int baseStrikeDamageAmplify;
    public static int baseBlockAmplify;
    public static int baseHealAmplify;
    public static int baseAliemusAmplify;

    private boolean needsArtRefresh = false;

    protected String upgradedName = "";

    public int prepare = 0;

    // for multiple cards to preview
    protected ArrayList<AbstractCard> previewCards = new ArrayList<>();
    protected int cardPreviewIndex = 0;
    protected float cardPreviewTimer = 0.0F;
    protected static final float CARD_PREVIEW_TIME = 1.0F; // second

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity,
            final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(removeModID(cardID), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.cards != null && !CardLibrary.cards.isEmpty()) {
                CardArtRoller.computeCard(this);
            } else
                needsArtRefresh = true;
        }

        CommonKeywordIconsField.useIcons.set(this, true);
    }

    @Override
    protected Texture getPortraitImage() {
        if (textureImg.contains("ui/missing.png")) {
            return CardArtRoller.getPortraitTexture(this);
        } else {
            return super.getPortraitImage();
        }
    }

    public static String getCardTextureString(final String cardName, final CardType type) {
        String textureString = type != CardType.STATUS
                ? makeImagePath("cards/" + cardName + ".png")
                : makeImagePath("cards/status.png");
        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = makeImagePath("ui/missing.png");
        }
        return textureString;
    }

    @Override
    public void applyPowers() {
        int damageAmplify = 100 + baseDamageAmplify + AbstractAwakener.baseDamageAmplify;
        if (this.hasTag(CardTags.STRIKE))
            damageAmplify += baseStrikeDamageAmplify;
        int blockAmplify = 100 + baseBlockAmplify + AbstractAwakener.baseBlockAmplify;
        int healAmplify = 100 + baseHealAmplify + AbstractAwakener.baseHealAmplify;
        int aliemusAmplify = 100 + baseAliemusAmplify + AbstractAwakener.baseAliemusAmplify;

        applyedBaseAmplifies(damageAmplify, blockAmplify, healAmplify, aliemusAmplify);

        super.applyPowers();

        if (damageAmplify != 100)
            isDamageModified = true;
        if (blockAmplify != 100)
            isBlockModified = true;
        if (healAmplify != 100) {
            isHealModified = true;
            heal = baseHeal;
        }
        if (aliemusAmplify != 100) {
            isAliemusModified = true;
            aliemus = baseAliemus;
        }

        // logger.info("aliemusAmplify: " + aliemusAmplify + ", baseAliemus: " +
        // baseAliemus);
    }

    protected void applySuperPower() {
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int damageAmplify = 100 + baseDamageAmplify + AbstractAwakener.baseDamageAmplify;
        if (this.hasTag(CardTags.STRIKE))
            damageAmplify += baseStrikeDamageAmplify;

        applyedBaseDamageAmplifies(damageAmplify);

        super.calculateCardDamage(mo);

        if (damageAmplify != 100)
            isDamageModified = true;
    }

    protected void calculateSuperCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
    }

    protected void applyedBaseDamageAmplifies(int damageAmplify) {
        if (damageAmplify == 100)
            return;

        AbstractCard tmp = CardLibrary.getCopy(cardID, timesUpgraded, misc);
        baseDamage = MathUtils.ceil(tmp.baseDamage * damageAmplify / 100F);
    }

    protected void applyedBaseAmplifies(int damageAmplify, int blockAmplify, int healAmplify, int aliemusAmplify) {
        AbstractEasyCard tmp = (AbstractEasyCard) CardLibrary.getCopy(cardID, timesUpgraded, misc);
        baseDamage = MathUtils.ceil(tmp.baseDamage * damageAmplify / 100F);
        baseBlock = MathUtils.ceil(tmp.baseBlock * blockAmplify / 100F);
        baseHeal = MathUtils.ceil(tmp.baseHeal * healAmplify / 100F);
        baseAliemus = MathUtils.ceil(tmp.baseAliemus * aliemusAmplify / 100F);
    }

    public void resetAttributes() {
        super.resetAttributes();
        heal = baseHeal;
        isHealModified = false;
        draw = baseDraw;
        isDrawModified = false;
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        thirdMagic = baseThirdMagic;
        isThirdMagicModified = false;
        attackCount = baseAttackCount;
        isAttackCountModified = false;
        aliemus = baseAliemus;
        isAliemusModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedHeal) {
            heal = baseHeal;
            isHealModified = true;
        }
        if (upgradedDraw) {
            draw = baseDraw;
            isDrawModified = true;
        }
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedThirdMagic) {
            thirdMagic = baseThirdMagic;
            isThirdMagicModified = true;
        }
        if (upgradedAttackCount) {
            attackCount = baseAttackCount;
            isAttackCountModified = true;
        }
        if (upgradedAliemus) {
            aliemus = baseAliemus;
            isAliemusModified = true;
        }
    }

    protected void upgradeHeal(int amount) {
        baseHeal += amount;
        heal = baseHeal;
        upgradedHeal = true;
    }

    protected void upgradeDraw(int amount) {
        baseDraw += amount;
        draw = baseDraw;
        upgradedDraw = true;
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeThirdMagic(int amount) {
        baseThirdMagic += amount;
        thirdMagic = baseThirdMagic;
        upgradedThirdMagic = true;
    }

    protected void upgradeAttackCount(int amount) {
        baseAttackCount += amount;
        attackCount = baseAttackCount;
        upgradedAttackCount = true;
    }

    protected void upgradeAliemus(int amount) {
        baseAliemus += amount;
        aliemus = baseAliemus;
        upgradedAliemus = true;
    }

    protected void uDesc() {
        this.rawDescription = this.cardStrings.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        if (upgradedName.isEmpty())
            this.name = this.name + "+";
        else
            this.name = upgradedName;
        this.initializeTitle();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upp();
            if (this.cardStrings.UPGRADE_DESCRIPTION != null) {
                this.uDesc();
            }
        }
    }

    public abstract void upp();

    public void update() {
        super.update();
        if (needsArtRefresh)
            CardArtRoller.computeCard(this);
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard result = super.makeStatEquivalentCopy();
        if (result instanceof AbstractEasyCard) {
            AbstractEasyCard c = (AbstractEasyCard) result;
            c.baseHeal = c.heal = baseHeal;
            c.baseDraw = c.draw = baseDraw;
            c.baseAttackCount = c.attackCount = baseAttackCount;
            c.baseSecondMagic = c.secondMagic = baseSecondMagic;
            c.baseThirdMagic = c.thirdMagic = baseThirdMagic;
            c.baseAliemus = c.aliemus = baseAliemus;
        }
        return result;
    }

    // These shortcuts are specifically for cards. All other shortcuts that aren't
    // specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void allDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    private AbstractGameAction dmgRandomAction(AbstractGameAction.AttackEffect fx,
            Consumer<AbstractMonster> extraEffectToTarget, Consumer<AbstractMonster> effectBefore) {
        return actionify(() -> {
            AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true,
                    AbstractDungeon.cardRandomRng);
            if (target != null) {
                calculateCardDamage(target);
                if (extraEffectToTarget != null)
                    extraEffectToTarget.accept(target);
                att(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
                if (effectBefore != null)
                    effectBefore.accept(target);
            }
        });
    }

    protected void dmgRandom(AbstractGameAction.AttackEffect fx) {
        dmgRandom(fx, null, null);
    }

    protected void dmgRandom(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget,
            Consumer<AbstractMonster> effectBefore) {
        atb(dmgRandomAction(fx, extraEffectToTarget, effectBefore));
    }

    protected void dmgRandomTop(AbstractGameAction.AttackEffect fx) {
        dmgRandomTop(fx, null, null);
    }

    protected void dmgRandomTop(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget,
            Consumer<AbstractMonster> effectBefore) {
        att(dmgRandomAction(fx, extraEffectToTarget, effectBefore));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void blckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public String cardArtCopy() {
        return null;
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return null;
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        super.renderCardPreview(sb);
        updateCardPreview();
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        super.renderCardPreviewInSingleView(sb);
        updateCardPreview();
    }

    protected void updateCardPreview() {
        if (this.previewCards.isEmpty())
            return;
        this.cardPreviewTimer -= Gdx.graphics.getDeltaTime();
        if (this.cardPreviewTimer <= 0.0F) {
            this.cardPreviewTimer = CARD_PREVIEW_TIME; // 每秒切換一次
            this.cardPreviewIndex = (this.cardPreviewIndex + 1) % this.previewCards.size();
            this.cardsToPreview = this.previewCards.get(this.cardPreviewIndex);
        }
    }

    @Override
    public void onRetained() {
        super.onRetained();
        if (prepare <= 0)
            return;
        CardModifierManager.addModifier(this, new ChangeCostUntilUseModifier(-prepare));
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        if (prepare <= 0)
            return;
        CardModifierManager.addModifier(this, new ChangeCostUntilUseModifier(-prepare));
    }

    // called in Main Mod File
    public static void onBattleStart() {
        baseDamageAmplify = 0;
        baseStrikeDamageAmplify = 0;
        baseAliemusAmplify = 0;
    }

    // called in Main Mod File
    public static void onPostBattle() {
        for (AbstractCard card : hand().group)
            if (card.hasTag(CustomTags.RETAIN_IN_DECK) && !isInDeck(card.uuid))
                deck().addToBottom(card.makeSameInstanceOf());
        for (AbstractCard card : drawPile().group)
            if (card.hasTag(CustomTags.RETAIN_IN_DECK) && !isInDeck(card.uuid))
                deck().addToBottom(card.makeSameInstanceOf());
        for (AbstractCard card : discardPile().group)
            if (card.hasTag(CustomTags.RETAIN_IN_DECK) && !isInDeck(card.uuid))
                deck().addToBottom(card.makeSameInstanceOf());
    }
}
