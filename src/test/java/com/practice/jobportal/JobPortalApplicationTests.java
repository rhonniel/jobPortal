package com.practice.jobportal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;


class JobPortalApplicationTests {

	@Test
	void contextLoads() {


	 String pp="dsadasdas";
	 List<String> pap= Arrays.asList(pp.split(""));
	String e= pap.stream().sorted().collect(Collectors.joining());

	 System.out.println(e);

	}

}
