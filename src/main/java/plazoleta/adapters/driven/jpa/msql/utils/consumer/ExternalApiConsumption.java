package plazoleta.adapters.driven.jpa.msql.utils.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import plazoleta.adapters.driven.jpa.msql.entity.OrderEntity;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.domain.model.pedido.Order;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExternalApiConsumption  {
    private final RestTemplate restTemplate;

    public UserEntity getRolByIdUser (int id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ token);

        return restTemplate.exchange("http://localhost:8080/auth/get/user?id=" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserEntity.class).getBody();
    }

    public void sendTraceability (UserEntity client, String previousState, String stateNew, UserEntity employeer, LocalDate date, int idOrder) {
        TraceabilityData data = new TraceabilityData(client, previousState, stateNew, employeer, date, idOrder);
        HttpEntity<TraceabilityData> requestEntity = new HttpEntity<>(data);

        restTemplate.exchange("http://localhost:8085/trazabilidad",
                HttpMethod.POST,
                requestEntity,
                Void.class
                );
    }
}
