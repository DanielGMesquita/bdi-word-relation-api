
# Dictionary Scraper

O Dictionary Scraper é uma ferramenta que coleta palavras e seus significados de um site de dicionário. A aplicação consiste em um serviço REST que permite a busca de palavras e seus significados e palavras relacionadas.

A aplicação consiste em endpoints que fornecem significados, sinônimos, antônimos e palavras relacionadas, assim como frases que podem utilizar a palvra. Tudo isso buscado a partir de protocolo HTTP.

Base utilizada: https://dicionariocriativo.com.br/


## Documentação da API
No momento só é possível buscar os sinônimos e palavras relacionadas a palavra buscada.
#### Retorna relacionamento entre palavras


```http
  GET /dictionary/synonyms
```

| Parâmetro | Tipo     | Descrição                                              |
|:----------|:---------|:-------------------------------------------------------|
| `word`    | `String` | **Obrigatório**. A palavra a ser buscada no dicionário |

#### Formato da saída
```json
{
  "name": "flare",
  "children": [
    {
      "name": "analytics",
      "children": [
        {
          "name": "cluster",
          "children": [
            {"name": "AgglomerativeCluster", "value": 3938},
            {"name": "CommunityStructure", "value": 3812},
            {"name": "HierarchicalCluster", "value": 6714},
            {"name": "MergeEdge", "value": 743}
          ]
        },
        {
          "name": "graph",
          "children": [
            {"name": "BetweennessCentrality", "value": 3534},
            {"name": "LinkDistance", "value": 5731},
            {"name": "MaxFlowMinCut", "value": 7840},
            {"name": "ShortestPaths", "value": 5914},
            {"name": "SpanningTree", "value": 3416}
          ]
        },
        {
          "name": "optimization",
          "children": [
            {"name": "AspectRatioBanker", "value": 7074}
          ]
        }
      ]
    },
    // Resto do JSON omitido por brevidade
  ]
}
```
## Stack utilizada

### Back-end
- Java
- Spring Boot
- Maven

### Implantação em produção
- Deploy: Railway - https://dictionary-api.up.railway.app/

## Como executar o projeto

Pré-requisito: Java 17

```bash
# clonar repositório
git clone git@github.com:DanielGMesquita/bdi-word-relation-api.git

# executar o projeto
./mvnw spring-boot:run
```

## Autores

Daniel Mesquita
- [@DanielGMesquita](https://www.github.com/DanielGMesquita)
- [Linkedin](https://www.linkedin.com/in/danielgmesquita/)

