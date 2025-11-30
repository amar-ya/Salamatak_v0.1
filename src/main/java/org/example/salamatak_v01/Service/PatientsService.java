package org.example.salamatak_v01.Service;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.AiClient.OpenAiClient;
import org.example.salamatak_v01.Model.*;
import org.example.salamatak_v01.Repository.PatientRecordsRepository;
import org.example.salamatak_v01.Repository.PatientsRepository;
import org.example.salamatak_v01.Repository.UsersKeyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PatientsService
{
    private final PatientsRepository patientsRepository;
    private final PatientRecordsRepository patientRecordsRepository;
    private final OpenAiClient aiClient;
    private final UsersKeyRepository usersKeyRepository;

    public List<Patients> getPatients(){
        return patientsRepository.findAll();
    }

    public String addPatient(Patients patient){
        Patients p = patientsRepository.findPatientByNationalId(patient.getNational_id());
        if (p==null){
            patientsRepository.save(patient);
            return "success";
        }else {
            return "an account with this National Id is already registered";
        }
    }

    public boolean updatePatient(Integer id, Patients patient){
        Patients p = patientsRepository.findPatientById(id);
        if(p != null){
            p.setBirth_date(patient.getBirth_date());
            p.setFirst_name(patient.getFirst_name());
            p.setLast_name(patient.getLast_name());
            p.setBlood_type(patient.getBlood_type());
            p.setGender(patient.getGender());
            p.setNational_id(patient.getNational_id());
            patientsRepository.save(p);
            return true;
        }
        return false;
    }

    public boolean deletePatient(Integer id){
        Patients p = patientsRepository.findPatientById(id);
        if(p != null){
            patientsRepository.delete(p);
            return true;
        }
        return false;
    }

    public String getHealthAdvice(String key){
        Patients p = patientsRepository.findPatientByLoginKey(key);
        PatientRecords records = patientRecordsRepository.findPatientRecordsById(p.getId());
        return aiClient.getHealthAdvice(records.getHeight(),records.getWeight(),records.getBmi(),p.getBirth_date(), p.getGender());
    }

    public String getFeelingAdvice(String userDescription){
        return aiClient.getFeelingAdvice(userDescription);
    }

    public String changePassword(String OTP ,String national_id, String password ){
        Patients p = patientsRepository.findPatientByNationalId(national_id);

        if (p == null){
            return "patient not found";
        }else {
            UsersKeys key = patientsRepository.findOTPByPatientId(p.getId());
            if (!Objects.equals(key.getOTP(), OTP)){
                return "OTP does not match";
            }if(key.getUsed()){
                return "OTP is used";
            }else {
                p.setPassword(password);
                key.setUsed(true);
                patientsRepository.save(p);
                return "success";
            }
        }
    }

    public String login(String national_id, String password){
        Random random = new Random();
        Patients p = patientsRepository.findPatientByNationalId(national_id);
        if (p == null){
            return "patient not found";
        }else{
            if(!password.equals(p.getPassword())){
                return "password does not match";
            }else {
                UsersKeys k = patientsRepository.findKeyByPatientId(p.getId());
                int key = 50000 + random.nextInt(9999);
                k.setLogin_key(String.valueOf(key));
                usersKeyRepository.save(k);
                return "success your login key is "+k.getLogin_key();
            }
        }
    }

    public String logOut(String key){
        Patients p = patientsRepository.findPatientByLoginKey(key);
        if(p == null){
            return "patient not found";
        }else {
            UsersKeys Login_key = patientsRepository.findKeyByPatientId(p.getId());
            Login_key.setLogin_key(null);
            usersKeyRepository.save(Login_key);
            return "success";
        }
    }

    public String prepareQuestionsToAsksDoctor(String key,String feeling){
        Patients p = patientsRepository.findPatientByLoginKey(key);
        Times t = patientsRepository.findTimeByPatientId(p.getId());
        if (t == null){
            return "failed";
        }else {
            Doctors d = patientsRepository.findDoctorBySessionsDoc_Id(t.getDoctor_id());
            String answer = aiClient.prepareQuestionsToAsksDoctor(t.getTime(),d.getSpeciality(),feeling);
            return answer;
        }
    }
}
