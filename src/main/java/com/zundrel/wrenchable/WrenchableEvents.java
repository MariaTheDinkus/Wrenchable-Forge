package com.zundrel.wrenchable;

import com.zundrel.wrenchable.api.WrenchableRegistry;
import com.zundrel.wrenchable.block.BlockListener;
import com.zundrel.wrenchable.block.BlockWrenchable;
import com.zundrel.wrenchable.block.PropertyListener;
import com.zundrel.wrenchable.entity.EntityWrenchable;
import com.zundrel.wrenchable.wrench.Wrench;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WrenchableMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WrenchableEvents {
    @SubscribeEvent
    public static void onBlockInteract(final PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        PlayerEntity playerEntity = event.getPlayer();
        Hand hand = event.getHand();
        ItemStack heldStack = playerEntity.getHeldItem(hand);
        BlockPos pos = event.getPos();
        Direction face = event.getFace();
        TileEntity blockEntity = world.getTileEntity(pos);

        if (!heldStack.isEmpty() && heldStack.getItem() == Items.SLIME_BALL && world.getBlockState(pos).getBlock() == Blocks.PISTON) {
            if (!world.getBlockState(pos).get(BlockStateProperties.EXTENDED)) {
                world.setBlockState(pos, Blocks.STICKY_PISTON.getDefaultState().with(BlockStateProperties.FACING, world.getBlockState(pos).get(BlockStateProperties.FACING)));
                world.playSound(null, pos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1, 1F);
                if (!playerEntity.isCreative())
                    heldStack.shrink(1);
            }
        }

        if (!heldStack.isEmpty() && WrenchableUtilities.isWrench(heldStack.getItem())) {
            Wrench wrench = WrenchableUtilities.getWrench(heldStack.getItem());

            if (world.getBlockState(pos).getBlock() instanceof BlockWrenchable) {
                wrench.onBlockWrenched(world, heldStack, playerEntity, hand, pos, face);
                ((BlockWrenchable) world.getBlockState(pos).getBlock()).onWrenched(world, playerEntity, pos, face);

                if (blockEntity instanceof BlockWrenchable) {
                    ((BlockWrenchable) blockEntity).onWrenched(world, playerEntity, pos, face);
                    wrench.onBlockEntityWrenched(world, heldStack, playerEntity, hand, blockEntity, pos, face);
                }

                event.setCancellationResult(ActionResultType.SUCCESS);
            } else if (blockEntity instanceof BlockWrenchable) {
                wrench.onBlockWrenched(world, heldStack, playerEntity, hand, pos, face);
                wrench.onBlockEntityWrenched(world, heldStack, playerEntity, hand, blockEntity, pos, face);
                ((BlockWrenchable) blockEntity).onWrenched(world, playerEntity, pos, face);

                event.setCancellationResult(ActionResultType.SUCCESS);
            } else if (WrenchableRegistry.isBlockWrenchable(world.getBlockState(pos).getBlock())) {
                BlockListener wrenchable = WrenchableRegistry.getBlockWrenchable(world.getBlockState(pos).getBlock());

                wrench.onBlockWrenched(world, heldStack, playerEntity, hand, pos, face);
                wrenchable.onWrenched(world, playerEntity, pos, face);

                event.setCancellationResult(ActionResultType.SUCCESS);
            } if (WrenchableRegistry.isBlockInstanceWrenchable(world.getBlockState(pos).getBlock())) {
                wrench.onBlockWrenched(world, heldStack, playerEntity, hand, pos, face);
                WrenchableRegistry.getBlockInstanceWrenchable(world.getBlockState(pos).getBlock()).onWrenched(world, playerEntity, pos, face);

                event.setCancellationResult(ActionResultType.SUCCESS);
            } else {
                for (PropertyListener wrenchable : WrenchableRegistry.PROPERTY_LISTENERS) {
                    if (world.getBlockState(pos).hasProperty(wrenchable.getProperty())) {
                        wrench.onBlockWrenched(world, heldStack, playerEntity, hand, pos, face);
                        wrenchable.onWrenched(world, playerEntity, pos, face);

                        event.setCancellationResult(ActionResultType.SUCCESS);
                    }
                }
            }
        }

        event.setCancellationResult(ActionResultType.PASS);
    }

    @SubscribeEvent
    public static void onEntityInteract(final PlayerInteractEvent.EntityInteract event) {
        World world = event.getWorld();
        PlayerEntity playerEntity = event.getPlayer();
        Hand hand = event.getHand();
        ItemStack heldStack = playerEntity.getHeldItem(hand);

        if (!heldStack.isEmpty() && WrenchableUtilities.isWrench(heldStack.getItem())) {
            Wrench wrench = WrenchableUtilities.getWrench(heldStack.getItem());

            if (event.getTarget() instanceof EntityWrenchable) {
                wrench.onEntityWrenched(world, heldStack, playerEntity, hand, event.getPos());
                ((EntityWrenchable) event.getTarget()).onWrenched(world, playerEntity, event.getPos());
                event.setCancellationResult(ActionResultType.SUCCESS);
            } else if (WrenchableRegistry.isEntityTypeWrenchable(event.getTarget())) {
                wrench.onEntityWrenched(world, heldStack, playerEntity, hand, event.getPos());
                WrenchableRegistry.getEntityTypeWrenchable(event.getTarget()).onWrenched(world, playerEntity, event.getPos());
                event.setCancellationResult(ActionResultType.SUCCESS);
            }
        }

        event.setCancellationResult(ActionResultType.PASS);
    }
}
