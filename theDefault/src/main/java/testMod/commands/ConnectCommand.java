package testMod.commands;

import basemod.devcommands.ConsoleCommand;
import basemod.helpers.ConvertHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.Network;

public class ConnectCommand extends ConsoleCommand {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    public ConnectCommand() {
        maxExtraTokens = 2;
        minExtraTokens = 0;
        requiresPlayer = false;
        simpleCheck = true;
    }

    @Override
    public void execute(String[] tokens, int depth) {
        logger.info("Executing command: connect");
        if (depth == 3) {
            TestMod.network.startClient(tokens[1], ConvertHelper.tryParseInt(tokens[2], Network.DEFAULT_PORT));
        } else if (depth == 2) {
            TestMod.network.startClient(tokens[1], Network.DEFAULT_PORT);
        } else {
            TestMod.network.startClient(Network.DEFAULT_HOST, Network.DEFAULT_PORT);
        }
    }
}
