package xyz.phanta.tconevo.handler;

import net.minecraft.item.ItemStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import xyz.phanta.tconevo.trait.base.MatchSensitiveModifier;

public class ItemSensitiveModificationHandler {

    public static boolean canApply(IModifier modifier, ItemStack stack, ItemStack original, RecipeMatch.Match match)
            throws TinkerGuiException {
        return modifier.canApply(stack, original) && (!(modifier instanceof MatchSensitiveModifier)
                || ((MatchSensitiveModifier)modifier).canApplyCustomWithMatch(stack, match));
    }

    public static void apply(IModifier modifier, ItemStack stack, RecipeMatch.Match match) {
        modifier.apply(stack);
        if (modifier instanceof MatchSensitiveModifier) {
            ((MatchSensitiveModifier)modifier).applyEffectWithMatch(stack, match);
        }
    }

}
