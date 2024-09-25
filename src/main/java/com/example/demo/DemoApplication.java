package com.example.demo;

import com.example.demo.member.Member;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        Member aaron = Member.builder()
                .name("Aaron")
                .email("aaron@example.com")
                .build();
        Member baron = Member.builder()
//              .name("Unnamed")       // 빌더 사용 시 .name() 에 어떤값도 넣지 않으면 name 필드에는 "Unnamed" 가 설정된다.
//              .email("Undefined")     // 빌더 사용 시 .email() 에 어떤값도 넣지 않으면 name 필드에는 "Undefined" 가 설정된다.
                .build();

        System.out.println("---");
        System.out.println(aaron);              // 객체
        System.out.println(aaron.toString());   // 객체
        System.out.println(aaron.getName());    // 필드 중 name
//      System.out.println(aaron.name);         // 필드 중 name (접근제어자 private)

        System.out.println("---");
        System.out.println(baron);              // 객체
        System.out.println(baron.toString());   // 객체
        System.out.println(baron.getName());    // 필드 중 name
//      System.out.println(baron.name);         // 필드 중 name (접근제어자 private)
    }
}
