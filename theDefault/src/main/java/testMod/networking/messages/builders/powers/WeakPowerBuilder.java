package testMod.networking.messages.builders.powers;

import com.megacrit.cardcrawl.powers.WeakPower;
import testMod.util.Util;

public class WeakPowerBuilder implements IPowerBuilder<WeakPower> {
    private final int targetIndex;
    private final int amount;
    private final Boolean isSourceMonster;

    public WeakPowerBuilder(WeakPower power) {
        this.targetIndex = Util.findMonsterIndex(power.owner);
        this.amount = power.amount;
        this.isSourceMonster = (Boolean) Util.accessField(power, "justApplied");
    }

    @Override
    public WeakPower buildPower() {
        return new WeakPower(Util.getMonsterAt(targetIndex), amount, isSourceMonster);
    }

    @Override
    public boolean isBuildable() {
        return targetIndex != -1 && isSourceMonster != null;
    }
}
