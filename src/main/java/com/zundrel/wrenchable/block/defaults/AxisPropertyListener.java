package com.zundrel.wrenchable.block.defaults;

import com.zundrel.wrenchable.block.PropertyListener;
import com.zundrel.wrenchable.modkeys.api.ModKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AxisPropertyListener extends PropertyListener {
    public AxisPropertyListener() {
        super(BlockStateProperties.AXIS);
    }

    @Override
    public void onWrenched(World world, PlayerEntity player, BlockPos pos, Direction face) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (ModKeys.isSecondaryPressed(player)) {
            Direction.Axis axis = Direction.Axis.X;

            if (face == Direction.UP || face == Direction.DOWN)
                axis = Direction.Axis.Y;
            else if (face == Direction.NORTH || face == Direction.SOUTH)
                axis = Direction.Axis.Z;

            world.setBlockState(pos, state.with(BlockStateProperties.AXIS, axis));
            world.neighborChanged(pos, block, pos);
            return;
        }

        world.setBlockState(pos, state.func_235896_a_(BlockStateProperties.AXIS));
        world.neighborChanged(pos, block, pos);
        return;
    }
}
