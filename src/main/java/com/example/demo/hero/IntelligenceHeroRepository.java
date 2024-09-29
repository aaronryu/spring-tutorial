package com.example.demo.hero;

import com.example.demo.hero.common.HeroRepository;

import java.util.Map;

public class IntelligenceHeroRepository implements HeroRepository<IntelligenceHero, Integer> {
    public static Map<Integer, IntelligenceHero> repository = Map.of(3, new IntelligenceHero());

    public IntelligenceHero findById(Integer id) {
        return repository.get(id);
    }
}
