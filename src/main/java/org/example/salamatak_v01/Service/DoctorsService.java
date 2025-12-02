package org.example.salamatak_v01.Service;


import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Api.ApiException;
import org.example.salamatak_v01.Model.*;
import org.example.salamatak_v01.Repository.*;
import org.example.salamatak_v01.WhatsappConfig.UltraMsgProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DoctorsService
{
    private final DoctorsRepository doctorsRepository;
    private final HospitalsRepository hospitalsRepository;
    private final VisitRecordsRepository visitRecordsRepository;
    private final UltraMsgProperties ultraMsgProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Doctors> getAll(){
        List<Doctors> d =  doctorsRepository.findAll();
        if (d.isEmpty()){
            throw new ApiException("No doctors found");
        }else {
            return d;
        }
    }

    public void addDoctor(Integer hospital_id,Doctors doctors){
        Hospitals requester = hospitalsRepository.findHospitalsById(hospital_id);
        if (requester.getIs_active().equals("pending") || requester.getIs_active().equals("reject")){
            throw new ApiException("hospital is not active");
        }else if (requester.getId().equals(doctors.getHospital_id())){
            doctors.setHospital_id(hospital_id);
            doctorsRepository.save(doctors);
        }
        throw new ApiException("hospital not found");
    }

    public void updateDoctor(Integer id,Integer hospital_id, Doctors doctors){
        Hospitals requester = hospitalsRepository.findHospitalsById(hospital_id);
        Doctors oldDoctor = doctorsRepository.findDoctorsById(id);
        if (requester.getId().equals(doctors.getHospital_id())){
            oldDoctor.setFirst_name(doctors.getFirst_name());
            oldDoctor.setLast_name(doctors.getLast_name());
            oldDoctor.setHospital_id(doctors.getHospital_id());
            oldDoctor.setSpeciality(doctors.getSpeciality());
            doctorsRepository.save(oldDoctor);
        }else if (requester == null){
            throw new ApiException("hospital not found");
        }else if(oldDoctor == null){
            throw new ApiException("doctor not found");
        }
        throw new ApiException("this doctor does not work at this hospital");
    }

    public void deleteDoctor(Integer id, Integer hospital_id){
        Hospitals h = hospitalsRepository.findHospitalsById(hospital_id);
        Doctors d = doctorsRepository.findDoctorsById(id);
        if (h.getId().equals(d.getHospital_id())){
            doctorsRepository.delete(d);
        }else if(h != null){
            throw new ApiException("hospital not found");
        }else if(d != null){
            throw new ApiException("doctor not found");
        }
        throw new ApiException("this doctor does not work at this hospital");
    }


    public void addComment(Integer patient_id, Integer doc_id, VisitRecords record){
        VisitRecords v = doctorsRepository.findVisitRecordByDoctorAndPatientId(doc_id,patient_id);
        if (v == null){
            throw new ApiException("visit record not found");
        }else if (!Objects.equals(v.getDoctor_id(), doc_id)){
            throw new ApiException("this visit doesnt belong to this doctor");
        }else {
            if(record.getBlood_pressure() != null){
                v.setBlood_pressure(record.getBlood_pressure());
            }if (record.getHeight() != null){
                v.setHeight(record.getHeight());
            }if (record.getWeight() != null){
                v.setWeight(record.getWeight());
            }if(v.getHeight() != null && v.getWeight() != null){
                v.setBmi(v.getWeight()/(v.getHeight()*v.getHeight()));
            }if(record.getAllergies() != null){
                v.setAllergies(record.getAllergies());
            }if (record.getHeart_rate() != null){
                v.setHeart_rate(record.getHeart_rate());
            }if (record.getTemperature() != null){
                v.setTemperature(record.getTemperature());
            }if(record.getAge() != null){
                v.setAge(record.getAge());
            }if (record.getDoctor_comments() != null){
                v.setDoctor_comments(record.getDoctor_comments());
            }
            visitRecordsRepository.save(v);
            Patients p = doctorsRepository.findPatientById(patient_id);
            Doctors d = doctorsRepository.findDoctorsById(doc_id);
            String message = "DR."+d.getFirst_name()+"'s visit comment : "+record.getDoctor_comments();
            sendWhatsappMessage("+966"+p.getPhone(), message);
        }
    }

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


}
