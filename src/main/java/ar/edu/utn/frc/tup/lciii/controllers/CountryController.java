package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
//import ar.edu.utn.frc.tup.lciii.model.Country;
//import ar.edu.utn.frc.tup.lciii.model.CountryRequest;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CountryController {

    @Autowired
    private final CountryService countryService;

    @GetMapping("/countries/all")
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        List<CountryDTO> countries = countryService.getAllCountriesDTO();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryDTO>> getCountriesByNameOrCode(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String code) {
        List<CountryDTO> filteredCountries = countryService.getCountriesByNameOrCode(name, code);
        return new ResponseEntity<>(filteredCountries, HttpStatus.OK);
    }
    @GetMapping("/countries/{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(@PathVariable String continent) {
        List<CountryDTO> countries = countryService.getCountriesByContinent(continent);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
    @GetMapping("/countries/{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage(@PathVariable String language) {
        List<CountryDTO> countries = countryService.getCountriesByLanguage(language);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
    @GetMapping("/countries/most-border")
    public ResponseEntity<CountryDTO> getCountriesByMostBorders() {
       CountryDTO countries = countryService.getCountriesByMostBorders();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }
//    @PostMapping()
//    public List<Country> saveCountries(@RequestBody CountryRequest countryRequest) {
//        return countryService.saveCountries(countryRequest.getAmountOfCountryToSave());
//    }
}