package br.com.daniel.biwordrelation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryScraperService {

  public void scrapeDictionary(String word) {
    log.info("Scraping dictionary for word: {}", word);
  }
}
