package com.openclassrooms.SafetyNetAlerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetAlertsApplication {

//	@Autowired
//	private BusinessService bs;

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetAlertsApplication.class, args);
	}
}

//	@Override
//	public void run (String... args) throws Exception{
//		HelloWorld hw = bs.getHelloWorld();
//		System.out.println(hw);
//
//		Any obj = JsonIterator.deserialize("[1,2,3]");
//		System.out.println(obj.get(2));
//		int[] array = JsonIterator.deserialize("[1,2,3]", int[].class);
//		System.out.println(array[2]);
//
//	}


//	@SpringBootApplication
//	public class SafetyNetAlertsApplication implements CommandLineRunner {
//
//}

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@SpringBootApplication
//@RestController
//class DemoApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
//	}
//
//	@GetMapping("/hello")
//	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//		return String.format("Hello %s!", name);
//	}
//}