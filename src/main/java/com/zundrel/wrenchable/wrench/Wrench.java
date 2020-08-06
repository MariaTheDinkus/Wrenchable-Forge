package com.zundrel.wrenchable.wrench;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Wrench {
    /**
     * This method is called when a block is wrenched. Durability handling should be done in this method.
     * @param world An instance of the world where this block was wrenched.
     * @param stack The wrench ItemStack which the player is currently holding.
     * @param player The player who wrenched this block.
     * @param hand The hand which is holding the wrench.
     * @param pos The position of the block interacted with.
     * @param face The side of the block interacted with.
     * @author Zundrel
     */
    default void onBlockWrenched(World world, ItemStack stack, PlayerEntity player, Hand hand, BlockPos pos, Direction face) {};

    /**
     * This method is called when a block is wrenched. Do not do durability handling in this method.
     * @param world An instance of the world where this block was wrenched.
     * @param stack The wrench ItemStack which the player is currently holding.
     * @param player The player who wrenched this block.
     * @param hand The hand which is holding the wrench.
     * @param tileEntity The TileEntity of the block that was wrenched.
     * @param pos The position of the block interacted with.
     * @param face The side of the block interacted with.
     * @author Zundrel
     */
    default void onBlockEntityWrenched(World world, ItemStack stack, PlayerEntity player, Hand hand, TileEntity tileEntity, BlockPos pos, Direction face) {};

    /**
     * This method is called when an entity is wrenched. Durability handling should be done in this method.
     * @param world An instance of the world where this entity was wrenched.
     * @param stack The wrench ItemStack which the player is currently holding.
     * @param player The player who wrenched this entity.
     * @param hand The hand which is holding the wrench.
     * @param result Information about the entity that was wrenched.
     * @author Zundrel
     */
    default void onEntityWrenched(World world, ItemStack stack, PlayerEntity player, Hand hand, BlockPos result) {};
}
