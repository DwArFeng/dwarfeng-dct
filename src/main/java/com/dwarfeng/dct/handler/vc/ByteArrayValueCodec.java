package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;
import java.util.Base64;

/**
 * Byte 数组值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class ByteArrayValueCodec extends AbstractValueCodec {

    public ByteArrayValueCodec() {
        super(byte[].class, "byte_array", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        // 使用 Base64 编码。
        byte[] bytes = (byte[]) target;
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        // 使用 Base64 解码。
        return Base64.getDecoder().decode(text);
    }
}
