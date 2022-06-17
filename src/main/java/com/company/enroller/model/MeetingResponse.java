package com.company.enroller.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
public class MeetingResponse {

    private long id;

    private String name;

    private String description;

    Set<String> participants = new HashSet<>();
}
