package br.com.daniel.biwordrelation.utils;

import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

@UtilityClass
public class HtmlElementProcessor {
  public static void populateSynonymsList(
      List<Node> words,
      DictionaryScraperRequest dictionaryScraperRequest,
      List<String> synonymsList) {
    for (Node node : words) {
      if (node instanceof TextNode) {
        TextNode textnode = (TextNode) node;
        String[] wordArray = textnode.text().split("[,\\s]+");
        for (String word : wordArray) {
          if (!Objects.equals(word, dictionaryScraperRequest.getWord())
              && !word.isBlank()
              && !synonymsList.contains(word)) {
            synonymsList.add(word);
          }
        }
      }
    }
  }
}
