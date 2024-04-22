package br.com.daniel.biwordrelation.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class NodeEntity {
  private String name;
  @Nullable private List<RootNodeEntity> children;
}
