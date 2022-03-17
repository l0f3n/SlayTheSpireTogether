package testMod.networking.messages.builders.powers;

import com.megacrit.cardcrawl.powers.StrengthPower;
import testMod.util.Util;

public class StrengthPowerBuilder implements IPowerBuilder<StrengthPower> {
    private final int targetIndex;
    private final int amount;

    public StrengthPowerBuilder(StrengthPower power) {
        this.targetIndex = Util.findMonsterIndex(power.owner);
        this.amount = power.amount;
    }

    @Override
    public StrengthPower buildPower() {
        return new StrengthPower(Util.getMonsterAt(targetIndex), amount);
    }

    @Override
    public boolean isBuildable() {
        return targetIndex != -1;
    }
}
