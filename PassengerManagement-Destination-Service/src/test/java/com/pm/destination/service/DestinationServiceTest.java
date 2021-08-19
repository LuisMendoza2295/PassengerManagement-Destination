//package com.pm.destination.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pm.destination.domain.model.Destination;
//import com.pm.destination.domain.repository.DestinationRepository;
//import com.pm.destination.domain.vo.UserID;
//import com.pm.destination.service.dto.UserDTO;
//import com.pm.destination.service.impl.DestinationServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.client.ClientRequest;
//import org.springframework.web.reactive.function.client.ClientResponse;
//import org.springframework.web.reactive.function.client.ExchangeFunction;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.test.StepVerifier;
//
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class DestinationServiceTest {
//
//    /* private final WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
//    private final WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
//    private final WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
//    private final WebClient webClient = mock(WebClient.class); */
//    public final ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
//
//    public final ObjectMapper objectMapper = new ObjectMapper();
//
//    public final DestinationRepository destinationRepository = mock(DestinationRepository.class);
//
//    public DestinationService destinationService;
//
//    @BeforeEach
//    public void initEach() {
//        WebClient webClient = WebClient.builder()
//                .exchangeFunction(exchangeFunction)
//                .build();
//
//        destinationService = new DestinationServiceImpl(destinationRepository, webClient);
//    }
//
//    @Test
//    public void testNotFoundUser() {
//        UserID userID = new UserID("notFound");
//        when(exchangeFunction.exchange(any(ClientRequest.class)))
//                .thenReturn(ClientResponse.create(HttpStatus.NOT_FOUND).build().bodyToMono(ClientResponse.class));
//
//        Flux<Destination> destinationResponseFlux = destinationService.findAllByUserID(userID);
//        /* StepVerifier.create(destinationResponseFlux)
//                .expectError(ExternalServiceException.class)
//                .log()
//                .verify(); */
//        StepVerifier.create(destinationResponseFlux)
//                .expectNextMatches(destination -> destination.getFrom().equals("Not found"))
//                .expectComplete()
//                .log()
//                .verify();
//    }
//
//    @Test
//    public void testDestinations() {
//        UserID userID = new UserID("test1");
//
//        when(exchangeFunction.exchange(any(ClientRequest.class)))
//                .thenReturn(Mono.just(ClientResponse
//                        .create(HttpStatus.OK)
//                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                        .body(this.generateUserDTOResponse(userID))
//                        .build()));
//
//        Flux<Destination> destinationFlux = this.generateDestinationFlux(userID);
//        when(destinationRepository.getAllByUserID(eq(userID))).thenReturn(destinationFlux);
//
//        Flux<Destination> destinationResponseFlux = destinationService.findAllByUserID(userID);
//
//        StepVerifier.create(destinationResponseFlux)
//                .expectNextMatches(destination -> "from1".equals(destination.getFrom()))
//                .expectNextCount(1)
//                .expectNextMatches(destination -> "from3".equals(destination.getFrom()))
//                .expectComplete()
//                .log()
//                .verify();
//    }
//
//    /* @Test
//    public void testFindDestination() {
//        destinationService = new DestinationServiceImpl(destinationRepository, webClient);
//
//        UserID userID = new UserID("test1");
//        Flux<Destination> destinationFlux = this.generateDestinationFlux(userID);
//        Flux<UserDTO> userDTOFlux = this.generateUserDTOFlux(userID);
//
//        when(uriSpec.uri(anyString(), any(Object.class))).thenReturn(headersSpec);
//        when(webClient.get()).thenReturn(uriSpec);
//
//        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
//        when(headersSpec.retrieve()).thenReturn(responseSpec);
//
//        when(responseSpec.bodyToFlux(UserDTO.class)).thenReturn(userDTOFlux);
//
//        when(destinationRepository.getAllByUserID(eq(userID))).thenReturn(destinationFlux);
//
//        Flux<Destination> destinationResponseFlux = destinationService.findAllByUserID(userID);
//
//        StepVerifier.create(destinationResponseFlux)
//                .expectNextMatches(destination -> "from1".equals(destination.getFrom()))
//                .expectNextCount(1)
//                .expectNextMatches(destination -> "from3".equals(destination.getFrom()))
//                .expectComplete()
//                .log()
//                .verify();
//
//        StepVerifier.create(destinationResponseFlux)
//                .expectNextCount(3)
//                .verifyComplete();
//    } */
//
//    private Flux<Destination> generateDestinationFlux(UserID userID) {
//        return Flux.just(
//                new Destination(userID, "from1", "to1"),
//                new Destination(userID, "from2", "to2"),
//                new Destination(userID, "from3", "to3")
//        );
//    }
//
//    private Flux<UserDTO> generateUserDTOFlux(UserID userID) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(userID.getValue());
//
//        return Flux.just(userDTO);
//    }
//
//    private String generateUserDTOResponse(UserID userID) {
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(userID.getValue());
//
//        try {
//            return this.objectMapper.writeValueAsString(userDTO);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return "{}";
//    }
//}
