# Дипломная работа профессии ТЕСТИРОВЩИК

В рамках дипломного проекта требовалось автоматизировать тестирование комплексного сервиса покупки тура, взаимодействующего с СУБД и API Банка.

База данных хранит информацию о заказах, платежах, статусах карт, способах оплаты.

Для покупки тура есть два способа: с помощью карты и в кредит. В приложении используются два отдельных сервиса оплаты: Payment Gate и Credit Gate.

[Ссылка на Дипломное задание](https://github.com/netology-code/qa-diploma).

### Тестовая документация

1. [План тестирования](https://github.com/okorskova/QA-DIPLOMA/blob/main/documents/Plan.md).
2. [Отчёт по итогам тестирования](https://github.com/okorskova/QA-DIPLOMA/blob/main/documents/Report.md).
3. [Отчет по итогам автоматизации](https://github.com/okorskova/QA-DIPLOMA/blob/main/documents/SummaryReport.md).

### Запуск приложения

#### Предусловия:
1. Скопировать репозиторий с Github по [ссылке](https://github.com/okorskova/QA-DIPLOMA).
2. Открыть проект в IntelliJ IDEA.
3. Установить и запустить Docker Desktop.

#### Запуск контейнеров:
1. Для запуска баз данных MySQL и PostgreSQL, а также NodeJS необходимо ввести в терминале команду:

   * docker-compose up
2. В новой вкладке терминала ввести следующую команду в зависимости от базы данных:

   * java -jar artifacts/aqa-shop.jar -Dspring.datasource.url=jdbc:mysql://localhost:3306/app - **для MySQL**

   * java -jar artifacts/aqa-shop.jar -Dspring.datasource-postgresql.url=jdbc:postgresql://localhost:5432/app - **для PostgreSQL**

3. Убедиться в готовности системы. Приложение должно быть доступно по адресу:
   * http://localhost:8080/

#### Запуск тестов:
В новой вкладке терминала ввести команду: 

* .\gradlew clean test

#### Перезапуск приложения и тестов:
Если требуется перезапустить приложение и/или тесты (например, для другой БД), необходимо выполнить остановку работы в запущенных ранее вкладках терминала нажав в них Ctrl+С.

### Формирование отчета AllureReport по результатам тестирования
В новой вкладке терминала ввести команду:

* .\gradlew allureServe 

Сгенерированный отчет откроется в браузере автоматически.
