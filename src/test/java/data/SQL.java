package data;

import lombok.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executor;

public class SQL {
    private static QueryRunner runner = new QueryRunner();
    private SQL() {

    }
    static String url = System.getProperty("db.url");
    static String user = System.getProperty("db.user");
    static String password = System.getProperty("db.password");


    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    public static class PaymentEntity {
        String id;
        String amount;
        String created;
        String status;
        String transaction_id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    public static class CreditRequestEntity {
        String id;
        String bank_id;
        String created;
        String status;
    }

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
        runner.execute(connection, "DELETE FROM credit_request_entity");
    }


    public static String getCardStatusForPayment() {
        String statusQuery = "SELECT status FROM payment_entity;";
        try (var conn = getConn()) {
            var cardStatus = runner.query(conn, statusQuery, new BeanHandler<>(PaymentEntity.class));
            return cardStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getCardStatusForCreditRequest() {
        String statusQuery = "SELECT status FROM credit_request_entity;";
        try (var conn = getConn()) {
            var cardStatus = runner.query(conn, statusQuery, new BeanHandler<>(CreditRequestEntity.class));
            return cardStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getAmountPayment() {
        String amountQuery = "SELECT amount FROM payment_entity;";
        try (var conn = getConn()) {
            var amount = runner.query(conn, amountQuery, new BeanHandler<>(PaymentEntity.class));
            return amount.getAmount();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

}
