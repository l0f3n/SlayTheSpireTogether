package testMod;

import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.interfaces.OnStartBattleSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.hooks.*;
import testMod.networking.Network;
import testMod.commands.ConnectCommand;
import testMod.commands.HostCommand;
import testMod.networking.messages.EndTurnMessage;
import testMod.networking.messages.NextRoomMessage;
import testMod.networking.messages.SetSeedMessage;
import testMod.networking.messages.builders.ActionBuilderFactory;
import testMod.networking.messages.builders.actions.AbstractActionBuilder;
import testMod.util.IDCheckDontTouchPls;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class TestMod implements ActionSubscriber, SetSeedSubscriber, EndTurnSubscriber, NextRoomSubscriber, PostInitializeSubscriber, OnStartBattleSubscriber, StartGameSubscriber {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Test mod";
    private static final String AUTHOR = "Me"; // And pretty soon - You!
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";

    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "testModResources/images/Badge.png";

    public static final Network network = new Network();

    public TestMod() {

        setModID("testMod");
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("testMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }

    // ===== BASEMOD CODE =====

    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = TestMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = TestMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = TestMod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    // ===== END OF BASEMODE CODE =====

    public static void initialize() {
        logger.info("===== TestMod: Initializing... =====");
        // TODO: Turn off basemod logging, or drop the dependency altogether
        TestMod mod = new TestMod();
        BaseMod.subscribe(mod);
        ActionHook.subscribe(mod);
        SetSeedHook.subscribe(mod);
        EndTurnHook.subscribe(mod);
        NextRoomHook.subscribe(mod);
        StartGameHook.subscribe(mod);
        logger.info("===== TestMod: Initialized successfully. =====");
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Adding console commands...");
        ConsoleCommand.addCommand("host", HostCommand.class);
        ConsoleCommand.addCommand("connect", ConnectCommand.class);
    }

    // TODO: If we drop the basemod dependency, we need to implement this ourselves
    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        // TODO: INCREASE monster health by (numConnections - 1) * monster.maxHealth, which will be the same as
        //  SET monster health to numConnections * monster.maxHealth. If numConnections is 1, is just regular health.
        for (AbstractMonster monster : room.monsters.monsters) {
            monster.increaseMaxHp(monster.maxHealth,true);
        }
    }

    public void receiveAction(AbstractGameAction action) {
        // TODO: Simply add this to a list and let the other threads processes the action, this could potentially stall the game a little
        AbstractActionBuilder<?> actionBuilder = ActionBuilderFactory.createActionBuilder(action);
        if (actionBuilder != null) {
            network.sendMessage(actionBuilder);
        }
    }

    public void receiveEndTurn() {
        // TODO: Maybe introduce broadcastMessage(), so that we don't have to execute our own message, feels weird
        EndTurnMessage endTurnMessage = new EndTurnMessage();
        endTurnMessage.broadcast = true;
        network.sendMessage(endTurnMessage);
    }

    public void receiveSetSeed(long seed) {
        if (network.isHost()) {
            network.sendMessage(new SetSeedMessage(seed));
        } else if (network.isClient()) {
            Settings.seed = network.seed;
        }
    }

    public void receiveNextRoom(int x, int y) {
        if (network.isHost()) {
            network.sendMessage(new NextRoomMessage(x, y));
        }
    }

    public void receiveStartGame() {
        /*
        if (network.isHost()) {
            network.sendMessage(new StartGameMessage());
        }
         */
    }
}
