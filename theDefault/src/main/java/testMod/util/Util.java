package testMod.util;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;

import java.lang.reflect.Field;

public class Util {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    private Util() {}

    public static int findMonsterIndex(AbstractCreature target) {
        int index = 0;
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster == target) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static AbstractCreature getMonsterAt(int index) {
        return AbstractDungeon.getMonsters().monsters.get(index);
    }

    public static Object accessField(Object o, String identifier) {
        try {
            Field f = o.getClass().getDeclaredField(identifier);
            f.setAccessible(true);
            return f.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Failed to access " + identifier + " on " + o.getClass().getSimpleName());
            return null;
        }
    }

    public static void setField(Object o, String identifier, Object value) {
        // TODO: Extend this later if we need support for more primitive types
        try {
            Field f = o.getClass().getDeclaredField(identifier);
            f.setAccessible(true);
            if (f.getType().equals(float.class)) {
                f.setFloat(o, (float) value);
            } else {
                f.set(o, value);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error("Failed to set " + identifier + " on " + o.getClass().getSimpleName());
        }
    }
}
