package com.dwarfeng.dct.util;

import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dutil.basic.time.TimeUtil;

import java.time.Instant;
import java.util.Objects;

/**
 * 通用数据工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class GeneralDataUtil {

    /**
     * 获取指定通用数据所表示的瞬时时间。
     *
     * @param generalData 指定的通用数据。
     * @return 指定通用数据所表示的瞬时时间。
     * @throws NullPointerException     入口参数为 <code>null</code>。
     * @throws IllegalArgumentException 入口参数不合法。
     */
    public static Instant getHappenedInstant(GeneralData generalData) {
        Objects.requireNonNull(generalData, "generalData 不能为空");
        Objects.requireNonNull(generalData.getHappenedDate(), "generalData.happenedDate 不能为空");
        TimeUtil.checkNanoOffset(generalData.getHappenedDateNanoOffset());

        return TimeUtil.toInstant(generalData.getHappenedDate(), generalData.getHappenedDateNanoOffset());
    }

    /**
     * 将瞬时时间设置到指定的通用数据中。
     *
     * <p>
     * 该方法会同步设置发生时间与毫秒内纳秒偏移。
     *
     * @param generalData 指定的通用数据。
     * @param instant     指定的瞬时时间。
     * @throws NullPointerException 入口参数为 <code>null</code>。
     */
    public static void setHappenedInstant(GeneralData generalData, Instant instant) {
        Objects.requireNonNull(generalData, "generalData 不能为空");
        Objects.requireNonNull(instant, "instant 不能为空");

        generalData.setHappenedDate(TimeUtil.toDate(instant));
        generalData.setHappenedDateNanoOffset(TimeUtil.toNanoOffset(instant));
    }

    private GeneralDataUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
