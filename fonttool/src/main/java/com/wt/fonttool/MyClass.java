package com.wt.fonttool;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MyClass {
    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("fonttool/src/main/text.txt");
        char[] c = new char[1];
        int read;

        Set<String> r = new HashSet<>();

        while ((read = reader.read(c)) != -1) {
            String s = new String(c);
            if (!s.equals("\n"))
                r.add(new String(c));
        }

        Object[] objects = r.toArray();
        for (Object j : objects) {

            System.out.print(j.toString());
        }


    }
}
