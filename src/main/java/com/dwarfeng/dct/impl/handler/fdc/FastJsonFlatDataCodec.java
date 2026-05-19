package com.dwarfeng.dct.impl.handler.fdc;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dct.sdk.handler.fdc.AbstractFlatDataCodec;
import com.dwarfeng.dct.stack.bean.dto.FastJsonFlatData;
import com.dwarfeng.dct.stack.bean.dto.FlatData;

/**
 * FastJson 扁平数据编解码器。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonFlatDataCodec extends AbstractFlatDataCodec {

    @Override
    protected String doEncode(FlatData target) {
        return JSON.toJSONString(FastJsonFlatData.of(target), false);
    }

    @Override
    protected FlatData doDecode(String text) {
        return FastJsonFlatData.toStackBean(JSON.parseObject(text, FastJsonFlatData.class));
    }
}
