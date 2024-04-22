package br.com.daniel.biwordrelation.manager;

import br.com.daniel.biwordrelation.dto.DictionaryScraperSynonymResponseDTO;
import br.com.daniel.biwordrelation.entities.ChildrenNodeEntity;
import br.com.daniel.biwordrelation.entities.DictionaryScraperRequest;
import br.com.daniel.biwordrelation.entities.NodeEntity;
import br.com.daniel.biwordrelation.entities.RootNodeEntity;
import br.com.daniel.biwordrelation.exceptions.ErrorGettingPageDataException;
import br.com.daniel.biwordrelation.exceptions.NoContentException;
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
  private static final List<String> allWords = new ArrayList<>();

  public DictionaryScraperSynonymResponseDTO getSynonyms(
      DictionaryScraperRequest dictionaryScraperRequest)
      throws ErrorGettingPageDataException, NoContentException {
    Element contentList = this.pageScraper.getPageData(dictionaryScraperRequest);
    String baseWord = dictionaryScraperRequest.getWord();

    if (contentList == null) {
      throw new NoContentException("No content found for the word provided.");
    }

    Elements items = contentList.getElementsByTag("li");

    List<RootNodeEntity> rootChildren = new ArrayList<>();

    for (Element item : items) {
      List<String> rootChildrenList = new ArrayList<>();
      Element paragraph = item.getElementsByTag("p").first();

      List<Node> words = new ArrayList<>();
      if (paragraph != null) {
        words.addAll(paragraph.childNodes());
      }

      HtmlElementProcessor.populateSynonymsList(
          words, baseWord, dictionaryScraperRequest, rootChildrenList);

      allWords.addAll(rootChildrenList);

      for (String synonym : rootChildrenList) {
        DictionaryScraperRequest synonymRequest = new DictionaryScraperRequest();
        synonymRequest.setWord(synonym);
        Element synonymElementList = this.pageScraper.getPageData(synonymRequest);

        Elements childItems = synonymElementList.getElementsByTag("li");

        List<ChildrenNodeEntity> clusterChildren = new ArrayList<>();

        for (Element rootItem : childItems) {
          List<String> clusterChildrenList = new ArrayList<>();
          Element rootParagraph = rootItem.getElementsByTag("p").first();

          List<Node> rootWords = new ArrayList<>();
          if (rootParagraph != null) {
            rootWords.addAll(rootParagraph.childNodes());
          }

          HtmlElementProcessor.populateSynonymsList(
              rootWords, baseWord, synonymRequest, clusterChildrenList);
          allWords.addAll(clusterChildrenList);

          for (String clusterWord : clusterChildrenList) {
            ChildrenNodeEntity clusterNode = new ChildrenNodeEntity(clusterWord, 0);
            if (!clusterChildren.contains(clusterNode)) {
              clusterChildren.add(clusterNode);
            }
          }
        }

        RootNodeEntity rootChild = new RootNodeEntity(synonym, clusterChildren);
        if (!rootChildren.contains(rootChild)) {
          rootChildren.add(rootChild);
        }
      }
    }

    for (RootNodeEntity clusterNode : rootChildren) {
      for (ChildrenNodeEntity clusterChild : clusterNode.getChildren()) {
        String nodeName = clusterChild.getName();
        clusterChild.setValue((int) allWords.stream().filter(str -> str.equals(nodeName)).count());
      }
    }

    NodeEntity root = new NodeEntity(dictionaryScraperRequest.getWord(), rootChildren);
    DictionaryScraperSynonymResponseDTO responseDTO = new DictionaryScraperSynonymResponseDTO();
    responseDTO.setSynonymsTree(root);

    return responseDTO;
  }
}
