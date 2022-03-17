package testMod.networking.messages.builders.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import testMod.util.Util;
import testMod.networking.messages.builders.others.DamageInfoBuilder;

public class FeedActionBuilder extends DamageActionBuilder {
    public FeedActionBuilder(FeedAction action) {
        super(Util.findMonsterIndex(action.target), new DamageInfoBuilder((DamageInfo) Util.accessField(action, "info")), AbstractGameAction.AttackEffect.NONE);
    }
}
