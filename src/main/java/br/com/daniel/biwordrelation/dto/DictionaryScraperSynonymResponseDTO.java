package br.com.daniel.biwordrelation.dto;

import java.util.List;
import lombok.Data;

@Data
public class DictionaryScraperSynonymResponseDTO {
  private List<String> synonyms;
}
