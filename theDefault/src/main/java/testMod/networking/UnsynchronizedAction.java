package testMod.networking;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class UnsynchronizedAction extends AbstractGameAction {

    private AbstractGameAction action;

    public UnsynchronizedAction(AbstractGameAction action) {
        this.action = action;
    }

    @Override
    public void update() {
        action.update();
        this.isDone = action.isDone;
    }
}
