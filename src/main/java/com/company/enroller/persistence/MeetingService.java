package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.MeetingResponse;
import com.company.enroller.model.Participant;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component("meetingService")
public class MeetingService {

    DatabaseConnector connector;
    ParticipantService participantService;

    public MeetingService() {
        connector = DatabaseConnector.getInstance();
    }

    public Meeting findById(long id) {
        return (Meeting) connector.getSession().get(Meeting.class, id);
    }

    public Meeting findByName(String name) {
        String hql = "FROM Meeting where name = '" + name + "'";
        Query query = connector.getSession().createQuery(hql);
        return (Meeting) query.uniqueResult();
    }

    public Collection<MeetingResponse> getAll() {
        List<Meeting> list = connector.getSession().createQuery("select m from Meeting m").list();
        List<MeetingResponse> meetingResponses = list.stream().map(meeting -> new MeetingResponse(meeting.getId(), meeting.getName(), meeting.getDescription(),
                meeting.getParticipants().stream().map(Participant::getLogin).collect(Collectors.toSet()))).collect(Collectors.toList());
        return meetingResponses;
    }

    public Meeting add(Meeting meeting) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(meeting);
        transaction.commit();
        return meeting;
    }

    public Meeting addParticipant(Meeting meeting, Participant participant) {
        meeting.addParticipant(participant);

        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(meeting);
        transaction.commit();
        return meeting;
    }


    public Meeting removeParticipant(Meeting meeting, Participant participant) {
        meeting.removeParticipant(participant);

        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(meeting);
        transaction.commit();
        return meeting;
    }

    public Meeting removeMeeting(Meeting meeting) {

        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(meeting);
        transaction.commit();
        return meeting;
    }
}
