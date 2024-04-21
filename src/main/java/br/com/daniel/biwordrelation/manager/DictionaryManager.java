package br.com.daniel.biwordrelation.manager;

import br.com.daniel.biwordrelation.dto.DictionaryScraperSynonymResponseDTO;
import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
import br.com.daniel.biwordrelation.exceptions.ErrorGettingPageDataException;
import br.com.daniel.biwordrelation.exceptions.NoContentException;
import br.com.daniel.biwordrelation.exceptions.NoSynonimsException;
import br.com.daniel.biwordrelation.utils.DictionaryPageScraper;
import br.com.daniel.biwordrelation.utils.HtmlElementProcessor;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DictionaryManager {
  private final DictionaryPageScraper pageScraper;

  public DictionaryScraperSynonymResponseDTO getSynonyms(
      DictionaryScraperRequest dictionaryScraperRequest)
      throws ErrorGettingPageDataException, NoContentException, NoSynonimsException {
    Element contentList = this.pageScraper.getPageData(dictionaryScraperRequest);

    List<String> synonymsList = new ArrayList<>();

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
      synonymsList.addAll(HtmlElementProcessor.getSynonymsList(words, dictionaryScraperRequest));
    }

    DictionaryScraperSynonymResponseDTO responseDTO = new DictionaryScraperSynonymResponseDTO();
    responseDTO.setSynonyms(synonymsList);

    return responseDTO;
  }
}
