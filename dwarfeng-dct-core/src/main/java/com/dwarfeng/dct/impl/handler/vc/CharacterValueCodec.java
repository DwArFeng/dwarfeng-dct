package com.dwarfeng.dct.impl.handler.vc;

import com.dwarfeng.dct.sdk.handler.vc.AbstractValueCodec;
import com.dwarfeng.dct.sdk.util.Constants;

import javax.annotation.Nonnull;

/**
 * Character 值编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class CharacterValueCodec extends AbstractValueCodec {

    public CharacterValueCodec() {
        super(Character.class, "character", Constants.VALUE_CODEC_PRIORITY_NORMAL);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) {
        return Character.toString((Character) target);
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) {
        return text.charAt(0);
    }

    @Override
    public String toString() {
        return "CharacterValueCodec{" +
                "targetClass=" + targetClass +
                ", valuePrefix='" + valuePrefix + '\'' +
                ", priority=" + priority +
                '}';
    }
}
