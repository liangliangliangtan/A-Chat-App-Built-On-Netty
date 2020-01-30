package com.example.mychatappnetty.util;

import com.example.mychatappnetty.MyChatappNettyApplicationTests;
import com.github.wujun234.uid.UidGenerator;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * @author tan3
 * @ClassName UIDGeneratorTest.java
 * @Description Test BAIDU distributed id generator based on Snowflake algorithm
 * @createTime 2019 -  11 - 28 - 11 : 28
 */
public class UIDGeneratorTest  extends MyChatappNettyApplicationTests {


    @Resource
    private UidGenerator cachedUidGenerator;


    @Test
    public void testSerialGenerate() {
        // Generate UID
        long uid = cachedUidGenerator.getUID();
        // Parse UID into [Timestamp, WorkerId, Sequence]
        // {"UID":"204467135840256","timestamp":"2019-11-28 11:39:42","workerId":"0","sequence":"0"}
        System.out.println(cachedUidGenerator.parseUID(uid));
    }

}
