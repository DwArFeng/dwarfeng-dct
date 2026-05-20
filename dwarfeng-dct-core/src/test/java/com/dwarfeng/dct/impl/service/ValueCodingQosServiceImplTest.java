package com.dwarfeng.dct.impl.service;

import com.dwarfeng.dct.node.configuration.SimpleConfiguration;
import com.dwarfeng.dct.stack.service.ValueCodingQosService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 值编码 QoS 服务实现测试。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class ValueCodingQosServiceImplTest {

    @Autowired
    private ValueCodingQosService valueCodingQosService;

    @Test
    public void testListHandlerNamesContainsDefaultBean() throws ServiceException {
        List<String> names = valueCodingQosService.listHandlerNames();
        Assert.assertEquals(1, names.size());
        Assert.assertEquals(SimpleConfiguration.BEAN_NAME_VALUE_CODING_HANDLER, names.get(0));
    }

    @Test
    public void testQosServiceNullHandlerNameRoundTrip() throws ServiceException {
        String encoded = valueCodingQosService.encode(null, "foobar");
        Object decoded = valueCodingQosService.decode(null, encoded);
        Assert.assertEquals("foobar", decoded);
    }
}
