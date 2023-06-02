package com.dwarfeng.dct.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Date;
import java.util.Objects;

/**
 * FastJson 扁平数据。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class FastJsonFlatData implements Dto {

    private static final long serialVersionUID = 1853419362259735561L;

    public static FastJsonFlatData of(FlatData flatData) {
        if (Objects.isNull(flatData)) {
            return null;
        } else {
            return new FastJsonFlatData(
                    FastJsonLongIdKey.of(flatData.getPointKey()),
                    flatData.getValue(),
                    flatData.getHappenedDate()
            );
        }
    }

    public static FlatData toStackBean(FastJsonFlatData fastJsonFlatData) {
        if (Objects.isNull(fastJsonFlatData)) {
            return null;
        } else {
            return new FlatData(
                    FastJsonLongIdKey.toStackBean(fastJsonFlatData.getPointKey()),
                    fastJsonFlatData.getValue(),
                    fastJsonFlatData.getHappenedDate()
            );
        }
    }

    @JSONField(name = "point_key", ordinal = 1)
    private FastJsonLongIdKey pointKey;

    @JSONField(name = "value", ordinal = 2)
    private String value;

    @JSONField(name = "happened_date", ordinal = 3)
    private Date happenedDate;

    public FastJsonFlatData() {
    }

    public FastJsonFlatData(FastJsonLongIdKey pointKey, String value, Date happenedDate) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public FastJsonLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(FastJsonLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FastJsonFlatData that = (FastJsonFlatData) o;

        if (!Objects.equals(pointKey, that.pointKey)) return false;
        if (!Objects.equals(value, that.value)) return false;
        return Objects.equals(happenedDate, that.happenedDate);
    }

    @Override
    public int hashCode() {
        int result = pointKey != null ? pointKey.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (happenedDate != null ? happenedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FastJsonFlatData{" +
                "pointKey=" + pointKey +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
