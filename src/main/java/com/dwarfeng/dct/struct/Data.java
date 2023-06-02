package com.dwarfeng.dct.struct;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

/**
 * 数据。
 *
 * <p>
 * 数据是 dwarfeng-dct 的核心概念。每个数据都包含了数据的点位主键、数据的值、数据的发生时间。
 *
 * <p>
 * 数据的点位主键用于标识数据所属的点位，数据的值用于标识数据的值，数据的发生时间用于标识数据的发生时间。<br>
 * 数据的主键与数据的发生时间都不能为 <code>null</code>，数据的值可以为 <code>null</code>。
 *
 * <p>
 * 该接口的实现类，必须保证接口的所有方法均能够迅速返回，不得阻塞。<br>
 * 典型的实现方式是使用 java bean 的方式实现该接口。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface Data {

    /**
     * 获取数据的点位主键。
     *
     * @return 数据的点位主键。
     */
    @Nonnull
    LongIdKey getPointKey();

    /**
     * 获取数据的值。
     *
     * @return 数据的值。
     */
    @Nullable
    Object getValue();

    /**
     * 获取数据的发生时间。
     *
     * @return 数据的发生时间。
     */
    @Nonnull
    Date getHappenedDate();
}
