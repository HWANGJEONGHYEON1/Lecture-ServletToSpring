package hello.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTEst {

    @Test
    void dataSourceConnectionPool() throws SQLException, InterruptedException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setPoolName("MyPool");
        dataSource.setMaximumPoolSize(10);

        useDataSource(dataSource);
        Thread.sleep(1000);
    }

    @Test
    void driverManager() throws SQLException {
        final Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        final Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection ={}, class={}", con1, con1.getClass());
        log.info("connection ={}, class={}", con2, con2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    private void useDataSource(DataSource dataSource) throws SQLException {
        final Connection con1 = dataSource.getConnection();
        final Connection con2 = dataSource.getConnection();
        final Connection con3 = dataSource.getConnection();
        final Connection con4 = dataSource.getConnection();
        final Connection con5 = dataSource.getConnection();
        final Connection con6 = dataSource.getConnection();
        final Connection con7 = dataSource.getConnection();
        final Connection con8 = dataSource.getConnection();
        final Connection con9 = dataSource.getConnection();
        final Connection con10 = dataSource.getConnection();
        final Connection con11 = dataSource.getConnection();
        final Connection con12 = dataSource.getConnection();
        final Connection con13 = dataSource.getConnection();

        log.info("connection ={}, class={}", con1, con1.getClass());
        log.info("connection ={}, class={}", con2, con2.getClass());
    }
}
