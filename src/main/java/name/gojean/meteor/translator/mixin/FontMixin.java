package name.gojean.meteor.translator.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import meteordevelopment.meteorclient.renderer.text.Font;
import org.lwjgl.stb.STBTTPackRange;
import org.lwjgl.stb.STBTTPackedchar;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.ByteBuffer;

@Mixin(Font.class)
public class FontMixin {
    @Shadow
    @Final
    @Mutable
    private static int size;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void changeSize(ByteBuffer buffer, int height, CallbackInfo ci) {
        size = 4096;
    }

    @ModifyVariable(method = "<init>", at = @At("STORE"), name = "cdata")
    private STBTTPackedchar.Buffer[] resizeCdata(STBTTPackedchar.Buffer[] value) {
        return new STBTTPackedchar.Buffer[]{
            STBTTPackedchar.create(95), // Basic Latin
            STBTTPackedchar.create(96), // Latin 1 Supplement
            STBTTPackedchar.create(128), // Latin Extended-A
            STBTTPackedchar.create(144), // Greek and Coptic
            STBTTPackedchar.create(256), // Cyrillic
            STBTTPackedchar.create(1), // infinity symbol
            STBTTPackedchar.create(0x5200), // Chinese character
            STBTTPackedchar.create(0xF0), // Full-width symbols
            STBTTPackedchar.create(0x40) // CJK symbols
        };
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/stb/STBTTPackRange$Buffer;flip()Lorg/lwjgl/system/CustomBuffer;"))
    private void addRange(ByteBuffer buffer, int height, CallbackInfo ci, @Local(name = "packRange") STBTTPackRange.Buffer packRange, @Local(name = "cdata") STBTTPackedchar.Buffer[] cdata) {
        packRange.put(STBTTPackRange.create().set(height, 0x4E00, null, 0x5200, cdata[6], (byte) 2, (byte) 2));
        packRange.put(STBTTPackRange.create().set(height, 0xFF00, null, 0xF0, cdata[7], (byte) 2, (byte) 2));
        packRange.put(STBTTPackRange.create().set(height, 0x3000, null, 0x40, cdata[8], (byte) 2, (byte) 2));
    }
}
