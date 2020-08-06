/*******************************************************************************
 * Copyright 2019 grondag
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.zundrel.wrenchable.modkeys.impl;

import com.zundrel.wrenchable.modkeys.ModKeysConfig;
import com.zundrel.wrenchable.modkeys.ModKeysPacketHandler;
import com.zundrel.wrenchable.modkeys.packets.ModKeysModifierPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

public class ModKeysHandler {

	public static ResourceLocation PACKET_ID = new ResourceLocation("modkeys", "modifiers");

	@OnlyIn(Dist.CLIENT)
	private static byte lastFlags = 0;

	@OnlyIn(Dist.CLIENT)
	public static void update(Minecraft client) {
		final long handle = client.getMainWindow().getHandle();

		byte f = 0;

		if (InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_RIGHT_SHIFT)) {
			f |= ModKeysAccess.SHIFT;
		}

		if (InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_LEFT_CONTROL) || InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_RIGHT_CONTROL)) {
			f |= ModKeysAccess.CONTROL;
		}

		if (InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_LEFT_ALT) || InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_RIGHT_ALT)) {
			f |= ModKeysAccess.ALT;
		}

		if (InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_LEFT_SUPER) || InputMappings.isKeyDown(handle, GLFW.GLFW_KEY_RIGHT_SUPER)) {
			f |= ModKeysAccess.SUPER;
		}

		if ((f & ModKeysConfig.primary().flag) != 0) {
			f |= ModKeysAccess.PRIMARY;
		}

		if ((f & ModKeysConfig.secondary().flag) != 0) {
			f |= ModKeysAccess.SECONDARY;
		}

		if ((f & ModKeysConfig.tertiary().flag) != 0) {
			f |= ModKeysAccess.TERTIARY;
		}

		if (f != lastFlags) {
			lastFlags = f;
			@SuppressWarnings("resource")
			final ClientPlayerEntity player = Minecraft.getInstance().player;
			if (player != null) {
				((ModKeysAccess) player).mk_flags(f);
			}
			sendUpdatePacket(f);
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static void sendUpdatePacket(byte flags) {
		ModKeysPacketHandler.INSTANCE.sendToServer(new ModKeysModifierPacket(flags));
	}
}
