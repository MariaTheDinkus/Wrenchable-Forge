package com.zundrel.wrenchable.block.defaults;

import com.zundrel.wrenchable.WrenchableUtilities;
import com.zundrel.wrenchable.block.InstanceListener;
import com.zundrel.wrenchable.modkeys.api.ModKeys;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SignInstanceListener extends InstanceListener {
    public SignInstanceListener() {
        super(AbstractSignBlock.class);
    }

    @Override
    public void onWrenched(World world, PlayerEntity player, BlockPos pos, Direction face) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        SignTileEntity blockEntity = (SignTileEntity) world.getTileEntity(pos);

        if (ModKeys.isPrimaryPressed(player) && world.getTileEntity(pos) instanceof SignTileEntity) {
            blockEntity.setPlayer(player);
            if (world.isRemote()) {
                blockEntity.setEditable(true);
            }
            player.openSignEditor((SignTileEntity) world.getTileEntity(pos));
            return;
        }

        if (block instanceof StandingSignBlock) {
            WrenchableUtilities.doRotationBehavior(world, player, pos, face);
        } else if (block instanceof WallSignBlock) {
            WrenchableUtilities.doHorizontalFacingBehavior(world, player, pos, face);
        }
    }
}
