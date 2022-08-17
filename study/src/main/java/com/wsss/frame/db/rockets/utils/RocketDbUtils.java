package com.wsss.frame.db.rockets.utils;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;

public class RocketDbUtils {
    public static void print(RocksDB rocksDB) throws Exception {
        /**
         *  打印全部[key - value]
         */
        RocksIterator iter = rocksDB.newIterator();
        System.out.println("-------------------------start------------------------------");
        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
            System.out.println("iter key:" + new String(iter.key()) + ", iter value:" + new String(iter.value()));
        }
        System.out.println("---------------------------end----------------------------");
    }

    public static void print(RocksDB rocksDB, ColumnFamilyHandle columnFamilyHandle) throws Exception {
        /**
         *  打印全部[key - value]
         */
        RocksIterator iter = rocksDB.newIterator(columnFamilyHandle);
        System.out.println("-------------------------start------------------------------");
        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
            System.out.println("iter key:" + new String(iter.key()) + ", iter value:" + new String(iter.value()));
        }
        System.out.println("---------------------------end----------------------------");
    }
}
