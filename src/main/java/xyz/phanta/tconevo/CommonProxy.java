package xyz.phanta.tconevo;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import xyz.phanta.tconevo.handler.*;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.IntegrationManager;
import xyz.phanta.tconevo.material.MaterialDefinition;
import xyz.phanta.tconevo.network.CPacketGaiaWrath;
import xyz.phanta.tconevo.network.SPacketEntitySpecialEffect;
import xyz.phanta.tconevo.network.SPacketLightningEffect;
import xyz.phanta.tconevo.recipe.MasterRecipes;
import xyz.phanta.tconevo.recipe.OreDictRegistration;

import java.util.List;

public class CommonProxy {

    private final ToolCapabilityHandler toolCapHandler = new ToolCapabilityHandler();
    private final PlayerStateHandler playerStateHandler = new PlayerStateHandler();
    private final EnergyShieldHandler energyShieldHandler = new EnergyShieldHandler();

    public void onPreInit(FMLPreInitializationEvent event) {
        IntegrationManager.injectHooks(event.getAsmData());
        MinecraftForge.EVENT_BUS.register(toolCapHandler);
        MinecraftForge.EVENT_BUS.register(playerStateHandler);
        MinecraftForge.EVENT_BUS.register(energyShieldHandler);
        MinecraftForge.EVENT_BUS.register(new EnergizedTraitConflictHandler());
        MinecraftForge.EVENT_BUS.register(new FlightSpeedHandler());
        MinecraftForge.EVENT_BUS.register(new EntityAttributeHandler());
        SimpleNetworkWrapper netHandler = TconEvoMod.INSTANCE.getNetworkHandler();
        netHandler.registerMessage(new SPacketEntitySpecialEffect.Handler(), SPacketEntitySpecialEffect.class, 0, Side.CLIENT);
        netHandler.registerMessage(new CPacketGaiaWrath.Handler(), CPacketGaiaWrath.class, 1, Side.SERVER);
        netHandler.registerMessage(new SPacketLightningEffect.Handler(), SPacketLightningEffect.class, 2, Side.CLIENT);
        IntegrationManager.dispatchPreInit(event);
    }

    public void onInit(FMLInitializationEvent event) {
        OreDictRegistration.registerOreDict();
        MasterRecipes.initRecipes();
        MaterialDefinition.initMaterialProperties();
        TconEvoTraits.initModifierMaterials();
        IntegrationManager.dispatchInit(event);
    }

    public void onImcReceived(FMLInterModComms.IMCEvent event) {
        // we need this to happen before tcon's post-init finishes and the imc handling event just happens to be convenient
        MaterialDefinition.activate();
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        IntegrationManager.dispatchPostInit(event);
    }

    public ToolCapabilityHandler getToolCapHandler() {
        return toolCapHandler;
    }

    public PlayerStateHandler getPlayerStateHandler() {
        return playerStateHandler;
    }

    public EnergyShieldHandler getEnergyShieldHandler() {
        return energyShieldHandler;
    }

    public void playEntityEffect(Entity entity, SPacketEntitySpecialEffect.EffectType type) {
        TconEvoMod.INSTANCE.getNetworkHandler().sendToAllAround(
                new SPacketEntitySpecialEffect(entity.getEntityId(), type),
                new NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 32D));
    }

    public void playLightningEffect(Entity ref, List<Vec3d> positions) {
        TconEvoMod.INSTANCE.getNetworkHandler().sendToAllAround(
                new SPacketLightningEffect(positions),
                new NetworkRegistry.TargetPoint(ref.dimension, ref.posX, ref.posY, ref.posZ, 64D));
    }

}
