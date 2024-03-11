package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.CreateCustomerDTO;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import guru.springframework.msscbreweryclient.web.model.UpdateCustomerReqDTO;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "mssc.brewery", ignoreUnknownFields = false)
public class CustomerClient {
    @Setter
    private String apiHost;
    private final String CUSTOMER_URL_PATH = "/api/v1/customer/";
    private final RestTemplate restTemplate;

    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public CustomerDto getCustomerById(UUID id) {
        return restTemplate.getForObject(apiHost + CUSTOMER_URL_PATH + id, CustomerDto.class);
    }

    public URI saveNew(CreateCustomerDTO createCustomerDTO) {
        return restTemplate.postForLocation(apiHost + CUSTOMER_URL_PATH, createCustomerDTO);
    }

    public void update(UUID id, UpdateCustomerReqDTO updateCustomerReqDTO) {
        restTemplate.put(apiHost + CUSTOMER_URL_PATH + id, updateCustomerReqDTO);
    }

    public void delete(UUID id) {
        restTemplate.delete(apiHost + CUSTOMER_URL_PATH + id);
    }
}
