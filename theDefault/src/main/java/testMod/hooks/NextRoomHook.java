package testMod.hooks;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.map.MapRoomNode;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;

import java.util.ArrayList;

@SpirePatch(clz= MapRoomNode.class, method="update")
public class NextRoomHook {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private static final ArrayList<NextRoomSubscriber> subscribers = new ArrayList<>();

    @SpireInsertPatch(locator= NextRoomHook.Locator.class)
    public static void Insert(MapRoomNode __obj_instance) {
        publish(__obj_instance.x, __obj_instance.y);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(MapRoomNode.class, "animWaitTimer");
            int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            return new int[]{matches[matches.length-2], matches[matches.length-1]};
        }
    }

    public static void publish(int x, int y) {
        logger.info("Publish receiveNextRoom: (" + x + ", " + y  + ")");
        for (NextRoomSubscriber subscriber : subscribers) {
            subscriber.receiveNextRoom(x, y);
        }
    }

    public static void subscribe(NextRoomSubscriber s) {
        subscribers.add(s);
    }
}
