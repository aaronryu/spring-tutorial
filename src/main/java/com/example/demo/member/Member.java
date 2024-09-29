package com.example.demo.member;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    public static String MEMBER_TO_STRING_FORMAT = "Member(id=%s, name=%s, age=%s, email=%s)";

    private Integer id;
    private String name;
    private int age;
    private String email;

    public String toString() {
        return String.format(MEMBER_TO_STRING_FORMAT, this.id, this.name, this.age, this.email);
    }

    public static String toString(Member member) {
        return String.format(MEMBER_TO_STRING_FORMAT, member.id, member.name, member.age, member.email);
    }

    @AllArgsConstructor
    public static class Favorite {
        private List<String> favorites;
    }
}
