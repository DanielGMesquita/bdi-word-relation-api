package br.com.daniel.biwordrelation.dto;

import br.com.daniel.biwordrelation.entities.NodeEntity;
import lombok.Data;

@Data
public class DictionaryScraperSynonymResponseDTO {
  private NodeEntity synonymsTree;
}
