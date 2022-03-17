package testMod.networking.messages;

import java.io.Serializable;

public abstract class AbstractMessage implements Serializable {
    public boolean broadcast = false;

    public abstract void execute();
}
