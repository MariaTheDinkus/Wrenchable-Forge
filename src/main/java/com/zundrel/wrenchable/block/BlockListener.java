package com.zundrel.wrenchable.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class BlockListener extends ForgeRegistryEntry<BlockListener> implements BlockWrenchable {
    private Block block;

    public BlockListener(Block block) {
        this.block = block;
    }

    public BlockListener(ResourceLocation identifier) {
        this(ForgeRegistries.BLOCKS.getValue(identifier));
    }

    public Block getBlock() {
        return block;
    }
}
