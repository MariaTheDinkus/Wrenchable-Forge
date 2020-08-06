package com.zundrel.wrenchable.block.defaults;

import com.zundrel.wrenchable.block.PropertyListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SlabTypePropertyListener extends PropertyListener {
    public SlabTypePropertyListener() {
        super(BlockStateProperties.SLAB_TYPE);
    }

    @Override
    public void onWrenched(World world, PlayerEntity player, BlockPos pos, Direction face) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (state.get(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM) {
            world.setBlockState(pos, state.with(BlockStateProperties.SLAB_TYPE, SlabType.TOP));
            world.neighborChanged(pos, block, pos);
            return;
        } else if (state.get(BlockStateProperties.SLAB_TYPE) == SlabType.TOP) {
            world.setBlockState(pos, state.with(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM));
            world.neighborChanged(pos, block, pos);
            return;
        }
    }
}
