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

    public static void main(String[] args) {
        System.out.println("冒泡排序：" + sortByMaopao(array));
        System.err.println("快速排序：" + sortByQuick(array1, 0, array1.length - 1));
        System.out.println("选择排序：" + sortBySelect(array2));
        System.err.println("插入排序：" + sortByInsert(array3));
    }

    private static List<Integer> sortByMaopao(Integer[] array) {
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
            array[low] = array[high];
            while (low < high && array[low] <= temp) {
                low++;
            }
            array[high] = array[low];
        }
        array[low] = temp;
        return low;
    }

    private static List<Integer> sortByQuick(Integer[] array, int low, int high) {
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

    private static List<Integer> sortByInsert(Integer[] array) {
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
}
