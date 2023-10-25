package com.wsss.frame.bit.map;

import org.junit.Test;
import org.roaringbitmap.FastAggregation;
import org.roaringbitmap.RoaringBitmap;

/**
 * @author GGBOOM
 * @description Roaring BitMap(高效压缩位图)测试
 * @createTime 2022/10/28 14:44
 */
public class RoaringBitMapTest {

    /**
     * 将值添加到容器中（将值设置为“true”），无论它是否已出现。
     * Java缺少本机无符号整数，但x参数被认为是无符号的。
     * 在位图中，数字按照Integer.compareUnsigned进行排序。我们订购的数字是0，1，…，2147483647，-2147483648，-2147473647，…，-1。
     * add(final int x)
     * 参数： x–整数值
     */
    @Test
    public void add() {
        RoaringBitmap rbm = new RoaringBitmap();
        rbm.add(1);
        rbm.add(4);
        rbm.add(100000000);
        System.out.println(rbm);
    }

    /**
     * 将值添加到容器中（将值设置为“true”），无论它是否已出现。
     * checkedAdd(final int x)
     * 参数： x–整数值
     * return: 如果添加的int尚未包含在位图中，则为true。否则为False
     */
    @Test
    public void checkAndadd() {
        RoaringBitmap rbm = RoaringBitmap.bitmapOf(1, 2, 3, 6, 1000, 100000000);
        boolean checkedAdd1 = rbm.checkedAdd(3);
        boolean checkedAdd2 = rbm.checkedAdd(4);
        // 查询该位图中存储的第几个值,从小到大排序
        System.out.println(checkedAdd1);
        System.out.println(checkedAdd2);
        System.out.println(rbm);
//        false
//        true
//        {1,2,3,4,6,1000,100000000}
    }

    /**
     * 检查是否包含该值，这相当于检查是否设置了相应的位（在BitSet类中获取）
     * 参数： x–整数值
     * return： 是否包括整数值。
     */
    @Test
    public void contains() {
        RoaringBitmap rbm = RoaringBitmap.bitmapOf(1, 2, 3, 6, 1000, 100000000);
        boolean contains1 = rbm.contains(3);
        boolean contains2 = rbm.contains(4);
        // 查询该位图中存储的第几个值,从小到大排序
        System.out.println(contains1);
        System.out.println(contains2);
    }

    /**
     * 查询该位图中存储的第几个值,从小到大排序
     * select(int j)方法中,
     * 参数：j代表的是位图值的索引
     * return：根据索引查到的值
     */
    @Test
    public void select() {
        RoaringBitmap rbm = RoaringBitmap.bitmapOf(1, 3, 2, 1000, 100000000);
        // 查询该位图中存储的第几个值,从小到大排序
        int valueOfIndex0 = rbm.select(0);
        int valueOfIndex1 = rbm.select(1);
        int valueOfIndex2 = rbm.select(2);
        int valueOfIndex3 = rbm.select(3);
        System.out.println(valueOfIndex0);
        System.out.println(valueOfIndex1);
        System.out.println(valueOfIndex2);
        System.out.println(valueOfIndex3);
//        1
//        2
//        3
//        1000
    }

    /**
     * 统计排名
     * rank(int x) ——> rankLong(int x)
     * Rank返回小于或等于x的整数数（Rank（无穷大）将是GetCardinality（））。如果提供最小值作为参数，此函数将返回1。如果提供小于最小值的值，则返回0。
     */
    @Test
    public void rank() {
        RoaringBitmap rbm = RoaringBitmap.bitmapOf(1, 2, 3, 6, 1000, 100000000);
        // 查询该位图中存储的第几个值,从小到大排序
        System.out.println(rbm.rank(0));
        System.out.println(rbm.rank(1));
        System.out.println(rbm.rank(2));
        System.out.println(rbm.rank(3));
        System.out.println(rbm.rank(4));
        System.out.println(rbm.rank(5));
        System.out.println(rbm.rank(6));
        System.out.println(rbm.rank(7));
        System.out.println(rbm.rank(2000));
        System.out.println(rbm.rank(3000));
        System.out.println(rbm.rank(6000));
        System.out.println(rbm.rank(111111111));
        System.out.println(rbm.rank(1000000));
    }


    /**
     * 检查范围是否与位图相交。
     * intersects(long minimum, long supremum)
     * 参数： minimum–范围的包含无符号下界 supermum–范围的唯一无符号上界
     * 注意：[上闭,下开)
     */
    @Test
    public void intersects() {
        RoaringBitmap rbm = RoaringBitmap.bitmapOf(1, 2, 3, 1000, 100000000);
        // 查询该位图中存储的第几个值,从小到大排序
        Boolean boolean1 = rbm.intersects(4L, 999L);
        Boolean boolean2 = rbm.intersects(4L, 1000L);
        Boolean boolean3 = rbm.intersects(4L, 1001L);
        Boolean boolean4 = rbm.intersects(3L, 999L);
        Boolean boolean5 = rbm.intersects(2000L, 100000000L);
        Boolean boolean6 = rbm.intersects(100000000L, 100000001L);
        System.out.println(boolean1);
        System.out.println(boolean2);
        System.out.println(boolean3);
        System.out.println(boolean4);
        System.out.println(boolean5);
        System.out.println(boolean6);
//        false
//        false
//        true
//        true
//        false
//        true
    }

    /**
     * 按位OR（联合）操作。参数中的位图不会被修改，只要提供的位图保持不变，此操作是线程安全的。
     * 如果您有2个以上的位图，请考虑使用FastAggregation类。
     * 参数： x1–第一个位图 x2–其他位图
     * return： 操作结果
     *
     * @see FastAggregation#or(RoaringBitmap...)
     * @see FastAggregation#horizontal_or(RoaringBitmap...)
     */
    @Test
    public void or() {
        RoaringBitmap rbm1 = RoaringBitmap.bitmapOf(1, 2, 3, 1000, 100000000);
        RoaringBitmap rbm2 = RoaringBitmap.bitmapOf(1, 2, 3, 4, 2000, 5000);
        RoaringBitmap or = RoaringBitmap.or(rbm1, rbm2);
        System.out.println(or);
    }


    /**
     * 按位AND（交叉）运算。参数中的位图不会被修改，只要提供的位图保持不变，此操作是线程安全的。
     * 如果您有2个以上的位图，请考虑使用FastAggregation类。
     * 参数： x1–第一个位图 x2–其他位图
     * return：操作结果
     *
     * @see FastAggregation#and(RoaringBitmap...)
     */
    @Test
    public void and() {
        RoaringBitmap rbm1 = RoaringBitmap.bitmapOf(1, 2, 3, 1000, 100000000);
        RoaringBitmap rbm2 = RoaringBitmap.bitmapOf(1, 2, 3, 4, 1000, 5000);
        RoaringBitmap and = RoaringBitmap.and(rbm1, rbm2);
        System.out.println(and);
    }

}

