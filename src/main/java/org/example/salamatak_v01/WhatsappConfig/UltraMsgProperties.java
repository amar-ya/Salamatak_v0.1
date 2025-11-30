package org.example.salamatak_v01.WhatsappConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ultramsg")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UltraMsgProperties {

    private String instanceId;
    private String token;
    private String apiBaseUrl;

}
