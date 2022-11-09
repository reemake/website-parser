## Парсер данных веб-сайта  
**Цель задания:** необходимо спарсить не менее 200-300 записей с какого-либо сайта. У каждой из записей должно быть не менее пяти параметров + URL.

Для решения задачи было разработано консольное приложение на языке Java. Для парсинга использовалась библиотека [Jsoup](https://jsoup.org/apidocs/).    
Объектом парсинга был выбран сайт объявлений по продаже автомобилей [auto.drom.ru](https://auto.drom.ru).  
Результат парсинга заносится в файл *cars.xls*, который автоматически создается по пути *src/main/resources*. Для записи данных в Excel-таблицу использовалась библиотека [Apache POI](https://poi.apache.org/).  
Процесс парсинга сопровождается логированием ([slf4j](https://www.slf4j.org/) + [log4j](https://logging.apache.org/log4j/2.x/)).

## Принцип работы
1. По значению атрибута class, находится контейнер с данными об автомобиле: 
```java
Elements cars = document.getElementsByClass("css-xb5nz8 ewrty961");
```
![image](https://user-images.githubusercontent.com/90447198/200879772-5ab5e146-0725-4b58-916e-cb4e37dbe106.png)

2. Для каждого автомобиля происходит отбор следующих параметров:
- Марка и год выпуска
```java
for (Element car : cars) {
     String[] brandAndYear = car
             .select("[data-ftid='bull_title']")
             .text()
             .split(",");
```
![image](https://user-images.githubusercontent.com/90447198/200880000-45c31fb8-ca28-497d-a0ac-ea6aaf2abc6a.png)

- Тип топлива
```java
String fuelType = car
      .select("[data-ftid='bull_description-item']")
      .eq(1)
      .text()
      .replaceAll(".$", "");
```
![image](https://user-images.githubusercontent.com/90447198/200880158-1ec76c4f-9a8e-4f5d-9650-e6c26e7401b2.png)

- Тип КПП
```java
String transmissionType = car
      .select("[data-ftid='bull_description-item']")
      .eq(2)
      .text()
      .replaceAll(".$", "");
```
![image](https://user-images.githubusercontent.com/90447198/200880348-ad4676cc-54de-4d8e-97a7-2fa93de8b653.png)

- Город продажи
```java
String location = car
      .select("[data-ftid='bull_location']")
      .text();
```
![image](https://user-images.githubusercontent.com/90447198/200880607-c6c75dde-72ee-4818-910e-12a9b63558ad.png)

- Цена
```java
String price = car
      .select("[data-ftid='bull_price']")
      .text();
```
![image](https://user-images.githubusercontent.com/90447198/200880979-0d702d49-f2b5-4c7e-8875-8d0254b2b3d9.png)

- URL
```java
String link = car
      .select("a")
      .attr("href");
```
![image](https://user-images.githubusercontent.com/90447198/200879772-5ab5e146-0725-4b58-916e-cb4e37dbe106.png)

## Фрагмент файла с результатами парсинга:
![image](https://user-images.githubusercontent.com/90447198/200912602-2f04e796-a734-4b3d-85c8-606195a3519f.png)

## Запуск приложения через консоль
Т.к. на сайте предусмотрена пагинация, был введен дополнительный параметр - pages. На одной странице сайта расположено 20 объявлений, поэтому по умолчанию pages равен 10 (для выполнения условия задачи парсинга не менее 200 записей). Для запуска приложения со значением параметра pages по умолчанию, выполнить следующие шаги:
1. Скачать репозиторий  
2. В cmd выполнить:  
```cd [ваш_путь]/website-parser```  
```mvn clean install```   
```mvn exec:exec```

Также, предусмотрена возможность задать количество страниц для парсинга. Для этого нужно выполнить:  
```mvn exec:exec -DargumentPages=[кол-во страниц]```
