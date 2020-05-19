package com.sejong.eatnow.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejong.eatnow.service.LobyService;
import com.sejong.eatnow.web.dto.LobyRequestDto;
import com.sejong.eatnow.web.dto.LocationDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = LobyController.class)
public class LobyControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LobyService lobyService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void cors_문제없이_로비등록() throws Exception{
        //given
        LocationDto locationDto = new LocationDto(111L,23L);
        LobyRequestDto lobyDto = LobyRequestDto.builder()
                                    .title("쌀국수파티")
                                    .openLink("open://11-c")
                                    .hostName("김씨")
                                    .meetingDate(LocalDate.of(2020,5,19))
                                    .locationDto(locationDto)
                                    .build();

        //when,then
        mvc.perform(
                post("/loby/insert")
                        .contentType("application/json;charset=utf-8;")
                        .content(objectMapper.writeValueAsString(lobyDto)))
                .andExpect(status().isOk());

    }
}
