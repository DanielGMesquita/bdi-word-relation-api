package br.com.daniel.biwordrelation.dto;

import java.util.List;
import lombok.Data;

@Data
public class DictionaryScraperResponseDTO {
  private List<String> synonyms;
}
