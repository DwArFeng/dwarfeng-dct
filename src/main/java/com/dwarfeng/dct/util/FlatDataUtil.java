package com.dwarfeng.dct.util;

import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dutil.basic.time.TimeUtil;

import java.time.Instant;
import java.util.Objects;

/**
 * 扁平数据工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class FlatDataUtil {

    /**
     * 获取指定扁平数据所表示的瞬时时间。
     *
     * @param flatData 指定的扁平数据。
     * @return 指定扁平数据所表示的瞬时时间。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 入口参数不合法。
     */
    public static Instant getHappenedInstant(FlatData flatData) {
        Objects.requireNonNull(flatData, "flatData 不能为空");
        Objects.requireNonNull(flatData.getHappenedDate(), "flatData.happenedDate 不能为空");
        TimeUtil.checkNanoOffset(flatData.getHappenedDateNanoOffset());

        return TimeUtil.toInstant(flatData.getHappenedDate(), flatData.getHappenedDateNanoOffset());
    }

    /**
     * 将瞬时时间设置到指定的扁平数据中。
     *
     * <p>
     * 该方法会同步设置发生时间与毫秒内纳秒偏移。
     *
     * @param flatData 指定的扁平数据。
     * @param instant  指定的瞬时时间。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void setHappenedInstant(FlatData flatData, Instant instant) {
        Objects.requireNonNull(flatData, "flatData 不能为空");
        Objects.requireNonNull(instant, "instant 不能为空");

        flatData.setHappenedDate(TimeUtil.toDate(instant));
        flatData.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    private FlatDataUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
