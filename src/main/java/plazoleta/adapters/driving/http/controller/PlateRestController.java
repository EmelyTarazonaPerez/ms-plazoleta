package plazoleta.adapters.driving.http.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plazoleta.adapters.driving.http.JwtService.JwtTokenValidator;
import plazoleta.adapters.driving.http.dto.request.plate.AddPlateRequest;
import plazoleta.adapters.driving.http.dto.response.RestaurantResponse;
import plazoleta.adapters.driving.http.mapper.IPlateResquestMapper;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.plate.Plate;

import java.util.List;

@RestController
@RequestMapping("/plate")
@RequiredArgsConstructor
public class PlateRestController {
    private final IPlateServicePort plateServicePort;
    private final IPlateResquestMapper plateRequestMapper;
    private final JwtTokenValidator jwtTokenValidator;

    @GetMapping("/getAll/{id}")
    public ResponseEntity<List<Plate>> get (
            @PathVariable int id,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @RequestParam (defaultValue = "0") int category){
        return new ResponseEntity<>(plateServicePort.get(page, size, category, id), HttpStatus.OK);
    }

    @PostMapping("/auth/create")
    public ResponseEntity<Plate> save (@Valid @RequestBody AddPlateRequest plateRequest){
        Plate plate = plateRequestMapper.toPlate(plateRequest);
        return new ResponseEntity<>(plateServicePort.create(plate), HttpStatus.OK);
    }

    @PutMapping("auth/{id}")
    public ResponseEntity<Plate> update (@Valid @RequestBody AddPlateRequest plateRequest,
                                         @PathVariable int id,
                                         @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        int idAuthenticated = jwtTokenValidator.getUserIdFromToken(auth);
        Plate plate = plateRequestMapper.toPlate(plateRequest);
        return new ResponseEntity<>(plateServicePort.update(plate, id, idAuthenticated), HttpStatus.OK);
    }



}
