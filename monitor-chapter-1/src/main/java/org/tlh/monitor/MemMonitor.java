package org.tlh.monitor;

import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.tlh.monitor.sigar.SigarFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * <br>
 * Created by hu ping on 8/13/2019
 * <p>
 */
public class MemMonitor {

    public static void main(String[] args) throws SigarException {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        //jvm内存
        System.out.println("totalMemory:\t" + totalMemory);
        System.out.println("freeMemory:\t" + freeMemory);
        System.out.println("usedMemory:\t" + usedMemory);
        //堆内存
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        long init = heapMemoryUsage.getInit();
        long max = heapMemoryUsage.getMax();
        long used = heapMemoryUsage.getUsed();
        long free = max - used;
        System.out.println(String.format("init:{%d}  max:{%d}  used:{%d}  free:{%d}", init, max, used, free));
        //非堆内存
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        init = nonHeapMemoryUsage.getInit();
        max = nonHeapMemoryUsage.getMax();
        used = nonHeapMemoryUsage.getUsed();
        free = max - used;
        System.out.println(String.format("init:{%d}  max:{%d}  used:{%d}  free:{%d}", init, max, used, free));

        //系统内存
        SigarProxy sigarProxy = SigarFactory.newSigar();
        Mem mem = sigarProxy.getMem();
        System.out.println(mem);
    }

}
