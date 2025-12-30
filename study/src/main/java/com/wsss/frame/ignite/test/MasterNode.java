package com.wsss.frame.ignite.test;

import com.wsss.frame.ignite.model.LogEntry;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class MasterNode {
    private static final String CACHE_NAME = "operationLogs";
    private Ignite ignite;
    private IgniteCache<String, LogEntry> logCache;
    private String nodeId;
    
    public MasterNode() {
        this.nodeId = "MASTER-" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    public void start() {
        System.out.println("启动主节点: " + nodeId);
        
        // 配置Ignite节点
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("MasterNode-" + nodeId);
        
        // 配置集群发现
        TcpDiscoverySpi discoSpi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47509"));
        discoSpi.setIpFinder(ipFinder);
        cfg.setDiscoverySpi(discoSpi);
        
        // 启动节点
        ignite = Ignition.start(cfg);
        
        // 获取缓存
        logCache = ignite.getOrCreateCache(CACHE_NAME);
        
        System.out.println("主节点启动完成，等待操作输入...");
        handleUserInput();
    }
    
    private void handleUserInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入操作 (格式: 操作类型|数据内容) 或 'quit' 退出: ");
            String input = scanner.nextLine();
            
            if ("quit".equalsIgnoreCase(input.trim())) {
                System.out.println("主节点关闭中...");
                stop();
                break;
            }
            
            if (input.contains("|")) {
                String[] parts = input.split("\\|", 2);
                String operation = parts[0].trim();
                String data = parts[1].trim();
                
                // 创建日志条目
                String logId = "LOG-" + System.currentTimeMillis();
                LogEntry logEntry = new LogEntry(logId, operation, data, nodeId);
                
                // 存储到Ignite缓存
                logCache.put(logId, logEntry);
                System.out.println("已记录操作: " + logEntry);
            } else {
                System.out.println("输入格式错误，请使用 '操作类型|数据内容' 格式");
            }
        }
    }
    
    public void stop() {
        if (ignite != null) {
            Ignition.stop(ignite.name(), true);
        }
    }
    
    public static void main(String[] args) {
        MasterNode master = new MasterNode();
        master.start();
    }
}
