package testMod.networking.messages;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;

import java.util.ArrayList;

import static testMod.util.Util.accessField;
import static testMod.util.Util.setField;

public class NextRoomMessage extends AbstractMessage {

    private final int x, y;

    public NextRoomMessage(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        @SuppressWarnings("unchecked")
        ArrayList<MapRoomNode> visibleMapNodes = (ArrayList<MapRoomNode>) accessField(AbstractDungeon.dungeonMapScreen, "visibleMapNodes");
        if (visibleMapNodes == null) return;
        for (MapRoomNode mapRoomNode : visibleMapNodes) {
            if (mapRoomNode.x == x && mapRoomNode.y == y) {
                // TODO: Do this in another way later, we don't get the circle animation (and we possibly miss other
                //  things as well)
                setField(mapRoomNode, "animWaitTimer", 0.25F);
                //((Hitbox) accessField(mapRoomNode, "hb")).hovered = true;
                break;
            }
        }
        AbstractDungeon.dungeonMapScreen.clicked = true;
    }
}
