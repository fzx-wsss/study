package com.wsss.frame.db.rockets.pressure;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadTest {
    private static final String dbPath = SimpleTest.class.getResource("").getPath() + "rockets.db";
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

    private String readLast() {
        RocksIterator iterator = rocksDB.newIterator();
        iterator.seekToLast();
        return new String(iterator.key());
    }


    public static void main(String[] args) throws Exception {
        ReadTest simpleTest = new ReadTest();
        simpleTest.createDb();

//        RocketDbUtils.print(simpleTest.rocksDB);
        long start = System.currentTimeMillis();
        System.out.println(simpleTest.readLast());
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
