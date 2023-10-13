package com.zhou;

public class A {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 判断特殊情况
        int len=m+n;
        if(len==0){
            return;
        }
        for (int i = 0; i < n; i++) {
            insert(nums1,m,nums2[i],i);
        }

    }

    private void insert(int[] nums1, int m, int num,int n) {
        int index=0;
       while (index<m+n){
            if(num<nums1[index]){
                break;
            }
            index++;
        }
        move(nums1,m,index,n);
       nums1[index]=num;
    }

    private void move(int[] nums1, int m, int index,int n ) {
        for (int i = m-1+n; i >=index; i--) {
            nums1[i+1]=nums1[i];
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.class.path"));
    }

}
