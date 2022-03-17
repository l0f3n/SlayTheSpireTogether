package testMod.networking.messages;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

public class StartGameMessage extends AbstractMessage {

    @Override
    public void execute() {
        CardCrawlGame.mode = CardCrawlGame.GameMode.CHAR_SELECT;
        CardCrawlGame.splashScreen = null;
        CardCrawlGame.mainMenuScreen = new MainMenuScreen();
        CardCrawlGame.mainMenuScreen.fadedOut = true;
    }
}
