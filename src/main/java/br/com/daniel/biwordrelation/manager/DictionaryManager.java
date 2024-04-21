package br.com.daniel.biwordrelation.manager;

import br.com.daniel.biwordrelation.dto.DictionaryScraperSynonymResponseDTO;
import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
import br.com.daniel.biwordrelation.exceptions.ErrorGettingPageDataException;
import br.com.daniel.biwordrelation.exceptions.NoContentException;
import br.com.daniel.biwordrelation.exceptions.NoSynonimsException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DictionaryManager {
  @Value("${url.dictionary_web_page}")
  public String baseUrl;

  private static final String SYNONYM_URL = "sinonimos-e-antonimos/";
  private static final String HTML_TAG_CONTENT_LIST = "contentList";

  public DictionaryScraperSynonymResponseDTO getSynonyms(
      DictionaryScraperRequest dictionaryScraperRequest)
      throws ErrorGettingPageDataException, NoContentException, NoSynonimsException {

    DictionaryScraperSynonymResponseDTO dictionaryScraperSynonymResponseDTO =
        new DictionaryScraperSynonymResponseDTO();

    List<String> synonymsList = new ArrayList<>();

    String url = this.baseUrl + SYNONYM_URL + dictionaryScraperRequest.getWord();

    Element contentList = getPageData(url);

    if (contentList == null) {
      throw new NoContentException("No content found for the word provided.");
    }

    Elements items = contentList.getElementsByTag("li");

    for (Element item : items) {
      Element paragraph = item.getElementsByTag("p").first();

      if (paragraph == null) {
        throw new NoSynonimsException("No synonyms found for the word provided.");
      }
      List<Node> words = paragraph.childNodes();

      synonymsList.addAll(getSynonymsList(synonymsList, words, dictionaryScraperRequest));
    }

    dictionaryScraperSynonymResponseDTO.setSynonyms(synonymsList);

    return dictionaryScraperSynonymResponseDTO;
  }

  public static Element getPageData(String url) throws ErrorGettingPageDataException {
    Document doc;
    try {
      doc =
          Jsoup.connect(url)
              .userAgent(
                  "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
              .header("Accept-Language", "*")
              .get();
    } catch (IOException e) {
      throw new ErrorGettingPageDataException("Error getting page data.");
    }
    return doc.getElementById(HTML_TAG_CONTENT_LIST);
  }

  public static List<String> getSynonymsList(
      List<String> synonymsList,
      List<Node> words,
      DictionaryScraperRequest dictionaryScraperRequest) {
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
