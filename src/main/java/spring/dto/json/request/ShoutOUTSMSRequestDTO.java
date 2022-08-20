package spring.dto.json.request;

import java.util.ArrayList;
import java.util.List;

public class ShoutOUTSMSRequestDTO {

    private String source = "ShoutDEMO";
    private List<String> destinations = new ArrayList<>();
    private SMSContentRequestDTO content;
    private List<String> transports = new ArrayList<>();

    public ShoutOUTSMSRequestDTO(String number, String content) {
        this.destinations.add(number);
        this.content = new SMSContentRequestDTO(content);
        this.transports.add("sms");
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<String> destinations) {
        this.destinations = destinations;
    }

    public SMSContentRequestDTO getContent() {
        return content;
    }

    public void setContent(SMSContentRequestDTO content) {
        this.content = content;
    }

    public List<String> getTransports() {
        return transports;
    }

    public void setTransports(List<String> transports) {
        this.transports = transports;
    }
}
