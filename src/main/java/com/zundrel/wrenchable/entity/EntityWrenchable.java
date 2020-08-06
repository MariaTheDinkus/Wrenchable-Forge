package com.zundrel.wrenchable.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntityWrenchable {
    /**
     * This method is called when an entity is wrenched.
     * @param world An instance of the world where this entity was wrenched.
     * @param player The player who wrenched this entity.
     * @param pos The block position of the entity interacted with.
     * @author Zundrel
     */
    void onWrenched(World world, PlayerEntity player, BlockPos pos);
}
