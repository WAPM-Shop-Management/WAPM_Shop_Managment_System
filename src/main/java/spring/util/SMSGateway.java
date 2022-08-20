package spring.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import spring.dto.json.reponse.ShoutOUTSMSResponseDTO;
import spring.dto.json.request.ShoutOUTSMSRequestDTO;

import java.util.*;

@Component
@Log4j2
public class SMSGateway {

    private final RestTemplate restTemplate;
    private final MobileNumberValidator mobileNumberValidator;

    @Value("${sms.api.key}")
    private String apiKey;

    public static final String URL = "https://api.getshoutout.com/coreservice/messages";

    public SMSGateway(RestTemplate restTemplate, MobileNumberValidator mobileNumberValidator) {
        this.restTemplate = restTemplate;
        this.mobileNumberValidator = mobileNumberValidator;
    }

    public void sendRegularSMS(String number, String message){
        try {
            log.info("Execute method sendRegularSMS params mobile number : {} message : {}", number, message);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", apiKey);

            String convertMobileNumber = mobileNumberValidator.convertMobileNumber(number);

            HttpEntity entity = new HttpEntity<>(new ShoutOUTSMSRequestDTO(convertMobileNumber, message), headers);

//            ResponseEntity<ShoutOUTSMSResponseDTO> response = restTemplate.exchange(URL, HttpMethod.POST, entity,
//                    ShoutOUTSMSResponseDTO.class);

        } catch (RestClientResponseException e) {
            log.error("Method sendRegularSMS RestClientResponseException " + e.getMessage());
        } catch (Exception e) {
            log.error("Method sendRegularSMS " + e.getMessage());
        }
    }
}
