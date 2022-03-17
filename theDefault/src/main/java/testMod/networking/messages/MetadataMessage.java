package testMod.networking.messages;

import testMod.TestMod;

public class MetadataMessage extends AbstractMessage {

    private final int numCurrentConnections;

    public MetadataMessage() {
        this.numCurrentConnections = TestMod.network.numCurrentConnections;
    }

    @Override
    public void execute() {
        TestMod.network.numCurrentConnections = numCurrentConnections;
    }
}
