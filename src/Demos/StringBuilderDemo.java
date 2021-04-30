package Demos;

import java.lang.*;

public class StringBuilderDemo {

    public static void main(String[] args) {

        int num = 123456;
        String NUM = Integer.toString(num);
        StringBuilder str = new StringBuilder(NUM);
        System.out.println("string = " + str);
        System.out.println("Length is: " + NUM.length());
        // insert character value at offset 8
        str.insert(3, '.');

        // prints StringBuilder after insertion
        System.out.print("After insertion = ");
        System.out.println(str.toString());
    }
}
