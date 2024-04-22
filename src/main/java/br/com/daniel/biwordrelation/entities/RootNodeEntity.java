package br.com.daniel.biwordrelation.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RootNodeEntity {
  private String name;
  private List<ChildrenNodeEntity> children;
}
