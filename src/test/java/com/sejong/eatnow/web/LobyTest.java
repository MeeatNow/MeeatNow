package com.sejong.eatnow.web;

import com.sejong.eatnow.domain.loby.Loby;
import com.sejong.eatnow.domain.loby.LobyRepository;
import com.sejong.eatnow.domain.location.Location;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log
@Commit
@NoArgsConstructor
public class LobyTest {

    @Autowired
    private LobyRepository repo;

    @Test
    public void insert_Loby() {
        Loby loby = Loby.builder()
                .title("toona party")
                .hostName("dongwon")
                .openLink("http://open-55")
                .location(Location.builder()
//                        .id(1L)  --> tx rollback-error도 이것때문에 생긴다.
                        .longitude(11.345)
                        .latitude(222.433)
                        .build())
//                .meetingDateTime(LocalDate.of(2020, 3, 2))
                .build();
        try {
            repo.saveAndFlush(loby);
            log.info("saved loby successfully.......");
        } catch (Exception e) {
            log.info("saving loby failed......" + e.getMessage());
        }
        모든로비_날짜시간_조회_테스트();
    }

    public void 모든로비_날짜시간_조회_테스트() {
        List<Loby> lobies = repo.findAllByDesc();
        for (Loby loby : lobies) {
            log.info("Id: " + loby.getId() + ", 날짜시간: " + loby.getMeetingDate());
        }
    }
}
