package BullsAndCows.app;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.SQLException;
import javax.activation.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App  {
    public static void main(String args[]) {
        SpringApplication.run(App.class, args);
    }

}
