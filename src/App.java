import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // Buscar os dados da API
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair dados
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir os dados
        StickMaker stickMaker = new StickMaker();
        for (Map<String,String> filme : listaDeFilmes) {

            String urlImage = filme.get("image");
            String title = filme.get("title");
            
            InputStream inputStream = new URL(urlImage).openStream();
            String fileName = "src/img/" + title + ".png";

            stickMaker.create(inputStream, fileName);

            System.out.println(title);
            System.out.println();
        }

    }
}
