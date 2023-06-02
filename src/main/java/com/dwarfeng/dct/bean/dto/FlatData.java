package com.dwarfeng.dct.bean.dto;

import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.Objects;

/**
 * 扁平数据。
 *
 * <p>
 * 扁平数据是指将 {@link Data#getValue()} 中的数据转换为文本后的数据。<br>
 * 扁平数据包含数据的点位主键、数据的值、数据的发生时间。此处的数据的值是文本。
 *
 * <p>
 * 扁平数据的值的格式为：<code>prefix:flat_value</code>。<br>
 * 文本的第一个冒号之前的部分为前缀，第一个冒号之后的部分为扁平数据的值。
 *
 * @author DwArFeng
 * @see Data
 * @see GeneralData
 * @see com.dwarfeng.dct.util.Constants#FLAT_DATA_VALUE_PREFIX_DELIMITER
 * @since 1.0.0
 */
public class FlatData implements Dto {

    private static final long serialVersionUID = -8552570128924976374L;

    private LongIdKey pointKey;
    private String value;
    private Date happenedDate;

    public FlatData() {
    }

    public FlatData(LongIdKey pointKey, String value, Date happenedDate) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
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

        FlatData flatData = (FlatData) o;

        if (!Objects.equals(pointKey, flatData.pointKey)) return false;
        if (!Objects.equals(value, flatData.value)) return false;
        return Objects.equals(happenedDate, flatData.happenedDate);
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
        return "FlatData{" +
                "pointKey=" + pointKey +
                ", value='" + value + '\'' +
                ", happenedDate=" + happenedDate +
                '}';
    }
}
