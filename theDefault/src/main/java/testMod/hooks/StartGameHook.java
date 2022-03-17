package testMod.hooks;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;

import java.util.ArrayList;

@SpirePatch(clz= CardCrawlGame.class, method="createCharacter")
public class StartGameHook {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private static ArrayList<StartGameSubscriber> subscribers = new ArrayList<>();

    public static void Postfix(Object __obj_instance) {
        publish();
    }

    public static void publish() {
        logger.info("Publish receiveStartGame");
        for (StartGameSubscriber subscriber : subscribers) {
            subscriber.receiveStartGame();
        }
    }

    public static void subscribe(StartGameSubscriber s) {
        subscribers.add(s);
    }
}
