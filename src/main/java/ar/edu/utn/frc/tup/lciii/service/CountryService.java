package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
        @Autowired
        private final ModelMapper modelMapper;
//        @Autowired
//        private final CountryRepository countryRepository;
        @Autowired
        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                List<String> borders = (List<String>) countryData.get("borders");
                if (borders == null) {
                        borders = new ArrayList<>();
                }
                System.out.println(borders);
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .code((String) countryData.get("cca3"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        //.borders((List<String>) countryData.get("borders"))
                        .borders(borders)
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }
        public List<CountryDTO> getAllCountriesDTO() {
                List<CountryDTO> countries = new ArrayList<>();
                List<Country> allCountries = getAllCountries();
                for (Country country : allCountries) {
                        countries.add(mapToDTO(country));
                }
                return countries;
        }


        public List<CountryDTO> getCountriesByNameOrCode(String name, String code) {
                List<CountryDTO> filtroCountries = new ArrayList<>();

                for (Country country : getAllCountries()) {
                        if ((name != null && country.getName().equalsIgnoreCase(name)) ||
                                (code != null && country.getCode().equalsIgnoreCase(code))) {
                                filtroCountries.add(mapToDTO(country));
                        }
                }

                return filtroCountries;
        }

        public List<CountryDTO> getCountriesByContinent(String region){
                List<CountryDTO> filtoContinente = new ArrayList<>();
                for (Country country : getAllCountries()) {
                        if(country.getRegion().equalsIgnoreCase(region) && country.getRegion() != null){
                                filtoContinente.add(mapToDTO(country));
                        }
                }
                return filtoContinente;
        }

        public List<CountryDTO> getCountriesByLanguage(String language){
                List<CountryDTO> filtroCountries = new ArrayList<>();
                for (Country country : getAllCountries()) {
                        if( country.getLanguages() != null && country.getLanguages().containsValue(language)){
                                System.out.println(filtroCountries.size());
                        }
                }
                return filtroCountries;
        }

        public CountryDTO getCountriesByMostBorders(){
                Country mostBorders = null;

                for (Country country : getAllCountries()) {
                        if(mostBorders == null) {
                                mostBorders = country;
                        } else if(country.getBorders().size() > mostBorders.getBorders().size()){
                                      mostBorders = country;
                        }
                }
                return mapToDTO(mostBorders);
        }

        public List<Country> saveCountries(int amountOfCountryToSave) {
                List<Country> countriesToSave = new ArrayList<>();

                // Seleccionando países aleatorios de la lista
                Random random = new Random();
                Set<Integer> selectedIndices = new HashSet<>();

                while (selectedIndices.size() < getAllCountries().size()) {
                        int index = random.nextInt(getAllCountries().size());
                        selectedIndices.add(index);
                }

                for (int index : selectedIndices) {
                        countriesToSave.add(getAllCountries().get(index));
                }

                return countriesToSave;
        }

}