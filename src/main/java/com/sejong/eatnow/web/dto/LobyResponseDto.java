package com.sejong.eatnow.web.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sejong.eatnow.domain.location.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class LobyResponseDto {

    private Long id;
    private String title;
    private String hostName;
    private String openLink;
    private Double latitude;
    private Double longitude;
    private LocalDate meetingDate;

    @Builder
    public LobyResponseDto(Long id, String title, String hostName, String openLink,
                           Location location,
                           LocalDate meetingDate) {
        this.id = id;
        this.title = title;
        this.hostName = hostName;
        this.openLink = openLink;
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.meetingDate = meetingDate;
    }
}