package com.dwarfeng.dct.util;

import com.dwarfeng.dct.handler.FlatDataCodec;
import com.dwarfeng.dct.handler.ValueCodingHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class FlatDataCodecUtilTest {

    @Autowired
    private FlatDataCodec flatDataCodec;

    @Autowired
    private ValueCodingHandler valueCodingHandler;

    @Test
    public void testCheckCodecValid() {
        FlatDataCodecUtil.checkCodec(flatDataCodec);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckCodecNull() {
        FlatDataCodecUtil.checkCodec(null);
    }

    @Test
    public void testCheckValueCodingHandlerValid() {
        FlatDataCodecUtil.checkValueCodingHandler(valueCodingHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckValueCodingHandlerNull() {
        FlatDataCodecUtil.checkValueCodingHandler(null);
    }
}
