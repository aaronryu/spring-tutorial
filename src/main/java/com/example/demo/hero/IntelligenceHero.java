package com.example.demo.hero;

import com.example.demo.hero.common.Hero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IntelligenceHero extends Hero {
    private final String name;

    @Override
    public void attack() {
        System.out.println("파이어볼");
    }

    @Override
    public void ultimate() {
        System.out.println("메테오");
    }
}