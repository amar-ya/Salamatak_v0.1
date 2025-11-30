package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Model.Doctors;
import org.example.salamatak_v01.Model.Patients;
import org.example.salamatak_v01.Model.UsersKeys;
import org.example.salamatak_v01.Repository.UsersKeyRepository;
import org.example.salamatak_v01.WhatsappConfig.UltraMsgProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UsersKeysService {

    private final UsersKeyRepository usersKeyRepository;
    private final UltraMsgProperties ultraMsgProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public void addPatientKeys(Integer patient_id){
        UsersKeys k = new UsersKeys();
        k.setPatient_id(patient_id);
        usersKeyRepository.save(k);
    }

    public void addDoctorKeys(Integer doc_id){
        UsersKeys k = new UsersKeys();
        k.setDoc_id(doc_id);
        usersKeyRepository.save(k);
    }

    public List<UsersKeys> getKeys(){
        return usersKeyRepository.findAll();
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

    public String generatePatientOTP(Integer id){
        Random random = new Random();
        UsersKeys k = usersKeyRepository.findByPatientId(id);
        Patients p =  usersKeyRepository.findPatientByKeyId(id);
        if (k == null){
            return "Patient not found";
        }
        int number = random.nextInt(1_000_000);
        String otp = String.format("%06d", number);
        k.setOTP(otp);
        usersKeyRepository.save(k);
        // 3) send via UltraMsg
        String message = "Your verification code is: " + otp +
                "\nIt expires in 5 minutes. Do not share this code with anyone.";

        sendWhatsappMessage("+966"+p.getPhone(), message);
        return "success";
    }

    public String generateDoctorOTP(Integer id){
        Random random = new Random();
        UsersKeys k = usersKeyRepository.findByDoctorId(id);
        Doctors p =  usersKeyRepository.findDoctorByKeyId(id);
        if (k == null){
            return "doctor not found";
        }
        int number = random.nextInt(1_000_000);
        String otp = String.format("%06d", number);
        k.setOTP(otp);
        k.setUsed(false);
        usersKeyRepository.save(k);
        // 3) send via UltraMsg
        String message = "Your verification code is: " + otp +
                "\nIt expires in 5 minutes. Do not share this code with anyone.";

        sendWhatsappMessage("+966"+p.getPhone(), message);
        return "success";
    }
}
