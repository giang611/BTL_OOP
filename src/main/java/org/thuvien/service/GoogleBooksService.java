package org.thuvien.service;

import org.springframework.stereotype.Service;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thuvien.models.BookGoogle;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoogleBooksService {
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public List<BookGoogle> getBookInfo(String query) {
        List<BookGoogle> books = new ArrayList<>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String url = API_URL + encodedQuery + "&key=AIzaSyDP6arr-VbSOiIGufFV2Hsd0DRCQunFDrs";
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(request);

            String jsonResponse = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);

            if (root.has("items")) {
                for (JsonNode item : root.path("items")) {
                    JsonNode volumeInfo = item.path("volumeInfo");

                    String title = volumeInfo.path("title").asText("No Title");
                    String author = volumeInfo.path("authors").isArray() && volumeInfo.path("authors").size() > 0
                            ? volumeInfo.path("authors").get(0).asText("Unknown Author")
                            : "Unknown Author";
                    String description = volumeInfo.path("description").asText("No description available.");

                    String isbn = "No ISBN available";
                    if (volumeInfo.has("industryIdentifiers")) {
                        for (JsonNode identifier : volumeInfo.path("industryIdentifiers")) {
                            if ("ISBN_13".equals(identifier.path("type").asText())) {
                                isbn = identifier.path("identifier").asText();
                                break;
                            } else if ("ISBN_10".equals(identifier.path("type").asText())) {
                                isbn = identifier.path("identifier").asText();
                            }
                        }
                    }

                    books.add(new BookGoogle(title, author, description, isbn));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }
}
