package org.example;

import java.util.List;
import org.example.Std;

public class Main {

    public static void main(String[] args) {
        Std student = new Std();

        List<Std> students = student.findAll();
        System.out.println(students);

        Std s1 = Std.builder().age(20)
               .name("Petruk")
               .groupId(2)
                .build();

        Std s2 = Std.builder()
                .age(22)
               .name("Romanyuk")
                .groupId(1)
                .build();

        student.save(s1);
        student.save(s2);

        List<Std> students1 = student.findAll();
        System.out.println(students1);

    }
}
