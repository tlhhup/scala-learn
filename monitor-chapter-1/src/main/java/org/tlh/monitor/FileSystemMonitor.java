package org.tlh.monitor;

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxy;
import org.tlh.monitor.sigar.SigarFactory;

/**
 * <br>
 * Created by hu ping on 8/13/2019
 * <p>
 */
public class FileSystemMonitor {

    public static void main(String[] args) throws SigarException {
        SigarProxy sigar = SigarFactory.newSigar();
        FileSystem[] fileSystemList = sigar.getFileSystemList();
        for (FileSystem fileSystem : fileSystemList) {
            System.out.println(fileSystem);
        }
    }

}
