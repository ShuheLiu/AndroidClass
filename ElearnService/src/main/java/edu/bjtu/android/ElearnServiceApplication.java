package edu.bjtu.android;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;


@SpringBootApplication

@MapperScan("edu.bjtu.android.dao")
public class ElearnServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElearnServiceApplication.class, args);
	}

}
