package xyz.phanta.tconevo.integration.thermal;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import java.util.Optional;

public interface ThermalHooks extends IntegrationHooks {

    // decided to use thermal foundation as the required mod since it's the one that provides most of the materials
    // also, the other thermal series mods depend on it so if any of them are installed, so is thermal foundation
    String MOD_ID = "thermalfoundation";

    @Inject(MOD_ID)
    ThermalHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemFluxCapacitor(int tier);

    void addCrucibleRecipe(int energy, ItemStack input, FluidStack output);

    class Noop implements ThermalHooks {

        @Override
        public Optional<ItemStack> getItemFluxCapacitor(int tier) {
            return Optional.empty();
        }

        @Override
        public void addCrucibleRecipe(int energy, ItemStack input, FluidStack output) {
            // NO-OP
        }

    }

}
