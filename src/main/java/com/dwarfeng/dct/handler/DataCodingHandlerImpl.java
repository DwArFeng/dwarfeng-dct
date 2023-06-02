package com.dwarfeng.dct.handler;

import com.dwarfeng.dct.bean.dto.FlatData;
import com.dwarfeng.dct.bean.dto.GeneralData;
import com.dwarfeng.dct.exception.DctException;
import com.dwarfeng.dct.struct.Data;
import com.dwarfeng.dct.struct.DataCodingConfig;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import javax.annotation.Nonnull;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据编码处理器实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DataCodingHandlerImpl implements DataCodingHandler {

    private final DataCodingConfig config;

    private final Lock lock = new ReentrantLock();

    private boolean initFlag;

    public DataCodingHandlerImpl(DataCodingConfig config) {
        this.config = config;
    }

    @BehaviorAnalyse
    @Override
    public void init() throws HandlerException {
        lock.lock();
        try {
            if (initFlag) {
                return;
            }

            config.getValueCodingHandler().init();

            initFlag = true;
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new DctException(e);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Nonnull
    @Override
    public String encode(@Nonnull Data data) throws HandlerException {
        lock.lock();
        try {
            // 取出数据的值，使用值编解码器进行编码。
            Object value = data.getValue();
            String flatValue = config.getValueCodingHandler().encode(value);
            // 构造 FlatData。
            FlatData flatData = new FlatData(data.getPointKey(), flatValue, data.getHappenedDate());
            // 使用 FlatData 编解码器进行编码。
            return config.getFlatDataCodec().encode(flatData);
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new DctException(e);
        } finally {
            lock.unlock();
        }
    }

    @BehaviorAnalyse
    @Nonnull
    @Override
    public Data decode(@Nonnull String string) throws HandlerException {
        lock.lock();
        try {
            // 使用 FlatData 编解码器进行解码。
            FlatData flatData = config.getFlatDataCodec().decode(string);
            // 使用值编解码器进行解码。
            Object value = config.getValueCodingHandler().decode(flatData.getValue());
            // 构造 Data。
            return new GeneralData(flatData.getPointKey(), value, flatData.getHappenedDate());
        } catch (DctException e) {
            throw e;
        } catch (Exception e) {
            throw new DctException(e);
        } finally {
            lock.unlock();
        }
    }
}
