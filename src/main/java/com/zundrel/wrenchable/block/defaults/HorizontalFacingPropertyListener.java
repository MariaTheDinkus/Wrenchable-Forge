package com.zundrel.wrenchable.block.defaults;

import com.zundrel.wrenchable.WrenchableUtilities;
import com.zundrel.wrenchable.block.PropertyListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HorizontalFacingPropertyListener extends PropertyListener {
    public HorizontalFacingPropertyListener() {
        super(BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public void onWrenched(World world, PlayerEntity player, BlockPos pos, Direction face) {
        WrenchableUtilities.doHorizontalFacingBehavior(world, player, pos, face);
    }
}
