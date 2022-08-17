package com.wsss.frame.db.rockets.pressure;

import com.wsss.frame.db.rockets.first.RocksJavaTest;
import com.wsss.frame.db.rockets.utils.RocketDbUtils;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleTest {
    private static final String dbPath = SimpleTest.class.getResource("").getPath() + "rockets.db";
    private static final byte[] MIN_MSG = "1".getBytes();
    private static final byte[] MAX_MSG = "1234567890123452789012345678901234567890123456789012345678901234567890123456789012345678901234567890".getBytes();
    static {
        RocksDB.loadLibrary();
    }

    public RocksDB rocksDB;

    private void createDb() throws Exception {
        Options options = new Options();
        options.setCreateIfMissing(true);
        System.out.println(dbPath);
        // 文件不存在，则先创建文件
        if (!Files.isSymbolicLink(Paths.get(dbPath))) Files.createDirectories(Paths.get(dbPath));
        rocksDB = RocksDB.open(options, dbPath);
    }

    private void write(int size,byte[] msg) throws RocksDBException {
        for(int i=0;i<size;i++) {
            rocksDB.put(String.valueOf(i).getBytes(), msg);
        }
    }

    public static void main(String[] args) throws Exception {
        SimpleTest simpleTest = new SimpleTest();
        simpleTest.createDb();

//        RocketDbUtils.print(simpleTest.rocksDB);
        long start = System.currentTimeMillis();
        simpleTest.write(200000,MAX_MSG);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
