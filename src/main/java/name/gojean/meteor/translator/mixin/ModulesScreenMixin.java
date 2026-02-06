package name.gojean.meteor.translator.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.screens.ModulesScreen;
import meteordevelopment.meteorclient.gui.tabs.Tab;
import meteordevelopment.meteorclient.gui.tabs.TabScreen;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModulesScreen.class)
public abstract class ModulesScreenMixin extends TabScreen {
    public ModulesScreenMixin(GuiTheme theme, Tab tab) {
        super(theme, tab);
    }

    @Inject(method = "initWidgets", at = @At(value = "INVOKE", target = "Lmeteordevelopment/meteorclient/gui/GuiTheme;label(Ljava/lang/String;)Lmeteordevelopment/meteorclient/gui/widgets/WLabel;", ordinal = 0), cancellable = true)
    private void helpTranslate(CallbackInfo ci, @Local(name = "help") WVerticalList help) {
        help.add(theme.label(Language.getInstance().getOrDefault("meteor.help.left", "Left click - Toggle module")));
        help.add(theme.label(Language.getInstance().getOrDefault("meteor.help.right", "Right click - Open module settings")));
        ci.cancel();
    }
}
