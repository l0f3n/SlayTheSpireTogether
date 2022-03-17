package testMod.networking.messages.builders.actions;

import com.megacrit.cardcrawl.actions.unique.VampireDamageAllEnemiesAction;

public class VampireDamageAllEnemiesActionBuilder extends DamageAllEnemiesActionBuilder {

    public VampireDamageAllEnemiesActionBuilder(VampireDamageAllEnemiesAction action) {
        super(action.damage, action.damageType, action.attackEffect, true);
    }
}