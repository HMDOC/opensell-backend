package com.opensell;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.opensell.service.timeservice.Job;
import com.opensell.service.timeservice.JobAction;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		//Thread thread = new Thread(new ThreadCleanUp(1000));

		Job threadCleanUp = new Job(new JobAction() {
			@Override
			public void task() {
				System.out.println("Hamid!");
			}
		}, 10000);
		threadCleanUp.run();
	}
}