package testMod.networking.messages.builders.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;

import java.io.Serializable;

public interface IPowerBuilder<T extends AbstractPower> extends Serializable {
    T buildPower();
    boolean isBuildable();
}
