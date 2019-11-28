package org.jutils.jprocesses.main;

import java.util.List;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;

public class JProcessesMain {
    public static void main(String[] args) {
        List<ProcessInfo> processesList = JProcesses.getProcessList();
    
        for (final ProcessInfo processInfo : processesList) {
            System.out.println("Process PID: " + processInfo.getPid());
            System.out.println("Process Name: " + processInfo.getName());
            System.out.println("Process Time: " + processInfo.getTime());
            System.out.println("User: " + processInfo.getUser());
            System.out.println("Virtual Memory: " + processInfo.getVirtualMemory());
            System.out.println("Physical Memory: " + processInfo.getPhysicalMemory());
            System.out.println("CPU usage: " + processInfo.getCpuUsage());
            System.out.println("Start Time: " + processInfo.getStartTime());
            System.out.println("Priority: " + processInfo.getPriority());
            System.out.println("Full command: " + processInfo.getCommand());
            System.out.println("------------------");
        }
    }
}
