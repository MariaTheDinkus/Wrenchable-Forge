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

package com.zundrel.wrenchable.modkeys.mixin.client;

import com.zundrel.wrenchable.modkeys.ModKeysConfigScreen;
import net.minecraft.client.gui.screen.ControlsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public class MixinControlsOptionsScreen extends Screen {

	public MixinControlsOptionsScreen(ITextComponent title) {
		super(title);
	}

	@Inject(at = @At(value = "RETURN"), method = "init()V")
	public void drawMenuButton(CallbackInfo info) {
		final Screen thisScreen =  this;

		addButton(new Button(width - 100, 10, 90, 20, new TranslationTextComponent("config.modkeys.button"), (buttonWidget) -> {
			minecraft.displayGuiScreen(new ModKeysConfigScreen(thisScreen));
		}));
	}
}
