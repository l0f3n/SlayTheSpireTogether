package testMod.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import com.badlogic.gdx.Net;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.Network;

public class HostCommand extends ConsoleCommand {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    public HostCommand() {
        maxExtraTokens = 1;
        minExtraTokens = 0;
        requiresPlayer = false;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        logger.info("Executing command: host");
        if (depth == 2) {
            TestMod.network.startServer(ConvertHelper.tryParseInt(tokens[1], Network.DEFAULT_PORT));
        } else {
            TestMod.network.startServer(Network.DEFAULT_PORT);
        }
    }
}
