import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class CarBrandsApiTest {

    @Test
    public void testCarBrandsResponse() throws Exception {
        // Створюємо клієнта
        HttpClient client = HttpClient.newHttpClient();

        // Створюємо GET-запит
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://qauto.forstudy.space/api/cars/brands"))
                .GET()
                .build();

        // Відправляємо запит і отримуємо відповідь
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Отримуємо код відповіді
        int statusCode = response.statusCode();
        // Парсимо тіло у форматі JSON
        JSONObject responseBody = new JSONObject(response.body());
        JSONArray brandsArray = responseBody.getJSONArray("data");

        // Перевіряємо, чи містить об'єкт з id = 1 та title = "Audi"
        boolean containsAudi = false;
        for (int i = 0; i < brandsArray.length(); i++) {
            JSONObject brand = brandsArray.getJSONObject(i);
            if (brand.getInt("id") == 1 && "Audi".equals(brand.getString("title"))) {
                containsAudi = true;
                break;
            }
        }

        // Використовуємо SoftAssert
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(statusCode, 200, "Перевірка статус-коду");
        softAssert.assertTrue(containsAudi, "Перевірка наявності 'id: 1' та 'title: Audi' у тілі");

        // Завершення перевірок
        softAssert.assertAll();
    }
}
