package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import writer.ExcelFileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CarsDataParser {

    private ExcelFileWriter excelFileWriter;

    public CarsDataParser(ExcelFileWriter excelFileWriter) {
        this.excelFileWriter = excelFileWriter;
    }

    public void parse() {

        Map<Integer, String[]> carsData = new HashMap<>();

        for (int page = 1, carCount = 1; page <= 10; page++) {

            try {
                String url = (page == 1) ? "https://auto.drom.ru" : "https://auto.drom.ru/all/page" + page;

                Document document = Jsoup.connect(url)
                        .timeout(5000)
                        .get();

                Elements cars = document.getElementsByClass("css-xb5nz8 ewrty961");
                for (Element car : cars) {

                    String[] brandAndYear = car
                            .select("[data-ftid='bull_title']")
                            .text()
                            .split(",");

                    String brand = brandAndYear[0];
                    String year = brandAndYear[1]
                            .substring(1);      // deleting unnecessary 'space' at the beginning of string

                    String fuelType = car
                            .select("[data-ftid='bull_description-item']")
                            .eq(1)
                            .text()
                            .replaceAll(".$", "");                    // deleting unnecessary ','

                    String transmissionType = car
                            .select("[data-ftid='bull_description-item']")
                            .eq(2)
                            .text()
                            .replaceAll(".$", "");                    // deleting unnecessary ','

                    String location = car
                            .select("[data-ftid='bull_location']")
                            .text();

                    String price = car
                            .select("[data-ftid='bull_price']")
                            .text();

                    String link = car
                            .select("a")
                            .attr("href");

                    carsData.put(
                            carCount,
                            new String[]{ brand, year, fuelType, transmissionType, location, price, link });

                    carCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            excelFileWriter.write(carsData);
        }
    }

}
