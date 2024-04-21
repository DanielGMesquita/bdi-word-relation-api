package br.com.daniel.biwordrelation.utils;

import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.stereotype.Component;

@Component
public class HtmlElementProcessor {
  public static List<String> getSynonymsList(
      List<Node> words, DictionaryScraperRequest dictionaryScraperRequest) {
    List<String> synonymsList = new ArrayList<>();
    for (Node node : words) {
      if (node instanceof TextNode) {
        TextNode textnode = (TextNode) node;
        String[] wordArray = textnode.text().split("[,\\s]+");
        for (String word : wordArray) {
          if (!Objects.equals(word, dictionaryScraperRequest.getWord()) && !word.isBlank()) {
            synonymsList.add(word);
          }
        }
      }
    }
    return synonymsList;
  }
}
