package com.zundrel.wrenchable.api;

import com.zundrel.wrenchable.WrenchableMain;
import com.zundrel.wrenchable.block.BlockListener;
import com.zundrel.wrenchable.block.InstanceListener;
import com.zundrel.wrenchable.block.PropertyListener;
import com.zundrel.wrenchable.entity.EntityListener;
import com.zundrel.wrenchable.wrench.Wrench;
import com.zundrel.wrenchable.wrench.WrenchListener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = WrenchableMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WrenchableRegistry {
    public static IForgeRegistry<BlockListener> BLOCK_LISTENERS;
    public static IForgeRegistry<InstanceListener> BLOCK_INSTANCE_LISTENERS;
    public static IForgeRegistry<PropertyListener> PROPERTY_LISTENERS;

    public static IForgeRegistry<EntityListener> ENTITY_LISTENERS;

    public static IForgeRegistry<WrenchListener> WRENCH_LISTENERS;

    private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> register(Class<T> classInstance, String name) {
        return new RegistryBuilder<T>().setName(new ResourceLocation(WrenchableMain.MODID, name))
                .setType(classInstance)
                .setIDRange(0, Short.MAX_VALUE)
                .create();
    }

    @SubscribeEvent
    public static void onCreateRegistry(RegistryEvent.NewRegistry event) {
        BLOCK_LISTENERS = register(BlockListener.class,"block_wrenchable");
        BLOCK_INSTANCE_LISTENERS = register(InstanceListener.class, "block_instance_wrenchable");
        PROPERTY_LISTENERS = register(PropertyListener.class, "property_wrenchable");

        ENTITY_LISTENERS = register(EntityListener.class, "entity_wrenchable");

        WRENCH_LISTENERS = register(WrenchListener.class, "wrench");
    }

    public static BlockListener getBlockWrenchable(Block block) {
        return BLOCK_LISTENERS.getValues().stream().filter(it -> it.getBlock().equals(block)).findAny().orElse(null);
    }

    public static boolean isBlockWrenchable(Block block) {
        return getBlockWrenchable(block) != null;
    }

    public static InstanceListener getBlockInstanceWrenchable(Block block) {
        return BLOCK_INSTANCE_LISTENERS.getValues().stream().filter(it -> it.getBlock().isInstance(block)).findAny().orElse(null);
    }

    public static boolean isBlockInstanceWrenchable(Block block) {
        return getBlockInstanceWrenchable(block) != null;
    }

    public static PropertyListener getPropertyWrenchable(Property property) {
        return PROPERTY_LISTENERS.getValues().stream().filter(it -> it.getProperty().equals(property)).findAny().orElse(null);
    }

    public static boolean isPropertyWrenchable(Property property) {
        return getPropertyWrenchable(property) != null;
    }

    public static EntityListener getEntityTypeWrenchable(Entity entity) {
        return ENTITY_LISTENERS.getValues().stream().filter(it -> it.getType().equals(entity.getType())).findAny().orElse(null);
    }

    public static boolean isEntityTypeWrenchable(Entity entity) {
        return getEntityTypeWrenchable(entity) != null;
    }

    public static Wrench getWrench(Item item) {
        return WRENCH_LISTENERS.getValues().stream().filter(it -> it.getItem().equals(item)).findAny().orElse(null);
    }

    public static boolean isWrench(Item item) {
        return getWrench(item) != null;
    }
}
