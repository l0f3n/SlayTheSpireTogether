package testMod.networking.messages.builders.others;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.io.Serializable;

public class DamageInfoBuilder implements Serializable {
    private int base;
    private DamageInfo.DamageType type;

    private boolean buildable = false;

    public DamageInfoBuilder(DamageInfo damageInfo) {
        if (damageInfo != null) {
            base = damageInfo.base;
            type = damageInfo.type;
            buildable = true;
        }
    }

    public DamageInfo build() {
        // TODO: Correct source creature (maybe fine the way it is)
        return new DamageInfo(AbstractDungeon.player, base, type);
    }

    public boolean isBuildable() {
        return buildable;
    }
}
