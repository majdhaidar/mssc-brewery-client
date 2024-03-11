package guru.springframework.msscbreweryclient.web.client;

import guru.springframework.msscbreweryclient.web.model.CreateCustomerDTO;
import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import guru.springframework.msscbreweryclient.web.model.UpdateCustomerReqDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    CustomerClient customerClient;

    @Test
    void getCustomerById() {
        CustomerDto customerDto = customerClient.getCustomerById(UUID.randomUUID());
        assertNotNull(customerDto);
    }

    @Test
    void saveNew() {
        CreateCustomerDTO customerDto = CreateCustomerDTO.builder().name("majd").build();
        URI uri = customerClient.saveNew(customerDto);
        assertNotNull(uri);
        System.out.println(uri);
    }

    @Test
    void update() {
        UUID customerId = UUID.randomUUID();
        UpdateCustomerReqDTO updateCustomerReqDTO = UpdateCustomerReqDTO.builder().name("updateName").build();
        customerClient.update(customerId, updateCustomerReqDTO);
    }

    @Test
    void delete() {
        customerClient.delete(UUID.randomUUID());
    }
}