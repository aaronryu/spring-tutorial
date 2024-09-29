package com.example.demo;

import com.example.demo.member.Administrator;
import com.example.demo.member.Member;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        Member aaron = new Member();

        aaron.setId(1);                         // @Setter 클래스 단위로 적용하여 모든 필드 수정 가능
        aaron.setName("Aaron");                 // @Setter (클래스 단위)
        aaron.setAge(10);                       // @Setter (클래스 단위)
        aaron.setEmail("aaron@example.com");    // @Setter (클래스 단위)

        System.out.println("---");
        System.out.println(aaron);              // 객체
        System.out.println(aaron.toString());   // 객체

        System.out.println(aaron.getId());      // @Getter 클래스 단위로 적용하여 모든 필드 조회 가능
        System.out.println(aaron.getName());    // @Getter (클래스 단위)
        System.out.println(aaron.getAge());     // @Getter (클래스 단위)
        System.out.println(aaron.getEmail());   // @Getter (클래스 단위)
    }
}
