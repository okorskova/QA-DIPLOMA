package data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {
    static String url = System.getProperty("db.url");
    static String user = System.getProperty("db.user");
    static String password = System.getProperty("db.password");

    @Data
    @NoArgsConstructor

    public static class PaymentEntity {
        String id;
        String amount;
        String created;
        String status;
        String transaction_id;
    }

    @Data
    @NoArgsConstructor

    public static class CreditRequestEntity {
        String id;
        String bank_id;
        String created;
        String status;
    }
    private static Connection connection;
    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }
    public static void clearDataBase() {
        val deleteOrderEntity = "DELETE FROM order_entity";
        val deletePaymentEntity = "DELETE FROM payment_entity";
        val deleteCreditRequestEntity = "DELETE FROM credit_request_entity";
        val runner = new QueryRunner();
        try (val conn = getConnection()) {
            runner.update(conn, deleteOrderEntity);
            runner.update(conn, deletePaymentEntity);
            runner.update(conn, deleteCreditRequestEntity);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static String getCardStatusForPayment() {
        String statusQuery = "SELECT status FROM payment_entity";
        val runner = new QueryRunner();
        try (Connection connection = getConnection()) {
            val cardStatus = runner.query(connection, statusQuery, new BeanHandler<>(PaymentEntity.class));
            return cardStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getCardStatusForCreditRequest() {
        String statusQuery = "SELECT status FROM credit_request_entity";
        val runner = new QueryRunner();
        try (Connection connection = getConnection()) {
            val cardStatus = runner.query(connection, statusQuery, new BeanHandler<>(CreditRequestEntity.class));
            return cardStatus.getStatus();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public static String getAmountPayment() {
        String amountQuery = "SELECT amount FROM payment_entity";
        val runner = new QueryRunner();
        try (Connection connection = getConnection()) {
            val amount = runner.query(connection, amountQuery, new BeanHandler<>(PaymentEntity.class));
            return amount.getAmount();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

}
