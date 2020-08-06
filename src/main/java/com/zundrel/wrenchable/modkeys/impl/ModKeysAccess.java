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

import net.minecraft.entity.player.PlayerEntity;

public interface ModKeysAccess {
	int SHIFT = 1;
	int CONTROL = 2;
	int ALT = 4;
	int SUPER = 8;
	int PRIMARY = 16;
	int SECONDARY = 32;
	int TERTIARY = 64;

	void mk_flags(byte flags);

	byte mk_flags();

	static boolean isShiftPressed(PlayerEntity player) {
		return player != null && (((ModKeysAccess) player).mk_flags() & SHIFT) == SHIFT;
	}

	static boolean isControlPressed(PlayerEntity player) {
		return player != null && (((ModKeysAccess) player).mk_flags() & CONTROL) == CONTROL;
	}

	static boolean isAltPressed(PlayerEntity player) {
		return player != null && (((ModKeysAccess) player).mk_flags() & ALT) == ALT;
	}

	static boolean isSuperPressed(PlayerEntity player) {
		return player != null && (((ModKeysAccess) player).mk_flags() & SUPER) == SUPER;
	}

	static boolean isPrimaryPressed(PlayerEntity player) {
		return player != null && (((ModKeysAccess) player).mk_flags() & PRIMARY) == PRIMARY;
	}

	static boolean isSecondaryPressed(PlayerEntity player) {
		return player != null && (((ModKeysAccess) player).mk_flags() & SECONDARY) == SECONDARY;
	}

	static boolean isTertiaryPressed(PlayerEntity player) {
		return player != null && (((ModKeysAccess) player).mk_flags() & TERTIARY) == TERTIARY;
	}
}
