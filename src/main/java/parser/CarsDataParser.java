package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CarsDataParser {

    public static void main(String[] args) {
        for (int i = 1, count = 1; i <= 10; ++i) {
            System.out.println("PAGE " + i);
            try {
                String url = (i == 1) ? "https://auto.drom.ru" : "https://auto.drom.ru/all/page" + i;

                Document document = Jsoup.connect(url)
                        .timeout(5000)
                        .get();

                Elements cars = document.getElementsByClass("css-xb5nz8 ewrty961");
                for (Element car : cars) {

                    System.out.println("CAR №" + count);
                    String[] nameAndYear = car
                            .select("[data-ftid='bull_title']")
                            .text()
                            .split(",");

                    String name = nameAndYear[0];
                    String year = nameAndYear[1]
                            .substring(1);      // deleting unnecessary 'space' at the beginning of string
                    System.out.println("Марка: " + name);
                    System.out.println("Год выпуска: " + year);

                    String fuelType = car
                            .select("[data-ftid='bull_description-item']")
                            .eq(1)
                            .text()
                            .replaceAll(".$", "");                    // deleting unnecessary ','
                    System.out.println("Тип топлива: " + fuelType);

                    String transmissionType = car
                            .select("[data-ftid='bull_description-item']")
                            .eq(2)
                            .text()
                            .replaceAll(".$", "");                    // deleting unnecessary ','
                    System.out.println("Тип коробки передач: " + transmissionType);

                    String location = car
                            .select("[data-ftid='bull_location']")
                            .text();
                    System.out.println("Локация: " + location);

                    String price = car
                            .select("[data-ftid='bull_price']")
                            .text();
                    System.out.println("Цена: " + price);

                    String link = car
                            .select("a")
                            .attr("href");
                    System.out.println("URL: " + link);

                    System.out.println();
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
