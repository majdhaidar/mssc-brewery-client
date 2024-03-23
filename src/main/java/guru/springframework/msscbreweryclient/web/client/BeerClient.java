package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
public class BeerClient {
    @Setter
    private String apiHost;
    @Setter
    private String beerUrlPath;
    private final RestTemplate restTemplate;

    public BeerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDto getBeerById(UUID id) {
        return restTemplate.getForObject(apiHost + beerUrlPath + id.toString(), BeerDto.class);
    }

    public URI saveNew(BeerDto beerDto) {
        return restTemplate.postForLocation(apiHost + beerUrlPath, beerDto);
    }

    public void update(UUID id, BeerDto beerDto) {
        restTemplate.put(apiHost + beerUrlPath + id, beerDto);
    }

    public void delete(UUID id) {
        restTemplate.delete(apiHost + beerUrlPath + id);
    }
}
