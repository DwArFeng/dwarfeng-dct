package com.dwarfeng.dct.bean.dto;

import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;

/**
 * 通用数据。
 *
 * <p>
 * 该类是 {@link Data} 接口在本项目中的通用实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class GeneralData implements Dto, Data {

    private static final long serialVersionUID = 1426650154006805507L;

    private LongIdKey pointKey;
    private Object value;
    private Date happenedDate;
    private int happenedDateNanoOffset;

    public GeneralData() {
    }

    public GeneralData(LongIdKey pointKey, Object value, Date happenedDate) {
        this(pointKey, value, happenedDate, 0);
    }

    public GeneralData(LongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset) {
        this.pointKey = pointKey;
        this.value = value;
        this.happenedDate = happenedDate;
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    @Nonnull
    @Override
    public LongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(LongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    @Nullable
    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    @Override
    public int getHappenedDateNanoOffset() {
        return happenedDateNanoOffset;
    }

    public void setHappenedDateNanoOffset(int happenedDateNanoOffset) {
        this.happenedDateNanoOffset = happenedDateNanoOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralData that = (GeneralData) o;

        if (!Objects.equals(pointKey, that.pointKey)) return false;
        if (!Objects.equals(value, that.value)) return false;
        if (!Objects.equals(happenedDate, that.happenedDate)) return false;
        return happenedDateNanoOffset == that.happenedDateNanoOffset;
    }

    @Override
    public int hashCode() {
        int result = pointKey != null ? pointKey.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (happenedDate != null ? happenedDate.hashCode() : 0);
        result = 31 * result + happenedDateNanoOffset;
        return result;
    }

    @Override
    public String toString() {
        return "GeneralData{" +
                "pointKey=" + pointKey +
                ", value=" + value +
                ", happenedDate=" + happenedDate +
                ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                '}';
    }
}
