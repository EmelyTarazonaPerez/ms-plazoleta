package plazoleta.adapters.driving.http;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;

@Service
@RequiredArgsConstructor
public class ConsumerUser {
    private final RestTemplate restTemplate;
    public UserEntity getRolByIdUser (int id) {
        return restTemplate.getForEntity("http://localhost:8080/user?id=" + id, UserEntity.class).getBody();
    }
}
