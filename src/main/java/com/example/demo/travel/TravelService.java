package com.example.demo.travel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TravelService {
    public void reservation() {
        Database database = new Database();
        database.connect(null, "9876");
//      database.connect("user", null);
//      database.connect("user", "9876");
//      database.connect("admin", "1234");

        database.save("항공권");
        database.save("리조트");
        database.save("수영장");

        log.info("여행 패키지 예약 완료 (항공권 + 리조트 + 수영장)");
    }
}
