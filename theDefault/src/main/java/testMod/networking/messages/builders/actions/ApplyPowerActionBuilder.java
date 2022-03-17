package testMod.networking.messages.builders.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.messages.builders.PowerBuilderFactory;
import testMod.networking.messages.builders.powers.IPowerBuilder;
import testMod.util.Util;

public class ApplyPowerActionBuilder extends AbstractActionBuilder<ApplyPowerAction> {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private final int targetIndex;
    private final IPowerBuilder<?> powerBuilder;
    private final int stackAmount;
    private final boolean isFast;
    private final AbstractGameAction.AttackEffect attackEffect;

    public ApplyPowerActionBuilder(ApplyPowerAction action) {
        targetIndex = Util.findMonsterIndex(action.target);
        stackAmount = action.amount;
        isFast = true; // TODO: Get correct value
        attackEffect = action.attackEffect;

        AbstractPower power = (AbstractPower) Util.accessField(action, "powerToApply");
        powerBuilder = PowerBuilderFactory.createPowerBuilder(power);
    }

    @Override
    public ApplyPowerAction buildAction() {
        return new ApplyPowerAction(Util.getMonsterAt(targetIndex), AbstractDungeon.player, powerBuilder.buildPower(), stackAmount, isFast, attackEffect);
    }

    @Override
    public boolean isBuildable() {
        return targetIndex != -1 && powerBuilder != null;
    }

}
