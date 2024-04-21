package br.com.daniel.biwordrelation.manager;

import br.com.daniel.biwordrelation.dto.DictionaryScraperResponseDTO;
import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
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
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DictionaryManager {
  @Value("${url.dictionary_web_page}")
  public String baseUrl;

  public DictionaryScraperResponseDTO getSynonyms(
      DictionaryScraperRequest dictionaryScraperRequest) {

    String url = this.baseUrl + dictionaryScraperRequest.getWord();

    DictionaryScraperResponseDTO dictionaryScraperResponseDTO = new DictionaryScraperResponseDTO();

    List<String> synonyms = new ArrayList<>();

    try {
      Document doc =
          Jsoup.connect(url)
              .userAgent(
                  "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
              .header("Accept-Language", "*")
              .get();

      Element contentList = doc.getElementById("contentList");

      if (contentList == null) {
        throw new NoContentException("No content found for the word provided.");
      }

      Elements items = contentList.getElementsByTag("li");

      for (Element item : items) {
        Element paragraph = item.getElementsByTag("p").first();

        if (paragraph == null) {
          throw new NoSynonimsException("No synonyms found for the word provided.");
        }
        Elements words = paragraph.getElementsByTag("span, mark");

        for (Element word : words) {
          if (!word.tagName().equals("span") && !word.tagName().equals("mark")) {
            String[] wordArray = word.text().split("[,\\s]+");
            for (String w : wordArray) {
              if (!Objects.equals(w, dictionaryScraperRequest.getWord())) {
                synonyms.add(w);
              }
            }
          }
        }
      }
    } catch (IOException | NoSynonimsException | NoContentException e) {
      throw new RuntimeException(e);
    }

    dictionaryScraperResponseDTO.setSynonyms(synonyms);

    return dictionaryScraperResponseDTO;
  }
}
