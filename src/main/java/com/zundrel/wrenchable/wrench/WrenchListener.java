package com.zundrel.wrenchable.wrench;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class WrenchListener extends ForgeRegistryEntry<WrenchListener> implements Wrench {
    private Item item;

    public WrenchListener(Item item) {
        this.item = item;
    }

    public WrenchListener(ResourceLocation identifier) {
        this(ForgeRegistries.ITEMS.getValue(identifier));
    }

    public Item getItem() {
        return item;
    }
}
