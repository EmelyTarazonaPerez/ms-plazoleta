package plazoleta.adapters.driving.http.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plazoleta.adapters.driving.http.JwtService.JwtTokenValidator;
import plazoleta.adapters.driving.http.dto.request.plate.AddPlateRequest;
import plazoleta.adapters.driving.http.mapper.IPlateResquestMapper;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.plate.Plate;

@RestController
@RequestMapping("/plate")
@RequiredArgsConstructor
public class PlateRestController {
    private final IPlateServicePort plateServicePort;
    private final IPlateResquestMapper plateRequestMapper;
    private final JwtTokenValidator jwtTokenValidator;

    @PostMapping("/create")
    public ResponseEntity<Plate> save (@Valid @RequestBody AddPlateRequest plateRequest){
        Plate plate = plateRequestMapper.toPlate(plateRequest);
        return new ResponseEntity<>(plateServicePort.create(plate), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plate> update (@Valid @RequestBody AddPlateRequest plateRequest,
                                         @PathVariable int id,
                                         @RequestHeader("Authorization") String token) {
        String auth = token.substring(7);
        int idAuthenticated = jwtTokenValidator.getUserIdFromToken(auth);
        Plate plate = plateRequestMapper.toPlate(plateRequest);
        return new ResponseEntity<>(plateServicePort.update(plate, id, idAuthenticated), HttpStatus.OK);
    }
}
