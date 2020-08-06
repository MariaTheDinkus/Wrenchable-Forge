package com.zundrel.wrenchable.modkeys.packets;

import com.zundrel.wrenchable.modkeys.impl.ModKeysAccess;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ModKeysModifierPacket {
    private byte flags;

    public ModKeysModifierPacket() {
    }

    public ModKeysModifierPacket(byte flags) {
        this.flags = flags;
    }

    public static void encode(ModKeysModifierPacket msg, PacketBuffer buf) {
        buf.writeByte(msg.flags);
    }

    public static ModKeysModifierPacket decode(PacketBuffer buf) {
        return new ModKeysModifierPacket(buf.readByte());
    }

    public static class Handler {
        public static void handle(ModKeysModifierPacket message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity sender = ctx.get().getSender();
                if (sender != null) {
                    ((ModKeysAccess) sender).mk_flags(message.flags);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}