package testMod.networking.messages.builders.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.util.Util;
import testMod.networking.messages.builders.others.DamageInfoBuilder;

public class DamageActionBuilder extends AbstractActionBuilder<DamageAction> {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private final int targetMonsterIndex;
    private final DamageInfoBuilder damageInfoBuilder;
    private final AbstractGameAction.AttackEffect effect;

    protected DamageActionBuilder(int targetMonsterIndex, DamageInfoBuilder damageInfoBuilder, AbstractGameAction.AttackEffect effect) {
        this.targetMonsterIndex = targetMonsterIndex;
        this.damageInfoBuilder = damageInfoBuilder;
        this.effect = effect;
    }

    public DamageActionBuilder(DamageAction damageAction) {
        this(Util.findMonsterIndex(damageAction.target), new DamageInfoBuilder((DamageInfo) Util.accessField(damageAction, "info")), damageAction.attackEffect);
    }

    @Override
    public DamageAction buildAction() {
        return new DamageAction(AbstractDungeon.getMonsters().monsters.get(targetMonsterIndex), damageInfoBuilder.build(), effect);
    }

    @Override
    public boolean isBuildable() {
        return damageInfoBuilder.isBuildable() && targetMonsterIndex != -1;
    }
}
