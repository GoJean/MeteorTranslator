package name.gojean.meteor.translator.mixin;

import meteordevelopment.meteorclient.renderer.Fonts;
import meteordevelopment.meteorclient.renderer.text.FontFace;
import meteordevelopment.meteorclient.renderer.text.FontInfo;
import meteordevelopment.meteorclient.utils.render.FontUtils;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fonts.class)
public abstract class FontsMixin {
    @Mutable
    @Final
    @Shadow
    public static String[] BUILTIN_FONTS;

    @Shadow
    public static String DEFAULT_FONT_FAMILY;

    @Shadow
    public static FontFace DEFAULT_FONT;

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void addFonts(CallbackInfo ci) {
        BUILTIN_FONTS = new String[]{"JetBrains Mono", "Comfortaa", "Tw Cen MT", "Pixelation", "FZCuYuan"};
    }

    @Redirect(method = "refresh", at = @At(value = "FIELD", target = "Lmeteordevelopment/meteorclient/renderer/Fonts;DEFAULT_FONT_FAMILY:Ljava/lang/String;", opcode = Opcodes.PUTSTATIC))
    private static void setDefaultFontFamily(String value) {
        DEFAULT_FONT_FAMILY = FontUtils.getBuiltinFontInfo(BUILTIN_FONTS[4]).family();
    }

    @Redirect(method = "refresh", at = @At(value = "FIELD", target = "Lmeteordevelopment/meteorclient/renderer/Fonts;DEFAULT_FONT:Lmeteordevelopment/meteorclient/renderer/text/FontFace;", opcode = Opcodes.PUTSTATIC))
    private static void setDefaultFont(FontFace value) {
        DEFAULT_FONT = Fonts.getFamily(DEFAULT_FONT_FAMILY).get(FontInfo.Type.Regular);
    }
}
