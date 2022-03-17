package testMod.networking.messages.builders.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.UnsynchronizedAction;
import testMod.networking.messages.AbstractMessage;

public abstract class AbstractActionBuilder<T extends AbstractGameAction> extends AbstractMessage {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    abstract T buildAction();
    public abstract boolean isBuildable();

    @Override
    public void execute() {
        AbstractGameAction action = buildAction();
        AbstractDungeon.actionManager.addToBottom(new UnsynchronizedAction(action));
    }
}
