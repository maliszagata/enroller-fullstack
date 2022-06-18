package com.company.enroller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "participant")
public class Participant {

    @Id
    private String login;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "meeting_participant", joinColumns = {
            @JoinColumn(name = "participant_login")}, inverseJoinColumns = {@JoinColumn(name = "meeting_id")})
    Set<Meeting> meetings = new HashSet<>();

}
