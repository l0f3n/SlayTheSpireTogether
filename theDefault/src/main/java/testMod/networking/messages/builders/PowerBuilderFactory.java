package testMod.networking.messages.builders;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.messages.builders.powers.IPowerBuilder;
import testMod.networking.messages.builders.powers.StrengthPowerBuilder;
import testMod.networking.messages.builders.powers.VulnerablePowerBuilder;
import testMod.networking.messages.builders.powers.WeakPowerBuilder;

public class PowerBuilderFactory {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    public static IPowerBuilder<?> createPowerBuilder(AbstractPower power) {

        IPowerBuilder<?> builder;
        if (power instanceof VulnerablePower) {
            builder = new VulnerablePowerBuilder((VulnerablePower) power);
        } else if (power instanceof WeakPower) {
            builder = new WeakPowerBuilder((WeakPower) power);
        } else if (power instanceof StrengthPower) {
            builder = new StrengthPowerBuilder((StrengthPower) power);
        } else {
            return null;
        }

        if (builder.isBuildable()) {
            return builder;
        } else {
            logger.error("Failed to create a builder for " + power.getClass().getSimpleName());
            return null;
        }
    }
}
