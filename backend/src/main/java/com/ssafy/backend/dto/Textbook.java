package com.ssafy.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Textbook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int textbookId;

    private String textbookUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "broadcastId")
    private Broadcast broadcast;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mattermostId")
    private Mattermost mattermost;
}
