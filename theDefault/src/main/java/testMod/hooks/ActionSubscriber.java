package testMod.hooks;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public interface ActionSubscriber {
    void receiveAction(AbstractGameAction action);
    void receiveEndTurn();
}
