package testMod.hooks;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;

import java.util.ArrayList;

@SpirePatch(clz=AbstractRoom.class, method="endTurn")
public class EndTurnHook {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private static ArrayList<EndTurnSubscriber> subscribers = new ArrayList<>();

    @SpireInsertPatch(locator=EndTurnHook.Locator.class)
    public static SpireReturn<Void> Insert(AbstractRoom __obj_instance) {
        publish();
        AbstractDungeon.player.isEndingTurn = false;
        return SpireReturn.Return();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(GameActionManager.class, "addToBottom");
            int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            return new int[]{matches[matches.length-1]};
        }
    }

    public static void publish() {
        logger.info("Publish receiveEndTurn");
        for (EndTurnSubscriber subscriber : subscribers) {
            subscriber.receiveEndTurn();
        }
    }

    public static void subscribe(EndTurnSubscriber s) {
        subscribers.add(s);
    }

}