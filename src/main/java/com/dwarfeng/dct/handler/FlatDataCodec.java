package com.dwarfeng.dct.handler;

import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 扁平数据编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface FlatDataCodec {

    /**
     * 编码。
     *
     * @param target 目标对象。
     * @return 编码后的文本。
     * @throws HandlerException 处理器异常。
     */
    String encode(FlatData target) throws HandlerException;

    /**
     * 解码。
     *
     * @param text 目标字文本。
     * @return 解码后的对象。
     * @throws HandlerException 处理器异常。
     */
    FlatData decode(String text) throws HandlerException;
}
