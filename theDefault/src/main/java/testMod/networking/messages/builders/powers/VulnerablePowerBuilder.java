package testMod.networking.messages.builders.powers;

import com.megacrit.cardcrawl.powers.VulnerablePower;
import testMod.util.Util;

public class VulnerablePowerBuilder implements IPowerBuilder<VulnerablePower> {
    private final int targetIndex;
    private final int amount;
    private final Boolean isSourceMonster;

    public VulnerablePowerBuilder(VulnerablePower power) {
        this.targetIndex = Util.findMonsterIndex(power.owner);
        this.amount = power.amount;
        this.isSourceMonster = (Boolean) Util.accessField(power, "justApplied");
    }

    @Override
    public VulnerablePower buildPower() {
        return new VulnerablePower(Util.getMonsterAt(targetIndex), amount, isSourceMonster);
    }

    @Override
    public boolean isBuildable() {
        return targetIndex != -1 && isSourceMonster != null;
    }
}
