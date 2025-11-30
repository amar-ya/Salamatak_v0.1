package org.example.salamatak_v01.Scheduled;

import lombok.RequiredArgsConstructor;
import org.example.salamatak_v01.Model.PatientRecords;
import org.example.salamatak_v01.Model.Times;
import org.example.salamatak_v01.Model.VisitRecords;
import org.example.salamatak_v01.Repository.PatientRecordsRepository;
import org.example.salamatak_v01.Repository.TimesRepository;
import org.example.salamatak_v01.Repository.VisitRecordsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyCleanUp
{
    private final TimesRepository timesRepository;
    private final PatientRecordsRepository patientRecordsRepository;
    private final VisitRecordsRepository visitRecordsRepository;

    @Scheduled(fixedRate = 6000)
    public void dailyTimeCleaner(){
        List<Times> times = timesRepository.findAll();
        for (Times t : times) {
            if (t.getTime().before(Time.valueOf(LocalTime.now()))){
                VisitRecords visit = visitRecordsRepository.findByTimeId(t.getId());
                if (visit != null) {
                    PatientRecords p = patientRecordsRepository.findPatientRecordsById(t.getPatient_id());
                    if (visit.getAllergies() != null) {
                        p.setAllergies(visit.getAllergies());
                    }
                    if (visit.getBlood_pressure() != null) {
                        p.setBlood_pressure(visit.getBlood_pressure());
                    }
                    if (visit.getHeart_rate() != null) {
                        p.setHeart_rate(visit.getHeart_rate());
                    }
                    if (visit.getTemperature() != null) {
                        p.setTemperature(visit.getTemperature());
                    }
                    if (visit.getWeight() != null) {
                        p.setWeight(visit.getWeight());
                    }
                    if (visit.getHeight() != null) {
                        p.setHeight(visit.getHeight());
                    }
                    if (visit.getWeight() != null && visit.getHeight() != null) {
                        p.setBmi(visit.getWeight()/(visit.getHeight()*visit.getHeight()));
                    }
                    patientRecordsRepository.save(p);
                }
                t.setPatient_id(null);
                t.setReason(null);
                timesRepository.save(t);
            }
        }
    }

}
