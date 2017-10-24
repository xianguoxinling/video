package com.test.video;

public class TestOthers
{

    public static void main(String[] args)
    {
        String flv = "a.flv";
        String originalArray[] = flv.split("\\.");
        System.out.println(originalArray.length);
        System.out.println(originalArray[0]);
        System.out.println(originalArray[1]);
    }

}
