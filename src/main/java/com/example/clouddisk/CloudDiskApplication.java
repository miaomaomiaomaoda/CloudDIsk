package com.example.clouddisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZERONJ
 */
@SpringBootApplication
@MapperScan("com.example.clouddisk.mapper")
public class CloudDiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudDiskApplication.class, args);
	}

}
