package com.dwarfeng.dct.impl.service;

import com.dwarfeng.dct.node.configuration.SimpleConfiguration;
import com.dwarfeng.dct.sdk.util.GeneralDataUtil;
import com.dwarfeng.dct.stack.bean.dto.GeneralData;
import com.dwarfeng.dct.stack.service.DataCodingQosService;
import com.dwarfeng.dct.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.List;

/**
 * 数据编码 QoS 服务实现测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class DataCodingQosServiceImplTest {

    @Autowired
    private DataCodingQosService dataCodingQosService;

    @Test
    public void testListHandlerNamesContainsDefaultBean() throws ServiceException {
        List<String> names = dataCodingQosService.listHandlerNames();
        Assert.assertEquals(1, names.size());
        Assert.assertEquals(SimpleConfiguration.BEAN_NAME_DATA_CODING_HANDLER, names.get(0));
    }

    @Test
    public void testQosServiceNullHandlerNameRoundTrip() throws ServiceException {
        GeneralData generalData = GeneralDataUtil.newInstance(
                new LongIdKey(12450L), "foobar", Instant.ofEpochMilli(724608000000L).plusNanos(123456L)
        );

        String encoded = dataCodingQosService.encode(null, generalData);
        Data decoded = dataCodingQosService.decode(null, encoded);

        Assert.assertEquals(generalData.getHappenedDate(), decoded.getHappenedDate());
        Assert.assertEquals(generalData.getHappenedDateNanoOffset(), decoded.getHappenedDateNanoOffset());
    }
}
