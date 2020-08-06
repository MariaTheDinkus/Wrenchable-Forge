package com.zundrel.wrenchable.block;

import net.minecraft.state.Property;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class PropertyListener extends ForgeRegistryEntry<PropertyListener> implements BlockWrenchable {
    private Property property;

    public PropertyListener(Property property) {
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }
}
