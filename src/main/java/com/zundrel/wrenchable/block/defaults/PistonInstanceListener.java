package com.zundrel.wrenchable.block.defaults;

import com.zundrel.wrenchable.WrenchableUtilities;
import com.zundrel.wrenchable.block.InstanceListener;
import com.zundrel.wrenchable.modkeys.api.ModKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PistonInstanceListener extends InstanceListener {
    public PistonInstanceListener() {
        super(PistonBlock.class);
    }

    @Override
    public void onWrenched(World world, PlayerEntity player, BlockPos pos, Direction face) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!state.get(BlockStateProperties.EXTENDED)) {
            if (ModKeys.isPrimaryPressed(player) && block == Blocks.STICKY_PISTON) {
                world.setBlockState(pos, Blocks.PISTON.getDefaultState().with(BlockStateProperties.FACING, state.get(BlockStateProperties.FACING)));
                world.playSound(null, pos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1, 1F);
                if (!player.isCreative())
                    player.inventory.placeItemBackInInventory(world, new ItemStack(Items.SLIME_BALL));

                return;
            }

            WrenchableUtilities.doFacingBehavior(world, player, pos, face);
        }
    }
}
