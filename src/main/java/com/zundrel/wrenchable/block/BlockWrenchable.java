package com.zundrel.wrenchable.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BlockWrenchable {
    /**
     * This method is called when a block is wrenched.
     * @param world An instance of the world where this block was wrenched.
     * @param player The player who wrenched this block.
     * @param pos The position of the block interacted with.
     * @param face The side of the block interacted with.
     * @author Zundrel
     */
    void onWrenched(World world, PlayerEntity player, BlockPos pos, Direction face);
}
