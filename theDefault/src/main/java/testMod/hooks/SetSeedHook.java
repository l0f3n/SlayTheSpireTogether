package testMod.hooks;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;

import java.util.ArrayList;

@SpirePatch(clz=CharacterSelectScreen.class, method="setRandomSeed")
public class SetSeedHook {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private static ArrayList<SetSeedSubscriber> subscribers = new ArrayList<>();

    public static void Postfix(CharacterSelectScreen __obj_instance) {
        publish(Settings.seed);
    }

    public static void publish(long seed) {
        logger.info("Publish receiveSetSeed: " + seed);
        for (SetSeedSubscriber subscriber : subscribers) {
            subscriber.receiveSetSeed(seed);
        }
    }

    public static void subscribe(SetSeedSubscriber s) {
        subscribers.add(s);
    }
}