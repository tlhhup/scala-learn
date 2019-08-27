package org.tlh.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.Date;

/**
 * <br>
 * Created by hu ping on 8/13/2019
 * <p>
 */
public class JvmInfo {

    public static void main(String[] args) throws Exception{
        //获取操作系统信息
        OperatingSystemMXBean a = ManagementFactory.getOperatingSystemMXBean();
        //名称、版本、可用的处理器、负载
        System.out.println(a.getName());
        System.out.println(a.getVersion());
        System.out.println(a.getArch());
        System.out.println(a.getAvailableProcessors());
        System.out.println(a.getSystemLoadAverage());

        //jvm的运行时
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        //输入参数
        System.out.println(runtimeMXBean.getInputArguments());
        //jvm启动时间
        System.out.println(new Date(runtimeMXBean.getStartTime()));
        Thread.sleep(5000);
        //jvm上线时间,单位为毫秒
        System.out.println(runtimeMXBean.getUptime());
    }

}
