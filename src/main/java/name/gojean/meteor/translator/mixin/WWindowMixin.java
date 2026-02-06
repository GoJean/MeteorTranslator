package name.gojean.meteor.translator.mixin;

import meteordevelopment.meteorclient.gui.widgets.containers.WWindow;
import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WWindow.class)
public class WWindowMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static String translateTitle(String title) {
        return Language.getInstance().getOrDefault("meteor.window." + title.toLowerCase(), title);
    }
}
