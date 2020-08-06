package com.zundrel.wrenchable;

import com.zundrel.wrenchable.block.InstanceListener;
import com.zundrel.wrenchable.block.PropertyListener;
import com.zundrel.wrenchable.block.defaults.*;
import com.zundrel.wrenchable.modkeys.ModKeysConfig;
import com.zundrel.wrenchable.modkeys.ModKeysPacketHandler;
import com.zundrel.wrenchable.modkeys.impl.ModKeysHandler;
import com.zundrel.wrenchable.wrench.WrenchListener;
import net.minecraft.block.BedBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("wrenchable")
public class WrenchableMain {
    public static final String MODID = "wrenchable";

    public static final Logger LOGGER = LogManager.getLogger();

    public static InstanceListener BED_LISTENER;
    public static InstanceListener DOOR_LISTENER;
    public static InstanceListener PISTON_LISTENER;
    public static InstanceListener SIGN_LISTENER;

    public static PropertyListener AXIS_LISTENER;
    public static PropertyListener FACING_LISTENER;
    public static PropertyListener HOPPER_FACING_LISTENER;
    public static PropertyListener HORIZONTAL_AXIS_LISTENER;
    public static PropertyListener HORIZONTAL_FACING_LISTENER;
    public static PropertyListener ROTATION_LISTENER;
    public static PropertyListener SLAB_TYPE_LISTENER;

    public WrenchableMain() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ModKeysPacketHandler.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ModKeysConfig.init();
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onInstanceListenerRegistry(final RegistryEvent.Register<InstanceListener> registryEvent) {
            IForgeRegistry<InstanceListener> registry = registryEvent.getRegistry();

            BED_LISTENER = register(registry, "bed", new NoBehaviorInstanceListener(BedBlock.class));
            DOOR_LISTENER = register(registry, "door", new DoorInstanceListener());
            PISTON_LISTENER = register(registry, "piston", new PistonInstanceListener());
            SIGN_LISTENER = register(registry, "sign", new SignInstanceListener());
        }

        @SubscribeEvent
        public static void onPropertyListenerRegistry(final RegistryEvent.Register<PropertyListener> registryEvent) {
            IForgeRegistry<PropertyListener> registry = registryEvent.getRegistry();

            AXIS_LISTENER = register(registry, "axis", new AxisPropertyListener());
            FACING_LISTENER = register(registry, "facing", new FacingPropertyListener());
            HOPPER_FACING_LISTENER = register(registry, "hopper_facing", new HopperFacingPropertyListener());
            HORIZONTAL_AXIS_LISTENER = register(registry, "horizontal_axis", new HorizontalAxisPropertyListener());
            HORIZONTAL_FACING_LISTENER = register(registry, "horizontal_facing", new HorizontalFacingPropertyListener());
            ROTATION_LISTENER = register(registry, "rotation", new RotationPropertyListener());
            SLAB_TYPE_LISTENER = register(registry, "slab_type", new SlabTypePropertyListener());
        }

        @SubscribeEvent
        public static void onWrenchListenerRegistry(final RegistryEvent.Register<WrenchListener> registryEvent) {
            IForgeRegistry<WrenchListener> registry = registryEvent.getRegistry();

            if (!FMLLoader.isProduction()) {
                register(registry, "stick", new WrenchListener(Items.STICK));
            }
        }

        public static <T extends IForgeRegistryEntry<T>> T register(IForgeRegistry<T> registry, String name, T object) {
            T newObject = object.setRegistryName(new ResourceLocation(MODID, name));
            registry.register(newObject);
            return newObject;
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void onClientTick(final TickEvent.ClientTickEvent event) {
            if (Minecraft.getInstance().player != null)
                ModKeysHandler.update(Minecraft.getInstance());
        }
    }
}
