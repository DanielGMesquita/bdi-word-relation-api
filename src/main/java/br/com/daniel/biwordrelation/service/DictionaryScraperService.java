package br.com.daniel.biwordrelation.service;

import br.com.daniel.biwordrelation.dto.DictionaryScraperSynonymResponseDTO;
import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
import br.com.daniel.biwordrelation.exceptions.ErrorGettingPageDataException;
import br.com.daniel.biwordrelation.exceptions.NoContentException;
import br.com.daniel.biwordrelation.exceptions.NoSynonimsException;
import br.com.daniel.biwordrelation.manager.DictionaryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryScraperService {
  private final DictionaryManager dictionaryManager;

  public DictionaryScraperSynonymResponseDTO scrapeDictionaryForSynonyms(
      DictionaryScraperRequest request)
      throws ErrorGettingPageDataException, NoContentException, NoSynonimsException {
    log.info("Scraping dictionary for word: {}", request.getWord());
    DictionaryScraperSynonymResponseDTO response = this.dictionaryManager.getSynonyms(request);
    log.info("{} occurrences found for word: {}", response.getSynonyms().size(), request.getWord());
    return this.dictionaryManager.getSynonyms(request);
  }
}
