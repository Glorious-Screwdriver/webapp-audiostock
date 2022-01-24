package com.audiostock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class WymApplication {

	public static String dataDir;

	public static void main(String[] args) throws IOException {
		dataDir = new File(".").getCanonicalPath() + "\\data\\";
		SpringApplication.run(WymApplication.class, args);
	}

}
