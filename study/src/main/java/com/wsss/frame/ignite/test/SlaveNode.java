package com.wsss.frame.ignite.test;

import com.wsss.frame.ignite.model.LogEntry;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.CacheEvent;
import org.apache.ignite.events.EventType;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.lang.IgnitePredicate;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

public class SlaveNode implements Serializable {
    private static final String CACHE_NAME = "operationLogs";
    private Ignite ignite;
    private IgniteCache<String, LogEntry> logCache;
    private String nodeId;
    
    public SlaveNode() {
        this.nodeId = "SLAVE-" + UUID.randomUUID().toString().substring(0, 8);
    }
    
    public void start() {
        System.out.println("启动从节点: " + nodeId);
        
        // 配置Ignite节点
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setIgniteInstanceName("SlaveNode-" + nodeId);
        
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
        
        // 订阅缓存事件
        subscribeToEvents();
        
        System.out.println("从节点启动完成，开始监听操作日志...");
        
        // 保持运行
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void subscribeToEvents() {
        // 创建事件监听器
        IgnitePredicate<CacheEvent> remoteListen = new IgnitePredicate<CacheEvent>() {
            @Override
            public boolean apply(CacheEvent event) {
                // 获取变更的数据
                LogEntry logEntry = logCache.get(event.key().toString());
                System.out.println(event);
                if (logEntry != null) {
                    System.out.println("IgnitePredicate [" + nodeId + "] 接收到同步日志: " + logEntry);
                    // 这里可以添加实际的日志处理逻辑
                    processLogEntry(logEntry);
                }
                return true; // 继续监听
            }
        };

        IgniteBiPredicate<UUID,CacheEvent> localListen = new IgniteBiPredicate<UUID,CacheEvent>() {
            @Override
            public boolean apply(UUID uuid, CacheEvent cacheEvent) {
                // 获取变更的数据
                LogEntry logEntry = logCache.get(cacheEvent.key().toString());
                System.out.println(cacheEvent);
                if (logEntry != null) {
                    System.out.println("IgniteBiPredicate "+uuid+" [" + nodeId + "] 接收到同步日志: " + logEntry);
                    // 这里可以添加实际的日志处理逻辑
                    processLogEntry(logEntry);
                }
                return true; // 继续监听
            }
        };
        
        // 订阅创建和更新事件
        ignite.events().remoteListen(
                localListen,
                remoteListen,
            EventType.EVT_CACHE_OBJECT_PUT,
            EventType.EVT_CACHE_OBJECT_REMOVED
        );
    }
    
    private void processLogEntry(LogEntry logEntry) {
        // 模拟日志处理逻辑
        System.out.println("[" + nodeId + "] 处理日志 - 操作: " + logEntry.getOperation() + 
                          ", 数据: " + logEntry.getData());
        
        // 实际应用中可以写入本地文件、数据库或其他存储系统
        // 例如: writeToLocalFile(logEntry);
    }
    
    public void stop() {
        if (ignite != null) {
            Ignition.stop(ignite.name(), true);
        }
    }
    
    public static void main(String[] args) {
        SlaveNode slave = new SlaveNode();
        slave.start();
    }
}
