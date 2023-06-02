package com.dwarfeng.dct.handler.fdc;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.bean.dto.FastJsonFlatData;
import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.exception.FlatDataCodecDecodeException;
import com.dwarfeng.dct.exception.FlatDataCodecEncodeException;
import com.dwarfeng.dct.handler.FlatDataCodec;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * FastJson 扁平数据编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonFlatDataCodec implements FlatDataCodec {

    @Override
    public String encode(FlatData target) throws HandlerException {
        try {
            return JSON.toJSONString(FastJsonFlatData.of(target), false);
        } catch (Exception e) {
            throw new FlatDataCodecEncodeException(e, this, target);
        }
    }

    @Override
    public FlatData decode(String text) throws HandlerException {
        try {
            return FastJsonFlatData.toStackBean(JSON.parseObject(text, FastJsonFlatData.class));
        } catch (Exception e) {
            throw new FlatDataCodecDecodeException(e, this, text);
        }
    }
}
