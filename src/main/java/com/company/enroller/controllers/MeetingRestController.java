package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.MeetingResponse;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    ParticipantService participantService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {

        Collection<MeetingResponse> meetings = meetingService.getAll();
        return new ResponseEntity<Collection<MeetingResponse>>(meetings, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
        if (meetingService.findByName(meeting.getName()) != null) {
            return new ResponseEntity(
                    "Unable to create. A meeting with name " + meeting.getName() + " already exist.",
                    HttpStatus.CONFLICT);
        }

        meetingService.add(meeting);
        return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeMeeting(@PathVariable long id) {
        Meeting foundMeeting = meetingService.findById(id);
        if (foundMeeting == null) {
            return new ResponseEntity(
                    "Unable to remove. A meeting with id " + id + " does not exist.",
                    HttpStatus.NOT_FOUND);
        }

        meetingService.removeMeeting(foundMeeting);
        return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipant(@PathVariable long id, @RequestParam String login) {

        Meeting foundMeeting = meetingService.findById(id);
        if (foundMeeting == null) {
            return new ResponseEntity(
                    "Unable to find meeting with id " + id + ".",
                    HttpStatus.NOT_FOUND);
        }

        Participant foundParticipant = participantService.findByLogin(login);
        if (foundParticipant == null) {
            return new ResponseEntity(
                    "Unable to find user with login " + login + ".",
                    HttpStatus.NOT_FOUND);
        }

        meetingService.addParticipant(foundMeeting, foundParticipant);
        return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}/participants", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeParticipant(@PathVariable long id, @RequestParam String login) {

        Meeting foundMeeting = meetingService.findById(id);
        if (foundMeeting == null) {
            return new ResponseEntity(
                    "Unable to find meeting with id " + id + ".",
                    HttpStatus.NOT_FOUND);
        }

        Participant foundParticipant = participantService.findByLogin(login);
        if (foundParticipant == null) {
            return new ResponseEntity(
                    "Unable to find user with login " + login + ".",
                    HttpStatus.NOT_FOUND);
        }

        meetingService.removeParticipant(foundMeeting, foundParticipant);
        return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.CREATED);
    }
}
