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
        System.out.println("斐波那契查找，请输入你要查找的数：");
        int num2 = input.nextInt();
        TestSort.sortByHeap(array);
        System.err.println(Arrays.asList(array));
        int result2 = fibSearch(array, array.length, num2);
        if (result == -1) {
            System.out.println("你要查找的数不存在……");
        } else {
            System.out.println("你要查找的数存在，在数组中的位置是：" + (result2 + 1));
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

    public static int fibSearch(Integer[] arr, int n, int key) {
        int low = 1;  //记录从1开始
        int high = n;     //high不用等于fib(k)-1，效果相同
        int mid;

        int k = 0;
        while (n > fib(k) - 1)    //获取k值
            k++;
        Integer[] temp = new Integer[fib(k)];   //因为无法直接对原数组arr[]增加长度，所以定义一个新的数组
        System.arraycopy(arr, 0, temp, 0, arr.length); //采用System.arraycopy()进行数组间的赋值
        for (int i = n + 1; i <= fib(k) - 1; i++)    //对数组中新增的位置进行赋值
            temp[i] = temp[n];

        while (low <= high) {
            mid = low + fib(k - 1) - 1;
            if (temp[mid] > key) {
                high = mid - 1;
                k = k - 1;  //对应上图中的左段，长度F[k-1]-1
            } else if (temp[mid] < key) {
                low = mid + 1;
                k = k - 2;  //对应上图中的右端，长度F[k-2]-1
            } else {
                if (mid <= n)
                    return mid;
                else
                    return n;       //当mid位于新增的数组中时，返回n
            }
        }
        return 0;
    }

    private static int fib(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fib(n - 1) + fib(n - 2);
    }
}
