package com.dwarfeng.dct.struct;

import com.dwarfeng.dct.handler.FlatDataCodec;
import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.dct.util.FlatDataCodecUtil;
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
        FlatDataCodecUtil.checkCodec(flatDataCodec);

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
            FlatDataCodecUtil.checkCodec(flatDataCodec);
            this.flatDataCodec = flatDataCodec;
            return this;
        }

        public Builder setValueCodingHandler(ValueCodingHandler valueCodingHandler) {
            FlatDataCodecUtil.checkValueCodingHandler(valueCodingHandler);
            this.valueCodingHandler = valueCodingHandler;
            return this;
        }

        @Override
        public DataCodingConfig build() {
            return new DataCodingConfig(flatDataCodec, valueCodingHandler);
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
