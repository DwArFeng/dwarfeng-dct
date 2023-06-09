package com.dwarfeng.dct.handler.fdc;

import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.exception.DctException;
import com.dwarfeng.dct.exception.FlatDataCodecDecodeException;
import com.dwarfeng.dct.exception.FlatDataCodecEncodeException;
import com.dwarfeng.dct.handler.FlatDataCodec;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 扁平数据编解码器的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractFlatDataCodec implements FlatDataCodec {

    @Override
    public String encode(FlatData target) throws HandlerException {
        try {
            return doEncode(target);
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new FlatDataCodecEncodeException(e, target);
        }
    }

    /**
     * 编码方法的具体实现。
     *
     * @param target 指定的目标对象。
     * @return 编码后的文本。
     * @throws Exception 编码过程中发生的异常。
     * @see FlatDataCodec#encode(FlatData)
     */
    protected abstract String doEncode(FlatData target) throws Exception;

    @Override
    public FlatData decode(String text) throws HandlerException {
        try {
            return doDecode(text);
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new FlatDataCodecDecodeException(e, text);
        }
    }

    /**
     * 解码方法的具体实现。
     *
     * @param text 指定的文本。
     * @return 解码后的对象。
     * @throws Exception 解码过程中发生的异常。
     * @see FlatDataCodec#decode(String)
     */
    protected abstract FlatData doDecode(String text) throws Exception;
}
