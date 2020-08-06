package com.zundrel.wrenchable.block;

import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class InstanceListener extends ForgeRegistryEntry<InstanceListener> implements BlockWrenchable {
    private Class block;

    public InstanceListener(Class block) {
        this.block = block;
    }

    public Class getBlock() {
        return block;
    }
}
