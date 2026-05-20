package com.dwarfeng.dct.stack.struct;

import com.dwarfeng.dct.stack.handler.FlatDataCodec;
import com.dwarfeng.dct.stack.handler.ValueCodingHandler;
import com.dwarfeng.dct.stack.util.DataCodingConfigUtil;
import com.dwarfeng.dutil.basic.prog.Buildable;

/**
 * 数据编解码器配置。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public final class DataCodingConfig {

    private final FlatDataCodec flatDataCodec;
    private final ValueCodingHandler valueCodingHandler;

    public DataCodingConfig(FlatDataCodec flatDataCodec, ValueCodingHandler valueCodingHandler) {
        this(flatDataCodec, valueCodingHandler, false);
    }

    private DataCodingConfig(
            FlatDataCodec flatDataCodec, ValueCodingHandler valueCodingHandler, boolean paramReliable
    ) {
        // 如果参数不可靠，则检查参数。
        if (!paramReliable) {
            DataCodingConfigUtil.checkFlatDataCodec(flatDataCodec);
            DataCodingConfigUtil.checkValueCodingHandler(valueCodingHandler);
        }
        // 设置值。
        this.flatDataCodec = flatDataCodec;
        this.valueCodingHandler = valueCodingHandler;
    }

    public FlatDataCodec getFlatDataCodec() {
        return flatDataCodec;
    }

    public ValueCodingHandler getValueCodingHandler() {
        return valueCodingHandler;
    }

    @Override
    public String toString() {
        return "DataCodingConfig{" +
                "flatDataCodec=" + flatDataCodec +
                ", valueCodingHandler=" + valueCodingHandler +
                '}';
    }

    public static final class Builder implements Buildable<DataCodingConfig> {

        private FlatDataCodec flatDataCodec;
        private ValueCodingHandler valueCodingHandler;

        public Builder() {
        }

        public Builder setFlatDataCodec(FlatDataCodec flatDataCodec) {
            this.flatDataCodec = flatDataCodec;
            return this;
        }

        public Builder setValueCodingHandler(ValueCodingHandler valueCodingHandler) {
            this.valueCodingHandler = valueCodingHandler;
            return this;
        }

        @Override
        public DataCodingConfig build() {
            // 检查参数。
            DataCodingConfigUtil.checkFlatDataCodec(flatDataCodec);
            DataCodingConfigUtil.checkValueCodingHandler(valueCodingHandler);

            // 构造并返回配置。
            return new DataCodingConfig(flatDataCodec, valueCodingHandler, true);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "flatDataCodec=" + flatDataCodec +
                    ", valueCodingHandler=" + valueCodingHandler +
                    '}';
        }
    }
}
