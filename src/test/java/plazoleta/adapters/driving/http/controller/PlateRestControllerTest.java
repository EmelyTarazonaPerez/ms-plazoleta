package plazoleta.adapters.driving.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import plazoleta.adapters.driving.http.JwtService.JwtTokenValidator;
import plazoleta.adapters.driving.http.dto.request.plate.AddPlateRequest;
import plazoleta.adapters.driving.http.mapper.IPlateResquestMapper;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.plate.Category;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.model.restaurant.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.param;

@ExtendWith(MockitoExtension.class)
class PlateRestControllerTest {

    @Mock
    IPlateServicePort plateServicePort;
    @Mock
    IPlateResquestMapper plateRequestMapper;
    @Mock
    private JwtTokenValidator jwtTokenValidator;

    @InjectMocks
    PlateRestController plateRestController;
    private MockMvc mockMcv;

    private Plate plateInput;

    @BeforeEach
    void setUp() {
        mockMcv = MockMvcBuilders.standaloneSetup(plateRestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        plateInput = new Plate(1, "Hamburguesa", 1,
                "Deliciosa hamburguesa", 1500, 1,
                "https://ejemplo.com/hamburguesa.jpg");
    }

    @Test
    void save() throws Exception {
        String plateJPA = "{\n" +
                "    \"name\": \"Plato de ejemplo\",\n" +
                "    \"categoryId\": 1," +
                "    \"description\": \"Descripción del plato de ejemplo\",\n" +
                "    \"price\": 628,\n" +
                "    \"restaurantId\": 1,\n" +
                "    \"imageUrl\": \"https://ejemplo.com/imagen.jpg\"\n" +
                "}";


        when(plateServicePort.create(plateRequestMapper.toPlate(any(AddPlateRequest.class)))).thenReturn(plateInput);

        mockMcv.perform(post("/plate/auth/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(plateJPA))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("retornar status 400 al recibir valores nulos")
    void saveError() throws Exception {
        String platetJSON = "{\\n\" +\n" +
                "                \"    \\\"name\\\": null,\\n\" +\n" +
                "                \"    \\\"categoryId\\\": 1 " +
                "                \"    \\\"description\\\": \\\"Descripción del plato de ejemplo\\\",\\n\" +\n" +
                "                \"    \\\"price\\\": 628,\\n\" +\n" +
                "                \"    \\\"restaurantId\\\": {\\n\" +\n" +
                "                \"        \\\"id\\\": 1\\n\" +\n" +
                "                \"    },\\n\" +\n" +
                "                \"    \\\"imageUrl\\\": \\\"https://ejemplo.com/imagen.jpg\\\"\\n\" +\n" +
                "                \"}";


        mockMcv.perform(post("/plate/auth/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(platetJSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("retornar status 400 al recibir precios negativos")
    void saveErrorPrice() throws Exception {
        String plateJPA = "{\n" +
                "    \"name\": \"Plato de ejemplo\",\n" +
                "    \"categoryId\": 1" +
                "    \"description\": \"Descripción del plato de ejemplo\",\n" +
                "    \"price\": -628,\n" +
                "    \"restaurantId\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"imageUrl\": \"https://ejemplo.com/imagen.jpg\"\n" +
                "}";


        mockMcv.perform(post("/plate/auth/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(plateJPA))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdatePlate_Success() throws Exception {
        AddPlateRequest plateRequest = new AddPlateRequest("name", 1, "Hamburguesa", 1223,
                1, "https://ejemplo.com/hamburguesa.jpg", true);

        String token = "Bearer your_jwt_token_here";
        int id = 1;
        int idAuthenticated = 29; // mock authenticated user id

        // Mocking behavior
        when(jwtTokenValidator.getUserIdFromToken(anyString())).thenReturn(idAuthenticated);
        when(plateRequestMapper.toPlate(any(AddPlateRequest.class))).thenReturn(plateInput);
        when(plateServicePort.update(any(Plate.class), eq(id), eq(idAuthenticated))).thenReturn(plateInput);

        // Act
        ResponseEntity<Plate> response = plateRestController.update(plateRequest, id, token);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void get() throws Exception {
        List<Plate> plateList = new ArrayList<>();
        when(plateServicePort.get(0, 5, 0, 6)).thenReturn(plateList);

        mockMcv.perform(MockMvcRequestBuilders.get("/plate/getAll/6")
                .param("page", "0")
                .param("size", "5")
                .param("category", "0"))
                .andExpect(status().isOk());
    }
}