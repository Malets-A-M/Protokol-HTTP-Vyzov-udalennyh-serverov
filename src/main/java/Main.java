import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Main {
    public static ObjectMapper  mapper = new ObjectMapper();
    public  static final String REMOTE_SERVICE_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
        CloseableHttpResponse response = httpClient.execute(request);
        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        List<Cat> cats = mapper.readValue(body, new TypeReference<>() {});
        cats.forEach(System.out::println);

        List<Cat> votesNULL = cats.stream().filter(cat -> (cat.getUpvotes() == 0)).toList();
        System.out.println("\n==================================" +
                           "\nОТФИЛЬТРОВАННЫЕ ПО НУЛЕВЫМ ГОЛОСАМ" +
                           "\n==================================");
        votesNULL.forEach(System.out::println);
    }
}
