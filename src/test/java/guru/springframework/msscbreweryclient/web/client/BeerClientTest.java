package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BeerClientTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void testGetClientById() {
        BeerDto beerById = beerClient.getBeerById(UUID.randomUUID());
        assertNotNull(beerById);
    }

    @Test
    void testSaveNew() {
        BeerDto beerDto = BeerDto.builder().id(UUID.randomUUID()).beerName("majd").beerStyle("some style").upc(1l).build();

        URI uri = beerClient.saveNew(beerDto);
        assertNotNull(uri);
        System.out.println(uri);
    }

    @Test
    void update(){
        BeerDto beerDto = BeerDto.builder().beerName("majd").beerStyle("some style").upc(1l).build();
        beerClient.update(UUID.randomUUID(), beerDto);
    }

    @Test
    void delete(){
        beerClient.delete(UUID.randomUUID());
    }
}