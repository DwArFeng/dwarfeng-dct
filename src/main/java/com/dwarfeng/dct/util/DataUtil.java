package com.dwarfeng.dct.util;

import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.dutil.basic.time.TimeUtil;

import java.time.Instant;
import java.util.Objects;

/**
 * 数据工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class DataUtil {

    /**
     * 获取指定数据所表示的瞬时时间。
     *
     * @param data 指定的数据。
     * @return 指定数据所表示的瞬时时间。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 入口参数不合法。
     */
    public static Instant getHappenedInstant(Data data) {
        Objects.requireNonNull(data, "data 不能为空");
        Objects.requireNonNull(data.getHappenedDate(), "data.happenedDate 不能为空");
        TimeUtil.checkNanoOffset(data.getHappenedDateNanoOffset());

        return TimeUtil.toInstant(data.getHappenedDate(), data.getHappenedDateNanoOffset());
    }

    private DataUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
