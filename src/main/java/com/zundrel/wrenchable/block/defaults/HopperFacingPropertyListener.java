package com.zundrel.wrenchable.block.defaults;

import com.zundrel.wrenchable.WrenchableUtilities;
import com.zundrel.wrenchable.block.PropertyListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HopperFacingPropertyListener extends PropertyListener {
    public HopperFacingPropertyListener() {
        super(BlockStateProperties.FACING_EXCEPT_UP);
    }

    @Override
    public void onWrenched(World world, PlayerEntity player, BlockPos pos, Direction face) {
        WrenchableUtilities.doHopperFacingBehavior(world, player, pos, face);
    }
}
