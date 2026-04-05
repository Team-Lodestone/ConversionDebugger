package me.dexrn.conversiondebugger.mixin.net.minecraft.client.title;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.gui.screen.TitleScreen;

import java.util.List;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
	@Unique
	private static final int ID_SETTINGS = 4;

	@Redirect(method = "init", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 2))
	private boolean exampleMod$onInit(List<ButtonWidget> instance, Object e) {
		instance.add(new ButtonWidget(ID_SETTINGS, this.width / 2 - 100, this.height / 4 + 96, "ConversionDebugger Settings"));
		return true;
	}
}
