package org.tlh.monitor;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.tlh.monitor.sigar.SigarFactory;

/**
 * <br>
 * Created by hu ping on 8/13/2019
 * <p>
 */
public class CpuMonitor {

    public static void main(String[] args) throws SigarException {
        SigarProxy sigar = SigarFactory.newSigar();
        CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
        CpuInfo cpuInfo = cpuInfoList[0];
        System.out.println("vendor:\t"+cpuInfo.getVendor());
        System.out.println("model:\t"+cpuInfo.getModel());
        System.out.println("maxGhz:\t"+cpuInfo.getMhz());
        System.out.println("cpuNum:\t"+cpuInfoList.length);
        System.out.println("cacheSize:\t"+cpuInfo.getCacheSize());
        CpuPerc cpuPerc = sigar.getCpuPerc();
        System.out.println(cpuPerc);
    }

}
