package testMod.networking.messages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;

public class SetSeedMessage extends AbstractMessage {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    public long seed;

    public SetSeedMessage(long seed) {
        this.seed = seed;
    }

    public void execute() {
        // TODO: Maybe we should sync seedSourceTimestap as well, but IDK what it does and it didn't seem be accessed
        //  when starting a game so idk.
        TestMod.network.seed = seed;
    }
}
