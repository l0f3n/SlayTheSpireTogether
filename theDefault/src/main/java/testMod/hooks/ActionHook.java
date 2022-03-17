package testMod.hooks;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;

import java.util.ArrayList;

// TODO: Look into spirepatch2, seemed to be a little cleaner
// TODO: Hooks/subscriber/publisher models seems rather overkill when we only ever use them once. Its good if someone
//  else wants to use them, but no one probably will. Clean this up later.
@SpirePatch(clz = GameActionManager.class, method = "getNextAction")
public class ActionHook {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private static final ArrayList<ActionSubscriber> subscribers = new ArrayList<>();

    @SpireInsertPatch(locator = ActionHook.Locator.class)
    public static void Insert(Object __obj_instance) {
        GameActionManager gameActionManager = (GameActionManager)__obj_instance;
        AbstractGameAction action = gameActionManager.actions.get(0);
        publish(action);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "currentAction");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }

    public static void publish(AbstractGameAction action) {
        logger.info("Publish receiveAction: " + action.getClass().getSimpleName());
        for (ActionSubscriber subscriber : subscribers) {
            subscriber.receiveAction(action);
        }
    }

    public static void subscribe(ActionSubscriber s) {
        subscribers.add(s);
    }
}
