package br.com.daniel.biwordrelation.utils;

import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
import br.com.daniel.biwordrelation.exceptions.ErrorGettingPageDataException;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DictionaryPageScraper {
  @Value("${url.dictionary_web_page}")
  public String baseUrl;

  private static final String SYNONYM_URL = "sinonimos-e-antonimos/";
  private static final String HTML_TAG_CONTENT_LIST = "contentList";

  public Element getPageData(DictionaryScraperRequest dictionaryScraperRequest)
      throws ErrorGettingPageDataException {
    String url = this.baseUrl + SYNONYM_URL + dictionaryScraperRequest.getWord();
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
}
