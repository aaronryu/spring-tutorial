package com.example.demo.principles.concretes;

import com.example.demo.principles.interfaces.Soup;

public class SinSoup implements Soup {
    public void input() {
        System.out.println("신라면 스프 넣기");
    }

    public void onion() {
        System.out.println("파 넣기");
    }

    public void egg() {
    }
}
