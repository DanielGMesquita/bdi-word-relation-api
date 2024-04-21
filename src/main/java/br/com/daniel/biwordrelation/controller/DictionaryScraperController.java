package br.com.daniel.biwordrelation.controller;

import br.com.daniel.biwordrelation.dto.DictionaryScraperSynonymResponseDTO;
import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
import br.com.daniel.biwordrelation.exceptions.ErrorGettingPageDataException;
import br.com.daniel.biwordrelation.exceptions.NoContentException;
import br.com.daniel.biwordrelation.exceptions.NoSynonimsException;
import br.com.daniel.biwordrelation.service.DictionaryScraperService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/dictionary", produces = MediaType.APPLICATION_JSON_VALUE)
public class DictionaryScraperController {
  private DictionaryScraperService dictionaryScraperService;

  @GetMapping("/synonyms")
  public DictionaryScraperSynonymResponseDTO findSynonyms(DictionaryScraperRequest request)
      throws ErrorGettingPageDataException, NoContentException, NoSynonimsException {
    return this.dictionaryScraperService.scrapeDictionaryForSynonyms(request);
  }
}
