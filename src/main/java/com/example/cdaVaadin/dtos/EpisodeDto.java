package com.example.cdaVaadin.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EpisodeDto {

    private String episodeNumber;
    private boolean watched;
    private boolean downloaded;

}
