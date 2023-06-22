package data;

import com.codeborne.selenide.Condition;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }


    @SneakyThrows
    public static String getPayStatus() {
        var sql = "select status from order_entity o\n" +
                "join payment_entity pe on o.payment_id = pe.transaction_id\n" +
                "order by o.created desc\n" +
                "limit 1";
        var conn = getConn();
        var status = runner.query(conn, sql, new ScalarHandler<String>());
        return status;
    }

    @SneakyThrows
    public static String getCreditPayStatus() {
        var sql = "select status from order_entity o\n" +
                "join credit_request_entity pe on o.payment_id = pe.bank_id\n" +
                "order by o.created desc\n" +
                "limit 1";
        var conn = getConn();
        var status = runner.query(conn, sql, new ScalarHandler<String>());
        return status;
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "Delete from payment_entity");
        runner.execute(connection, "Delete from credit_request_entity");
        runner.execute(connection, "Delete from order_entity");
    }


}
