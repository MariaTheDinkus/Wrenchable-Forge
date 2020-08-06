package com.zundrel.wrenchable.modkeys;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ModKeysConfigScreen extends Screen {
	protected final Screen parent;
	private ModKeysConfig.Option primary = ModKeysConfig.primary();
	private ModKeysConfig.Option secondary = ModKeysConfig.secondary();
	private ModKeysConfig.Option tertiary = ModKeysConfig.tertiary();
	private final String keyPrefix = Minecraft.IS_RUNNING_ON_MAC ? "config.modkeys.key.osx." : "config.modkeys.key.win.";

	private final ModKeysConfig.Option[] options = ModKeysConfig.Option.values();

	public ModKeysConfigScreen(Screen parent) {
		super(new TranslationTextComponent("config.modkeys.title"));
		this.parent = parent;
	}

	@Override
	public void closeScreen() {
		minecraft.displayGuiScreen(parent);
	}

	@Override
	protected void init() {
		addButton(new Button(width / 2 + 5, 100, 120, 20, new TranslationTextComponent(keyPrefix + primary.key), (buttonWidget) -> {
			primary = primary.ordinal() == options.length - 1 ? options[0] : options[primary.ordinal() + 1];
			buttonWidget.queueNarration(250);
		}) {
			@Override
			public IFormattableTextComponent getMessage() {
				return new TranslationTextComponent(keyPrefix + primary.key);
			}

			@Override
			public void renderToolTip(MatrixStack matrixStack, int i, int j) {
				renderTooltip(matrixStack, new TranslationTextComponent("config.modkeys.help.primary"), i, j);
			}
		});

		addButton(new Button(width / 2 + 5, 130, 120, 20, new TranslationTextComponent(keyPrefix + secondary.key), (buttonWidget) -> {
			secondary = secondary.ordinal() == options.length - 1 ? options[0] : options[secondary.ordinal() + 1];
			buttonWidget.queueNarration(250);
		}) {
			@Override
			public IFormattableTextComponent getMessage() {
				return new TranslationTextComponent(keyPrefix + secondary.key);
			}

			@Override
			public void renderToolTip(MatrixStack matrixStack, int i, int j) {
				renderTooltip(matrixStack, new TranslationTextComponent("config.modkeys.help.secondary"), i, j);
			}
		});

		addButton(new Button(width / 2 + 5, 160, 120, 20, new TranslationTextComponent(keyPrefix + tertiary.key), (buttonWidget) -> {
			tertiary = tertiary.ordinal() == options.length - 1 ? options[0] : options[tertiary.ordinal() + 1];
			buttonWidget.queueNarration(250);
		}) {
			@Override
			public IFormattableTextComponent getMessage() {
				return new TranslationTextComponent(keyPrefix + tertiary.key);
			}

			@Override
			public void renderToolTip(MatrixStack matrixStack, int i, int j) {
				renderTooltip(matrixStack, new TranslationTextComponent("config.modkeys.help.tertiary"), i, j);
			}
		});

		addButton(new Button(width / 2 - 155, 200, 150, 20, new TranslationTextComponent("controls.reset"), (buttonWidget) -> {
			primary = ModKeysConfig.Option.CONTROL;
			secondary = ModKeysConfig.Option.ALT;
			tertiary = ModKeysConfig.Option.SUPER;
		}));

		addButton(new Button(width / 2 + 5, 200, 150, 20, new TranslationTextComponent("gui.done"), (buttonWidget) -> {
			ModKeysConfig.saveOptions(primary, secondary, tertiary);
			minecraft.displayGuiScreen(parent);
		}));
	}

	@Override
	public void render(MatrixStack matrixStack, int i, int j, float f) {
		renderBackground(matrixStack, 0);
		drawCenteredString(matrixStack, font, title, width / 2, 5, 16777215);
		drawRightAlignedText(matrixStack, new TranslationTextComponent("config.modkeys.label.primary"), width / 2 - 5, 105, 16777215);
		drawRightAlignedText(matrixStack, new TranslationTextComponent("config.modkeys.label.secondary"), width / 2 - 5, 135, 16777215);
		drawRightAlignedText(matrixStack, new TranslationTextComponent("config.modkeys.label.tertiary"), width / 2 - 5, 165, 16777215);

		super.render(matrixStack, i, j, f);
	}

	private void drawRightAlignedText(MatrixStack matrices, ITextComponent text, int x, int y, int color) {
		font.drawStringWithShadow(matrices, text.toString(), x - font.getStringWidth(text.getUnformattedComponentText()), y, color);
	}
}
