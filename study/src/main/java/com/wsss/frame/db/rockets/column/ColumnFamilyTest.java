package com.wsss.frame.db.rockets.column;

import com.wsss.frame.db.rockets.pressure.SimpleTest;
import com.wsss.frame.db.rockets.utils.RocketDbUtils;
import org.rocksdb.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnFamilyTest {
    private static final String dbPath = ColumnFamilyTest.class.getResource("").getPath() + "rockets.db";
    static {
        RocksDB.loadLibrary();
    }

    public RocksDB rocksDB;
    public Map<String,ColumnFamilyHandle> columnFamilyHandleMap = new HashMap<>();

    private void createDb() throws Exception {
        Options options = new Options();
        options.setCreateIfMissing(true);
        System.out.println(dbPath);
        // 文件不存在，则先创建文件
        if (!Files.isSymbolicLink(Paths.get(dbPath))) Files.createDirectories(Paths.get(dbPath));

        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
        List<byte[]> cfs = RocksDB.listColumnFamilies(options, dbPath);
        if (cfs.size() > 0) {
            for (byte[] cf : cfs) {
                columnFamilyDescriptors.add(new ColumnFamilyDescriptor(cf, new ColumnFamilyOptions()));
            }
        } else {
            columnFamilyDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));
        }

        List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
        DBOptions dbOptions = new DBOptions();
        dbOptions.setCreateIfMissing(true);

        rocksDB = RocksDB.open(dbOptions, dbPath, columnFamilyDescriptors, columnFamilyHandles);
        for (int i = 0; i < columnFamilyDescriptors.size(); i++) {
            columnFamilyHandleMap.put(new String(columnFamilyDescriptors.get(i).columnFamilyName()),columnFamilyHandles.get(i));
        }
    }

    public static void main(String[] args) throws Exception {
        ColumnFamilyTest simpleTest = new ColumnFamilyTest();
        simpleTest.createDb();

        if(!simpleTest.columnFamilyHandleMap.containsKey("test1")) {
            ColumnFamilyHandle handle = simpleTest.rocksDB.createColumnFamily(new ColumnFamilyDescriptor("test1".getBytes()));
            simpleTest.columnFamilyHandleMap.put("test1",handle);
        }

        if(!simpleTest.columnFamilyHandleMap.containsKey("test2")) {
            ColumnFamilyHandle handle = simpleTest.rocksDB.createColumnFamily(new ColumnFamilyDescriptor("test2".getBytes()));
            simpleTest.columnFamilyHandleMap.put("test2",handle);
        }

        simpleTest.rocksDB.put(simpleTest.columnFamilyHandleMap.get("test1"), "1".getBytes(), "1".getBytes());
        simpleTest.rocksDB.put(simpleTest.columnFamilyHandleMap.get("test2"), "1".getBytes(), "2".getBytes());
        simpleTest.rocksDB.put(simpleTest.columnFamilyHandleMap.get("test2"), "2".getBytes(), "2".getBytes());
        simpleTest.rocksDB.put(simpleTest.columnFamilyHandleMap.get("default"), "3".getBytes(), "3".getBytes());

        RocketDbUtils.print(simpleTest.rocksDB,simpleTest.columnFamilyHandleMap.get("test1"));
        RocketDbUtils.print(simpleTest.rocksDB,simpleTest.columnFamilyHandleMap.get("test2"));
        RocketDbUtils.print(simpleTest.rocksDB);
    }
}
