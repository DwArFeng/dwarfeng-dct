package com.dwarfeng.dct.util;

import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;
import java.util.Comparator;
import java.util.function.Function;

/**
 * 比较工具类。
 *
 * <p>
 * 该工具类提供了一些比较器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class CompareUtil {

    // region 主键比较

    /**
     * 长整型主键比较器，按照主键的升序进行比较。
     */
    public static final Comparator<LongIdKey> LONG_ID_KEY_ASC_COMPARATOR = Comparator.comparing(LongIdKey::getLongId);

    /**
     * 长整型主键比较器，按照主键的降序进行比较。
     */
    public static final Comparator<LongIdKey> LONG_ID_KEY_DESC_COMPARATOR = LONG_ID_KEY_ASC_COMPARATOR.reversed();

    // endregion

    // region 日期比较

    /**
     * Instant 比较器，按照 Instant 的升序进行比较。
     */
    public static final Comparator<Instant> INSTANT_ASC_COMPARATOR = Comparator.comparing(Function.identity());

    /**
     * Instant 比较器，按照 Instant 的降序进行比较。
     */
    public static final Comparator<Instant> INSTANT_DESC_COMPARATOR = INSTANT_ASC_COMPARATOR.reversed();

    // endregion

    // region 数据比较

    /**
     * 数据比较器，按照数据的默认顺序进行比较。
     */
    public static final Comparator<Data> DATA_DEFAULT_COMPARATOR =
            Comparator.comparing(Data::getPointKey, LONG_ID_KEY_ASC_COMPARATOR)
                    .thenComparing(DataUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 数据比较器，按照数据的发生 Instant 的升序进行比较。
     */
    public static final Comparator<Data> DATA_HAPPENED_INSTANT_ASC_COMPARATOR =
            Comparator.comparing(DataUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 数据比较器，按照数据的发生 Instant 的降序进行比较。
     */
    public static final Comparator<Data> DATA_HAPPENED_INSTANT_DESC_COMPARATOR =
            Comparator.comparing(DataUtil::getHappenedInstant, INSTANT_DESC_COMPARATOR);

    // endregion

    // region 通用数据比较

    /**
     * 通用数据比较器，按照通用数据的默认顺序进行比较。
     */
    public static final Comparator<GeneralData> GENERAL_DATA_DEFAULT_COMPARATOR =
            Comparator.comparing(GeneralData::getPointKey, LONG_ID_KEY_ASC_COMPARATOR)
                    .thenComparing(GeneralDataUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 通用数据比较器，按照通用数据的发生 Instant 的升序进行比较。
     */
    public static final Comparator<GeneralData> GENERAL_DATA_HAPPENED_INSTANT_ASC_COMPARATOR =
            Comparator.comparing(GeneralDataUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 通用数据比较器，按照通用数据的发生 Instant 的降序进行比较。
     */
    public static final Comparator<GeneralData> GENERAL_DATA_HAPPENED_INSTANT_DESC_COMPARATOR =
            Comparator.comparing(GeneralDataUtil::getHappenedInstant, INSTANT_DESC_COMPARATOR);

    // endregion

    // region 扁平数据比较

    /**
     * 扁平数据比较器，按照扁平数据的默认顺序进行比较。
     */
    public static final Comparator<FlatData> FLAT_DATA_DEFAULT_COMPARATOR =
            Comparator.comparing(FlatData::getPointKey, LONG_ID_KEY_ASC_COMPARATOR)
                    .thenComparing(FlatDataUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 扁平数据比较器，按照扁平数据的发生 Instant 的升序进行比较。
     */
    public static final Comparator<FlatData> FLAT_DATA_HAPPENED_INSTANT_ASC_COMPARATOR =
            Comparator.comparing(FlatDataUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 扁平数据比较器，按照扁平数据的发生 Instant 的降序进行比较。
     */
    public static final Comparator<FlatData> FLAT_DATA_HAPPENED_INSTANT_DESC_COMPARATOR =
            Comparator.comparing(FlatDataUtil::getHappenedInstant, INSTANT_DESC_COMPARATOR);

    // endregion

    private CompareUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
