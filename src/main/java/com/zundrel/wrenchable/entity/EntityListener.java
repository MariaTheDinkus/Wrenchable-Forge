package com.zundrel.wrenchable.entity;

import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class EntityListener extends ForgeRegistryEntry<EntityListener> implements EntityWrenchable {
    private EntityType type;

    public EntityListener(EntityType type) {
        this.type = type;
    }

    public EntityType getType() {
        return type;
    }
}
