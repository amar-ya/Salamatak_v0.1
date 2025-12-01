package org.example.salamatak_v01.AiClient;

import org.example.salamatak_v01.AiClient.AiConfig.AiProperties;
import org.example.salamatak_v01.Model.Times;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiClient implements AiClient {

    private final RestClient restClient;
    private final AiProperties props;

    public OpenAiClient(AiProperties props) {
        this.props = props;
        this.restClient = RestClient.builder()
                .baseUrl(props.getBaseUrl())
                .defaultHeader("Authorization", "Bearer " + props.getApiKey())
                .build();
    }

    @Override
    public String getHealthAdvice(Float height, Float weight, Float bmi, Date birthdate,String gender,String name) {
        Map<String, Object> requestBody = Map.of(
                "model", props.getModel(),
                "messages", List.of(
                        Map.of("role", "system",
                                "content", """
                                    You are a helpful health assistant.
                                    You give simple, practical lifestyle advice (nutrition, movement, sleep, habits)
                                    based on the user's age, height, weight, and BMI.
                                    You are NOT a doctor and you must always remind the user that your advice
                                    is general and not a substitute for medical diagnosis.
                                    Keep your answer clear, friendly, and under 100 words.
                                    the default language in answering is arabic unless the user`s name is none arab name like mike or john for example but if names like ammar muhammed go with the default
                                    
                                    """),
                        Map.of("role", "user",
                                "content",
                                "Patient name is : "+ name +" Give me health and lifestyle advice based on these details: " +
                                        "gender: "+ gender + " , " +
                                        "date of birth: " + birthdate + " , " +
                                        "Height: " + height + " Meter, " +
                                        "Weight: " + weight + " kg, " +
                                        "BMI: " + bmi + ".")
                ),
                "max_tokens", 3000
        );
        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // ⚠️ Very simplified parsing – in real code map to a proper DTO
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        return (String) message.get("content");
    }


    @Override
    public String getFeelingAdvice(String userDescription) {
        Map<String, Object> requestBody = Map.of(
                "model", props.getModel(),
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", """
                                    You are a supportive health and wellbeing assistant.
                                    The user will describe what they are feeling (physically or emotionally).
                                    
                                    Your job:
                                    1) Briefly summarize what they might be experiencing IN SIMPLE LANGUAGE.
                                    2) Give 3–6 practical self-care suggestions they can try to feel a bit better (lifestyle, rest, hydration, stress reduction, gentle movement, etc.).
                                    3) Suggest 1–3 appropriate medical SPECIALTIES they could visit (e.g., family physician / general practitioner, cardiologist, dermatologist, psychiatrist, psychologist, ENT, orthopedist, etc.).
                                    4) Clearly say this is NOT a diagnosis and NOT a substitute for seeing a doctor.
                                    5) If the description mentions serious red flags
                                       (chest pain, difficulty breathing, severe bleeding, loss of consciousness,
                                        stroke symptoms, suicidal thoughts, extremely severe pain, high fever in infants),
                                       tell them to seek EMERGENCY medical care immediately.
                                    6) if the asker telling he has some medical issue and you feel like its something serious dont give him a lot of talking just tell him to go to EMERGENCY asap
                                    
                                    Rules:
                                    - Do NOT name specific medicines, doses, or prescriptions.
                                    - Do NOT give treatment plans. Only general self-care and specialty suggestions.
                                    - Keep the tone kind, non-judgmental, and encouraging.
                                    - Keep the answer under about 100 words.
                                    and you must respond in the same language of the user that used in the question.
                                    """
                        ),
                        Map.of(
                                "role", "user",
                                "content", "The user says they feel: " + userDescription
                        )
                ),
                "max_tokens", 3000
        );

        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // ⚠️ Very simplified parsing – in real code map to a proper DTO
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        return (String) message.get("content");
    }

    @Override
    public String prepareQuestionsToAsksDoctor(Time time, String Speciality, String feeling) {
        Map<String, Object> requestBody = Map.of(
                "model", props.getModel(),
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", """
                                        Your job:
                                        1) Understand the user's situation in simple, general terms. \s
                                        2) Prepare 5 clear and practical questions the patient can ask the doctor during their visit. \s
                                        3) These questions should help the patient:
                                           - Understand the possible causes of their symptoms
                                           - Know what tests might be needed
                                           - Know what lifestyle changes might help
                                           - Understand warning signs or when to seek urgent care
                                           - Understand the treatment plan or next steps
                                        4) If the user describes dangerous or emergency symptoms
                                           (chest pain, severe breathing problems, stroke symptoms, severe bleeding,
                                            suicidal thoughts, high fever in infants, loss of consciousness),
                                           DO NOT generate questions — instead say:
                                           “Please go to the emergency room immediately.
                                        5) if the patient is talking about something he is feeling that doesnt have any link to the doctor speciality tell him he is reserving the wrong speciality”
                                        
                                        
                                        Rules:
                                        - Do NOT diagnose.
                                        - Do NOT mention specific medicines.
                                        - Do NOT give treatment instructions.
                                        - Keep wording simple, helpful, and respectful.
                                        - Output ONLY the 5 questions in a numbered list.
                                        - and you must respond in the same language of the user that used in the question.
                                    """
                        ),
                        Map.of(
                                "role", "user",
                                "content", "The user has reservation : " + time + " " + Speciality + " " + feeling
                        )
                ),
                "max_tokens", 3000
        );

        Map<String, Object> response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        // ⚠️ Very simplified parsing – in real code map to a proper DTO
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
        return (String) message.get("content");
    }

}
