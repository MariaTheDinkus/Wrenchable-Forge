package com.zundrel.wrenchable;

import com.zundrel.wrenchable.api.WrenchableRegistry;
import com.zundrel.wrenchable.modkeys.api.ModKeys;
import com.zundrel.wrenchable.wrench.Wrench;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class WrenchableUtilities {
    /**
     * This method causes the default Properties.FACING behavior to run.
     * @param world An instance of the world where this block was wrenched.
     * @param player The player who wrenched this block.
     * @param pos The position of the block interacted with.
     * @param face The side of the block interacted with.
     * @author Zundrel
     */
    public static void doFacingBehavior(World world, PlayerEntity player, BlockPos pos, Direction face) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Direction direction = state.get(BlockStateProperties.FACING);

        if (ModKeys.isSecondaryPressed(player)) {
            world.setBlockState(pos, state.with(BlockStateProperties.FACING, face));
            world.neighborChanged(pos, block, pos);
            return;
        }

        if (direction == Direction.UP || direction == Direction.DOWN) {
            world.setBlockState(pos, state.with(BlockStateProperties.FACING, direction.getOpposite()));
            world.neighborChanged(pos, block, pos);

            return;
        }

        rotateState(world, player, state, pos);
    }

    /**
     * This method causes the default Properties.HORIZONTAL_FACING behavior to run.
     * @param world An instance of the world where this block was wrenched.
     * @param player The player who wrenched this block.
     * @param pos The position of the block interacted with.
     * @param face The side of the block interacted with.
     * @author Zundrel
     */
    public static void doHorizontalFacingBehavior(World world, PlayerEntity player, BlockPos pos, Direction face) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (ModKeys.isSecondaryPressed(player)) {
            if (!state.with(BlockStateProperties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite()).isValidPosition(world, pos))
                return;

            world.setBlockState(pos, state.with(BlockStateProperties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite()));
            world.neighborChanged(pos, block, pos);
            return;
        }

        rotateState(world, player, state, pos);
    }

    /**
     * This method causes the default Properties.HOPPER_FACING behavior to run.
     * @param world An instance of the world where this block was wrenched.
     * @param player The player who wrenched this block.
     * @param pos The position of the block interacted with.
     * @param face The side of the block interacted with.
     * @author Zundrel
     */
    public static void doHopperFacingBehavior(World world, PlayerEntity player, BlockPos pos, Direction face) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Direction direction = state.get(BlockStateProperties.FACING_EXCEPT_UP);

        if (ModKeys.isSecondaryPressed(player)) {
            if (face != Direction.UP) {
                world.setBlockState(pos, state.with(BlockStateProperties.FACING_EXCEPT_UP, face));
                world.neighborChanged(pos, block, pos);
                return;
            } else {
                world.setBlockState(pos, state.with(BlockStateProperties.FACING_EXCEPT_UP, Direction.DOWN));
                world.neighborChanged(pos, block, pos);
                return;
            }
        }

        if (face == Direction.UP) {
            world.setBlockState(pos, state.with(BlockStateProperties.FACING_EXCEPT_UP, Direction.DOWN));
            world.neighborChanged(pos, block, pos);
            return;
        } else if (state.get(BlockStateProperties.FACING_EXCEPT_UP) == Direction.DOWN) {
            world.setBlockState(pos, state.with(BlockStateProperties.FACING_EXCEPT_UP, Direction.NORTH));
            world.neighborChanged(pos, block, pos);
            return;
        }

        rotateState(world, player, state, pos);
    }

    /**
     * This method causes basic rotation behavior for a BlockState to occur.
     * I don't recommend using this, instead use doFacingBehavior or doHorizontalFacingBehavior.
     * @param world An instance of the world where this block was wrenched.
     * @param player The player who wrenched this block.
     * @param state The BlockState that was wrenched.
     * @param pos The BlockPos that was wrenched.
     * @author Zundrel
     */
    public static void rotateState(World world, PlayerEntity player, BlockState state, BlockPos pos) {
        Block block = state.getBlock();

        if (player.isSneaking()) {
            if (!state.rotate(Rotation.COUNTERCLOCKWISE_90).isValidPosition(world, pos))
                return;

            world.setBlockState(pos, state.rotate(Rotation.COUNTERCLOCKWISE_90));
            world.neighborChanged(pos, block, pos);
        } else {
            if (!state.rotate(Rotation.CLOCKWISE_90).isValidPosition(world, pos))
                return;

            world.setBlockState(pos, state.rotate(Rotation.CLOCKWISE_90));
            world.neighborChanged(pos, block, pos);
        }
    }

    /**
     * This method causes the default Properties.ROTATION_0_15 behavior to run.
     * @param world An instance of the world where this block was wrenched.
     * @param player The player who wrenched this block.
     * @param pos The position of the block interacted with.
     * @param face The side of the block interacted with.
     * @author Zundrel
     */
    public static void doRotationBehavior(World world, PlayerEntity player, BlockPos pos, Direction face) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (ModKeys.isSecondaryPressed(player)) {
            if (block instanceof SkullBlock)
                world.setBlockState(pos, state.with(BlockStateProperties.ROTATION_0_15, MathHelper.floor((double)((player.rotationYaw) * 16.0F / 360.0F) + 0.5D) & 15));
            else
                world.setBlockState(pos, state.with(BlockStateProperties.ROTATION_0_15, MathHelper.floor((double)((180.0F + player.rotationYaw) * 16.0F / 360.0F) + 0.5D) & 15));
            world.neighborChanged(pos, block, pos);
            return;
        }

        if (player.isSneaking()) {
            if (state.get(BlockStateProperties.ROTATION_0_15) > 0) {
                world.setBlockState(pos, state.with(BlockStateProperties.ROTATION_0_15, state.get(BlockStateProperties.ROTATION_0_15) - 1));
            } else {
                world.setBlockState(pos, state.with(BlockStateProperties.ROTATION_0_15, 15));
            }

            world.neighborChanged(pos, block, pos);
            return;
        } else {
            if (state.get(BlockStateProperties.ROTATION_0_15) < 15) {
                world.setBlockState(pos, state.with(BlockStateProperties.ROTATION_0_15, state.get(BlockStateProperties.ROTATION_0_15) + 1));
            } else {
                world.setBlockState(pos, state.with(BlockStateProperties.ROTATION_0_15, 0));
            }

            world.neighborChanged(pos, block, pos);
            return;
        }
    }

    public static Wrench getWrench(Item item) {
        if (item instanceof Wrench)
            return (Wrench) item;
        else if (WrenchableRegistry.isWrench(item))
            return WrenchableRegistry.getWrench(item);

        return null;
    }

    public static boolean isWrench(Item item) {
        return item instanceof Wrench || WrenchableRegistry.isWrench(item);
    }
}
