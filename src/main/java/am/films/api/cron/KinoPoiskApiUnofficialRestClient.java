package am.films.api.cron;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Data
public class KinoPoiskApiUnofficialRestClient {

    private String host;

    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public KinoPoiskApiUnofficialRestClient(
            @Value("${kinopoiskapiunofficial.api.host}") String host,
            @Value("${kinopoiskapiunofficial.api.key.name}") String keyName,
            @Value("${kinopoiskapiunofficial.api.key.value}") String keyValue
    ) {
        rest = new RestTemplate();
        headers = new HttpHeaders();
        this.host = host;
        headers.add(keyName, keyValue);
    }

    public String get(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = rest.exchange(host + uri, HttpMethod.GET, requestEntity, String.class);
            this.setStatus(responseEntity.getStatusCode());
            return responseEntity.getBody();
        } catch (RestClientException e) {
            System.err.println("==============================");
        }
        return null;
    }

    public String post(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(host + uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public void put(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(host + uri, HttpMethod.PUT, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
    }

    public void delete(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(host + uri, HttpMethod.DELETE, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
