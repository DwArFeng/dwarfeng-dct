package com.dwarfeng.dct.handler.vc;

import com.dwarfeng.dct.util.Constants;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.Base64;

/**
 * 可序列化的值编解码器。
 *
 * <p>
 * 该编解码器使用 Java 的序列化机制进行编解码，是解决对象的编解码的较为靠后的方案。<br>
 * 该编解码器的编码结果是一个 Base64 编码的字符串，该字符串是对象序列化后的结果。<br>
 * 几乎可以肯定的是，该编解码器的编码结果是不可读的，因为它是一个 Base64 编码的字符串，并且该字符串是对象序列化后的结果。<br>
 * 因此，该编码器的优先级是最低的。<br>
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class SerializableValueCodec extends AbstractValueCodec {

    public SerializableValueCodec() {
        super(Serializable.class, "serializable", Constants.VALUE_CODEC_PRIORITY_MIN);
    }

    @Nonnull
    @Override
    protected String doEncode(@Nonnull Object target) throws Exception {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)
        ) {
            objectOutputStream.writeObject(target);
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        }
    }

    @Nonnull
    @Override
    protected Object doDecode(@Nonnull String text) throws Exception {
        byte[] decode = Base64.getDecoder().decode(text);
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decode);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
        ) {
            return objectInputStream.readObject();
        }
    }
}
