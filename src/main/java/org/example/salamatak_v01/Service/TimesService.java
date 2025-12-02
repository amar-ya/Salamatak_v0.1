package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiException;
import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.Times;
import org.example.salamatak_v01.Model.VisitRecords;
import org.example.salamatak_v01.Repository.DoctorsRepository;
import org.example.salamatak_v01.Repository.PatientsRepository;
import org.example.salamatak_v01.Repository.TimesRepository;
import org.example.salamatak_v01.Repository.VisitRecordsRepository;
import org.example.salamatak_v01.WhatsappConfig.UltraMsgProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimesService
{
    private final TimesRepository timesRepository;
    private final PatientsRepository patientsRepository;
    private final VisitRecordsService visitRecordsService;
    private final VisitRecordsRepository visitRecordsRepository;
    private final UltraMsgProperties ultraMsgProperties;
    private final RestTemplate restTemplate = new RestTemplate();


    private void sendWhatsappMessage(String to, String body) {
        String url = ultraMsgProperties.getApiBaseUrl()
                + "/" + ultraMsgProperties.getInstanceId()
                + "/messages/chat";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("token", ultraMsgProperties.getToken());
        formData.add("to", to);    // must be like 9665XXXXXXXX
        formData.add("body", body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(formData, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        System.out.println("UltraMsg response: " + response.getStatusCode()
                + " body: " + response.getBody());
    }

    public List<Times> getAll(){
        return timesRepository.findAll();
    }

    public void addTimes(Integer doctor_id, Times times){
        Integer workingHours = timesRepository.findTimesCountByDoctor_id(doctor_id);
        if(workingHours>= 8){
            throw new ApiException("your working schedule is full");
        }else{
            times.setDoctor_id(doctor_id);
            timesRepository.save(times);
        }
    }

    public void updateTimes(Integer id, Integer doctor_id, Times times){
        Doctors doctor = timesRepository.findDoctorByTime(doctor_id);
        Times session = timesRepository.findTimesById(id);
        if (doctor.getId().equals(session.getDoctor_id())){
            session.setTime(times.getTime());
            session.setDoctor_id(doctor_id);
            timesRepository.save(session);
        } else if (doctor == null) {
            throw new ApiException("doctor not found");
        }else if(!session.getDoctor_id().equals(doctor.getId())){
            throw new ApiException("this session does not belong to the doctor");
        }else{
            throw  new ApiException("session does not exist");
        }
    }

    public void deleteTimes(Integer id, Integer doctor_id) {
        Times session = timesRepository.findTimesById(id);
        if (doctor_id.equals(session.getDoctor_id())){
            timesRepository.delete(session);
        } else if (session == null) {
            throw new ApiException("session not found");
        }else {
            throw  new ApiException("this session does not belong to the doctor");
        }
    }

    public List<Times> findAvaliableTimes(String speciality){
        List<Times> t = timesRepository.findAvaliableTimesBySpeciality(speciality);
        if (t.isEmpty()){
            throw new ApiException("times with the chosen speciality not found");
        }
        return t;
    }

    public void reserveSession(Integer id, String key, String reason) {
        Times times = timesRepository.findTimesById(id);
        Doctors d = timesRepository.findDoctorByTime(times.getDoctor_id());
        if (times.getPatient_id() != null){
            throw new ApiException("this session is already reserved");
        }else if(times == null){
            throw new ApiException("there is no session to reserve at this time");
        }else{
            Patients p = patientsRepository.findPatientByLoginKey(key);
            if (p == null){
                throw new ApiException("patient not found");
            }
            times.setPatient_id(p.getId());
            times.setReason(reason);
            timesRepository.save(times);
            VisitRecords v = new VisitRecords();
            v.setDoctor_id(times.getDoctor_id());
            v.setTime_id(times.getId());
            v.setPatient_id(p.getId());
            visitRecordsService.addVisitRecords(times.getDoctor_id(), v);
            sendWhatsappMessage(p.getPhone(), "hello Mr."+p.getFirst_name()+" session has been reserved at " + times.getTime() + " with Dr."+d.getFirst_name()+" "+d.getLast_name()+"\nyou can cancel it an hour before the session at max");
        }
    }

    public void setWorkingHours(Integer doc_id, Integer startTime, Integer endTime){
        Integer workingHours = timesRepository.findTimesCountByDoctor_id(doc_id);
        if (workingHours == null) {
            workingHours = 0;
        }
        int newSessions = endTime - startTime + 1;
        if (workingHours + newSessions > 8) {
            throw new ApiException("your working schedule is full");
        }if (startTime == null || endTime == null || startTime < 0 || endTime > 23 || endTime < startTime) {
            throw new ApiException("invalid time range");
        }else {
            for(int i = startTime ; i <= endTime ; i++){
                Times newSession = new Times();
                String timeStr = String.format("%02d:00:00", i);
                newSession.setTime(Time.valueOf(timeStr));
                newSession.setDoctor_id(doc_id);
                timesRepository.save(newSession);
            }
        }
    }

    public void cancelReservation(Integer id, String key){
        Times sessions = timesRepository.findTimesById(id);
        VisitRecords v = timesRepository.findVisitByDoctorAndPatient(sessions.getDoctor_id(), sessions.getPatient_id());
        if(sessions == null){
            throw new ApiException("session not found");
        }else {
            Patients p = patientsRepository.findPatientByLoginKey(key);
            if (p == null){
                throw new ApiException("patient not found");
            }
            if (v.getDoctor_comments() != null){
                throw new ApiException("you have attended this session already so you cant cancel it");
            }
            visitRecordsRepository.delete(v);
            sessions.setPatient_id(null);
            sessions.setReason(null);
            timesRepository.save(sessions);
            Doctors d = timesRepository.findDoctorByTime(sessions.getDoctor_id());
            sendWhatsappMessage(p.getPhone(), "hello Mr."+p.getFirst_name()+" session has been canceled were scheduled at " + sessions.getTime() + " with Dr."+d.getFirst_name()+" "+d.getLast_name()+" successfully");
        }
    }
}
