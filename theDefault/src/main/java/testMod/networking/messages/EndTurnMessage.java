package testMod.networking.messages;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.MonsterStartTurnAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import testMod.TestMod;

public class EndTurnMessage extends AbstractMessage {

    @Override
    public void execute() {
        if (++TestMod.network.numPlayerHasEndedTurn >= TestMod.network.numCurrentConnections) {
            TestMod.network.numPlayerHasEndedTurn = 0;

            // NOTE: This does the same thing as the rest of the end turn function, just after we stopped it before sync
            AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                public void update() {
                    this.addToBot(new EndTurnAction());
                    this.addToBot(new WaitAction(1.2F));
                    if (!AbstractDungeon.currMapNode.room.skipMonsterTurn) {
                        this.addToBot(new MonsterStartTurnAction());
                    }

                    AbstractDungeon.actionManager.monsterAttacksQueued = false;
                    this.isDone = true;
                }
            });
        }
    }
}
