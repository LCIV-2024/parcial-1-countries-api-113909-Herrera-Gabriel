package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCountries_ReturnsListOfCountries() {
        CountryDTO country1 = new CountryDTO("ARG", "Argentina");
        CountryDTO country2 = new CountryDTO("BRA", "Brazil");
        List<CountryDTO> countries = Arrays.asList(country1, country2);

        when(countryService.getAllCountriesDTO()).thenReturn(countries);

        ResponseEntity<List<CountryDTO>> response = countryController.getAllCountries();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(countries, response.getBody());
        verify(countryService, times(1)).getAllCountriesDTO();
    }

    @Test
    void getCountriesByNameOrCode_ReturnsFilteredCountries() {
        CountryDTO country = new CountryDTO("ARG", "Argentina");
        List<CountryDTO> countries = new ArrayList<>();
        countries.add(country);

        when(countryService.getCountriesByNameOrCode("Argentina", null)).thenReturn(countries);

        ResponseEntity<List<CountryDTO>> response = countryController.getCountriesByNameOrCode("Argentina", null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(countries, response.getBody());
        verify(countryService, times(1)).getCountriesByNameOrCode("Argentina", null);
    }

    @Test
    void getCountriesByContinent_ReturnsCountriesByContinent() {
        CountryDTO country = new CountryDTO("ARG", "Argentina");
        List<CountryDTO> countries = new ArrayList<>();
        countries.add(country);

        when(countryService.getCountriesByContinent("South America")).thenReturn(countries);

        ResponseEntity<List<CountryDTO>> response = countryController.getCountriesByContinent("South America");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(countries, response.getBody());
        verify(countryService, times(1)).getCountriesByContinent("South America");
    }

    @Test
    void getCountriesByLanguage_ReturnsCountriesByLanguage() {
        CountryDTO country = new CountryDTO("ARG", "Argentina");
        List<CountryDTO> countries = new ArrayList<>();
        countries.add(country);

        when(countryService.getCountriesByLanguage("Spanish")).thenReturn(countries);

        ResponseEntity<List<CountryDTO>> response = countryController.getCountriesByLanguage("Spanish");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(countries, response.getBody());
        verify(countryService, times(1)).getCountriesByLanguage("Spanish");
    }

    @Test
    void getCountriesByMostBorders_ReturnsCountryWithMostBorders() {
        CountryDTO country = new CountryDTO("ARG", "Argentina");

        when(countryService.getCountriesByMostBorders()).thenReturn(country);

        ResponseEntity<CountryDTO> response = countryController.getCountriesByMostBorders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(country, response.getBody());
        verify(countryService, times(1)).getCountriesByMostBorders();
    }
}