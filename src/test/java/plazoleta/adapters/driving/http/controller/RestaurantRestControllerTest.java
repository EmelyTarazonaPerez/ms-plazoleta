package plazoleta.adapters.driving.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driving.http.ConsumerUser;
import plazoleta.adapters.driving.http.dto.AddRestaurantRequest;
import plazoleta.adapters.driving.http.mapper.IRestaurantRequestMapper;
import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.model.Restaurant;
import plazoleta.domain.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {

    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;
    @Mock
    private ConsumerUser consumerUser;

    @InjectMocks
    RestaurantRestController restaurantRestController;
    private MockMvc mockMcv;

    @BeforeEach
    void setUp() {
        mockMcv = MockMvcBuilders.standaloneSetup(restaurantRestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void save() throws Exception {
        String restaurantJSON = "{\n" +
                "    \"name\": \"restaurant\",\n" +
                "    \"address\": \"calle 7 #17\",\n" +
                "    \"ownerId\": {\n" +
                "        \"idUser\": 1\n" +
                "    },\n" +
                "    \"phone\": \"+57109056853\",\n" +
                "    \"urlLogo\": \"djasdnjasdhasd\",\n" +
                "    \"nit\": \"nit\"\n" +
                "}\n";

        Restaurant restaurant = new Restaurant(1,"rest", "any",
                new User(), "+573104922805", "logo", "nit"
        );
        UserEntity userEntity = new UserEntity();
        userEntity.setIdUser(1);
        userEntity.getIdRol().setIdRol(2);

        when(consumerUser.getRolByIdUser(userEntity.getIdUser())).thenReturn(userEntity);
        when(restaurantServicePort.createRestaurant(restaurantRequestMapper.toRestaurant(any(AddRestaurantRequest.class))))
                .thenReturn(restaurant);

        mockMcv.perform(post("/restaurant/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(restaurantJSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("retornar status 400 al registrar un phone incorrectamente")
    void savePhoneError() throws Exception {
        String restaurantJSON = "{\n" +
                "    \"name\": \"restaurant\",\n" +
                "    \"address\": \"calle 7 #17\",\n" +
                "    \"ownerId\": {\n" +
                "        \"idUser\": 2\n" +
                "    },\n" +
                "    \"phone\": \"p571z09056853\",\n" +
                "    \"urlLogo\": \"djasdnjasdhasd\"\n" +
                "}\n";


        mockMcv.perform(post("/restaurant/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(restaurantJSON))
                .andExpect(status().isBadRequest());
    }
}