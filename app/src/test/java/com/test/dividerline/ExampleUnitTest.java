package com.test.dividerline;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void sf() {
        ClientHostLinks test=new ClientHostLinks();
        System.out.println(test.vtaInfoUrl());
        System.out.println(test.locationInfoUrl());
        System.out.println(test.historyLocationInfoUrl());
        System.out.println(test.dspRequestUrl());
        System.out.println(test.unionRequestUrl());
        System.out.println(test.sdkCommonReportUrl());
        System.out.println(test.hbaseLinkUrl());
        System.out.println(test.sdkRequestReportUrl());
        System.out.println(test.sdkReturnReportUrl());
        System.out.println(test.sdkShowReportUrl());
        System.out.println(test.sdkClickReportUrl());
        System.out.println(test.pollingUrl());
        System.out.println(test.extInfoUrl());
        System.out.println(test.appListUrl());
    }
}