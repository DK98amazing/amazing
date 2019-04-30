package com.amazing.test;

import java.util.Arrays;
import java.util.List;

/**
 * TestSort.
 *
 * @author liguoyao
 */
public class TestSort {
    private static Integer[] array = { 1, 4, 7, 9, 5, 6, 0, 0, 3, 2, 8 };
    private static Integer[] array1 = { 1, 4, 7, 9, 5, 6, 0, 0, 3, 2, 8 };
    private static Integer[] array2 = { 1, 4, 7, 9, 5, 6, 0, 0, 3, 2, 8 };
    private static Integer[] array3 = { 1, 4, 7, 9, 5, 6, 0, 0, 3, 2, 8 };
    private static Integer[] array4 = { 19, 38, 7, 36, 5, 5, 3, 2, 1, 0, 56 };

    public static void main(String[] args) {
        System.out.println("冒泡排序 O(N2)：" + sortByMaopao(array));
        System.err.println("快速排序 O(N2)：" + sortByQuick(array1, 0, array1.length - 1));
        System.out.println("选择排序 O(N*logN)：" + sortBySelect(array2));
        System.err.println("插入排序 O(N2)：" + sortByInsert(array3));
        System.out.println("堆排序 O(N*logN)：" + sortByHeap(array4));
    }

    public static List<Integer> sortByMaopao(Integer[] array) {
        int size = array.length;
        int temp = 0;
        for (int i = 0; i < size - 1; i++) {
            for (int j = size - 1; j > i; j--) {
                if (array[j] < array[j - 1]) {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
        return Arrays.asList(array);
    }

    private static int getMiddle(Integer[] array, int low, int high) {
        int temp = array[low];
        while (low < high) {
            while (low < high && array[high] >= temp) {
                high--;
            }
            if (low < high) {
                array[low] = array[high];
                low++;
            }

            while (low < high && array[low] <= temp) {
                low++;
            }
            if (low < high) {
                array[high] = array[low];
                high--;
            }

        }
        array[low] = temp;
        return low;
    }

    public static List<Integer> sortByQuick(Integer[] array, int low, int high) {
        if (array.length > 0) {
            if (low < high) {
                int middle = getMiddle(array, low, high);
                sortByQuick(array, low, middle - 1);
                sortByQuick(array, middle + 1, high);
            }
        }
        return Arrays.asList(array);
    }

    private static List<Integer> sortBySelect(Integer[] array) {
        int size = array.length;
        int temp;
        for (int i = 0; i < size; i++) {
            int k = i;
            for (int j = i + 1; j < size; j++) {
                if (array[j] < array[k]) {
                    k = j;
                }
            }
            temp = array[i];
            array[i] = array[k];
            array[k] = temp;
        }
        return Arrays.asList(array);
    }

    public static List<Integer> sortByInsert(Integer[] array) {
        int size = array.length;
        int temp;
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                } else {         //不需要交换
                    break;
                }
            }
        }
        return Arrays.asList(array);
    }

    public static List<Integer> sortByHeap(Integer[] array) {
        if (array == null || array.length == 1)
            return null;
        buildArrayToHeap(array); //将数组元素转化为大顶堆/小顶堆
        for (int i = array.length - 1; i >= 1; i--) {
            // 经过上面的一些列操作，目前array[0]是当前数组里最大的元素，需要和末尾的元素交换，然后拿出最大的元素
            swap(array, 0, i);
            /**
             * 交换完后，下次遍历的时候，就应该跳过最后一个元素，也就是最大的那个
             * 值，然后开始重新构建最大堆堆的大小就减去1，然后从0的位置开始最大堆
             */
            //			buildMaxHeap(array, i, 0);
            buildMaxHeap(array, i, 0);
        }
        return Arrays.asList(array);
    }

    // 构建堆
    private static void buildArrayToHeap(Integer[] array) {
        if (array == null || array.length == 1)
            return;
        //递推公式就是 int root = 2*i, int left = 2*i+1, int right = 2*i+2;
        int cursor = array.length / 2;
        for (int i = cursor; i >= 0; i--) { // 这样for循环下，就可以第一次排序完成
            buildMaxHeap(array, array.length, i);
            //            buildMinHeap(array, array.length, i);
        }
    }

    //大顶堆
    private static void buildMaxHeap(Integer[] array, int heapSize, int index) {
        int left = index * 2 + 1; // 左子节点
        int right = index * 2 + 2; // 右子节点
        int maxValue = index; // 暂时定在Index的位置就是最大值
        // 如果左子节点的值，比当前最大的值大，就把最大值的位置换成左子节点的位置
        if (left < heapSize && array[left] > array[maxValue]) {
            maxValue = left;
        }
        // 如果右子节点的值，比当前最大的值大，就把最大值的位置换成右子节点的位置
        if (right < heapSize && array[right] > array[maxValue]) {
            maxValue = right;
        }
        // 如果不相等说明，这个子节点的值有比自己大的，位置发生了交换了位置
        if (maxValue != index) {
            swap(array, index, maxValue); // 就要交换位置元素
            // 交换完位置后还需要判断子节点是否打破了最大堆的性质。最大堆性质：两个子节点都比父节点小。
            buildMaxHeap(array, heapSize, maxValue);
        }
    }

    //小顶堆
    private static void buildMinHeap(Integer[] array, int heapSieze, int index) {
        int left = index * 2 + 1; // 左子节点
        int right = index * 2 + 2; // 右子节点
        int maxValue = index; // 暂时定在Index的位置就是最小值
        // 如果左子节点的值，比当前最小的值小，就把最小值的位置换成左子节点的位置
        if (left < heapSieze && array[left] < array[maxValue]) {
            maxValue = left;
        }
        // 如果右子节点的值，比当前最小的值小，就把最小值的位置换成左子节点的位置
        if (right < heapSieze && array[right] < array[maxValue]) {
            maxValue = right;
        }
        // 如果不相等说明这个子节点的值有比自己小的，位置发生了交换了位置
        if (maxValue != index) {
            swap(array, index, maxValue); // 就要交换位置元素
            // 交换完位置后还需要判断子节点是否打破了最小堆的性质。最小性质：两个子节点都比父节点大。
            buildMinHeap(array, heapSieze, maxValue);
        }
    }

    // 数组元素交换
    private static void swap(Integer[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
