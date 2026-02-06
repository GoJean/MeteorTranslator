package name.gojean.meteor.translator.mixin;

import name.gojean.meteor.translator.Translator;
import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Language.class)
public class LanguageMixin {
    @Inject(method = "inject", at = @At("TAIL"))
    private static void translate(Language language, CallbackInfo ci) {
        for (Translator t: Translator.translators) {
            t.translate();
        }
    }
}
