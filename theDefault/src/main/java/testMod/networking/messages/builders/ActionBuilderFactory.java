package testMod.networking.messages.builders;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.PummelDamageAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.actions.unique.VampireDamageAllEnemiesAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import testMod.TestMod;
import testMod.networking.messages.builders.actions.*;

public class ActionBuilderFactory {
    public static final Logger logger = LogManager.getLogger(TestMod.class.getName());

    public static AbstractActionBuilder<?> createActionBuilder(AbstractGameAction action) {

        // TODO: Feels like bad code, but how do you implement it otherwise?
        AbstractActionBuilder<?> builder;
        if (action instanceof DamageAction) {
            builder = new DamageActionBuilder((DamageAction) action);
        } else if (action instanceof DamageAllEnemiesAction) {
            builder = new DamageAllEnemiesActionBuilder((DamageAllEnemiesAction) action);
        } else if (action instanceof ApplyPowerAction) {
            builder = new ApplyPowerActionBuilder((ApplyPowerAction) action);
        } else if (action instanceof PummelDamageAction) {
            builder = new PummelDamageActionBuilder((PummelDamageAction) action);
        } else if (action instanceof FeedAction) {
            builder = new FeedActionBuilder((FeedAction) action);
        } else if (action instanceof VampireDamageAllEnemiesAction) {
            builder = new VampireDamageAllEnemiesActionBuilder((VampireDamageAllEnemiesAction) action);
        } else {
            return null;
        }

        if (builder.isBuildable()) {
            return builder;
        } else {
            // TODO: Its not always an error that they cant be built, for example, if the target of damage is the player
            logger.error("Failed to create a builder for " + action.getClass().getSimpleName());
            return null;
        }
    }
}
