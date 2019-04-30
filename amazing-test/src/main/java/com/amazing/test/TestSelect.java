package com.amazing.test;

import java.util.Arrays;
import java.util.Scanner;

/**
 * TestSelect.
 *
 * @author liguoyao
 */
public class TestSelect {
    private static Integer[] array = { 1, 4, 7, 9, 5, 6, 0, 0, 3, 2, 8 };

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("二分查找，请输入你要查找的数：");
        int num = input.nextInt();
        TestSort.sortByHeap(array);
        System.err.println(Arrays.asList(array));
        int result = binarySearch(array, num);
        if (result == -1) {
            System.out.println("你要查找的数不存在……");
        } else {
            System.out.println("你要查找的数存在，在数组中的位置是：" + (result + 1));
        }
    }

    public static int binarySearch(Integer[] array, int target) {
        int low = 0;
        int upper = array.length - 1;
        while (low <= upper) {
            int mid = (upper + low) / 2;
            if (array[mid] < target) {
                low = mid + 1;
            } else if (array[mid] > target) {
                upper = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}
