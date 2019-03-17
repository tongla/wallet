package wallet.wallet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("wallet.wallet.dao")
public class WalletApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WalletApplication.class, args);
	}
}

