package anis.com.beer.order.service.services;

import anis.com.beer.order.service.domain.BeerOrder;
import anis.com.beer.order.service.domain.BeerOrderLine;
import anis.com.beer.order.service.domain.BeerOrderStatusEnum;
import anis.com.beer.order.service.domain.Customer;
import anis.com.beer.order.service.repositories.BeerOrderRepository;
import anis.com.beer.order.service.repositories.CustomerRepository;
import anis.com.beer.order.service.services.beer.BeerServiceImpl;
import anis.com.brewery.model.BeerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.jgroups.util.Util.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(WireMockExtension.class)
@SpringBootTest
public class BeerOrderManagerImpIT {


    @Autowired
    WireMockServer wireMockServer;
    @Autowired
    BeerOrderManager beerOrderManager;
    @Autowired
    BeerOrderRepository beerOrderRepository;
    @Autowired
    CustomerRepository customerRepository;

    Customer testCustomer;
    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ObjectMapper objectMapper;

    UUID beerId =  UUID.randomUUID();

    @TestConfiguration
    static class RestTemplateBuilderProvider {
        @Bean(destroyMethod = "stop")
        public WireMockServer wireMockServer(){
            WireMockServer server = with(wireMockConfig().port(8084));
            server.start();
            return server;
        }
    }

    @BeforeEach
    void setUp(){
        testCustomer = customerRepository.save(Customer.builder()
                            .customerName("Test Customer")
                            .build());
    }

    @Test
    void itShouldAllocated() throws JsonProcessingException {
        BeerDto beerDto = BeerDto.builder().id(beerId).upc("12345").build();

        wireMockServer.stubFor(get(BeerServiceImpl.BEER_PATH_UPC_V1+"12345")
        .willReturn(okJson(objectMapper.writeValueAsString(beerDto))));

        BeerOrder beerOrder = createBeerOrder();
        BeerOrder savedBeerOrder =  beerOrderManager.newBeerOrder(beerOrder);
        assertNotNull(savedBeerOrder);
        assertEquals(BeerOrderStatusEnum.ALLOCATED,savedBeerOrder.getOrderStatus());
    }

    private BeerOrder createBeerOrder(){
        BeerOrder beerOrder = BeerOrder.builder().
                customer(testCustomer)
                .build();

        Set<BeerOrderLine> lines = new HashSet<>();
        lines.add(BeerOrderLine.builder()
                .beerId(beerId)
                .orderQuantity(1)
                .upc("12345")
                .beerOrder(beerOrder)
                .build());
        beerOrder.setBeerOrderLines(lines);
        return beerOrder;
    }
}
