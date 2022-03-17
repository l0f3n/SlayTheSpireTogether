package testMod.networking.messages.builders.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DamageAllEnemiesActionBuilder extends AbstractActionBuilder<DamageAllEnemiesAction> {
    private final int[] damage;
    private final DamageInfo.DamageType damageType;
    private final AbstractGameAction.AttackEffect attackEffect;
    private final boolean isFast;

    protected DamageAllEnemiesActionBuilder(int[] damage, DamageInfo.DamageType damageType, AbstractGameAction.AttackEffect attackEffect, boolean isFast) {
        this.damage = damage;
        this.damageType = damageType;
        this.attackEffect = attackEffect;
        this.isFast = isFast;
    }

    public DamageAllEnemiesActionBuilder(DamageAllEnemiesAction action) {
        this(action.damage, action.damageType, action.attackEffect, true);
    }

    public DamageAllEnemiesAction buildAction() {
        // TODO: Correct source creature (maybe fine the way it is)
        return new DamageAllEnemiesAction(AbstractDungeon.player, damage, damageType, attackEffect, isFast);
    }

    @Override
    public boolean isBuildable() {
        return true;
    }
}
