package testMod.networking.messages.builders.actions;

import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import testMod.util.Util;
import testMod.networking.messages.builders.others.DamageInfoBuilder;

public class PummelDamageActionBuilder extends AbstractActionBuilder<PummelDamageAction> {
    private final int targetMonsterIndex;
    private final DamageInfoBuilder damageInfoBuilder;

    public PummelDamageActionBuilder(PummelDamageAction action) {
        targetMonsterIndex = Util.findMonsterIndex(action.target);
        damageInfoBuilder = new DamageInfoBuilder((DamageInfo) Util.accessField(action, "info"));
    }

    @Override
    public PummelDamageAction buildAction() {
        return new PummelDamageAction(Util.getMonsterAt(targetMonsterIndex), damageInfoBuilder.build());
    }

    @Override
    public boolean isBuildable() {
        return targetMonsterIndex != -1 && damageInfoBuilder.isBuildable();
    }
}
