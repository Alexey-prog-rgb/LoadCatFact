import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

public class CatFactLoader {
    private String URL;
    private List<CatFact> catFacts;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public CatFactLoader(String URL) {
        this.URL = URL;
        catFacts = null;
    }

    public boolean load() {
        catFacts = null;
        try(CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("CatFactLoader")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build()){
            HttpGet httpGet = new HttpGet(URL);
            httpGet.setHeader("Accept", "application/json");
            try(CloseableHttpResponse resonse = httpClient.execute(httpGet)){
                //System.out.println("Строка заголовка ответа: " + resonse.getStatusLine());
                //System.out.println("Заголовки ответа: ");
                //for(Header header : resonse.getAllHeaders()){
                //    System.out.println(header.getName() + ":" + header.getValue());
                //}
                catFacts = objectMapper.readValue(resonse.getEntity().getContent(), new TypeReference<List<CatFact>>(){});
            }catch (Exception e){
                System.out.println("Ошибка при отправне http запроса: " + e);
                e.printStackTrace();
                return false;
            }
        }catch (Exception e){
            System.out.println("Ошибка при создании HTTP клиента: " + e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<CatFact> getCatFacts() {
        return catFacts;
    }
}
