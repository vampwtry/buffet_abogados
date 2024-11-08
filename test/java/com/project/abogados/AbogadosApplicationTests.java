package com.project.abogados;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.testng.annotations.Test;

@SpringBootTest
class AbogadosApplicationTests {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
/*
	@Test

	public void  testCifrado(){
		String rawPass = "1000064655Js";
		String encodePass = passwordEncoder.encode(rawPass);
		String encrypPass= "$2a$10$WtTySoKEVixpMtJh3.NSn.W19MJl0uaO5ROzQts0D9/cNgLL8S6hS";

		boolean isMatch = passwordEncoder.matches(rawPass,encrypPass);

		System.out.println(encodePass);
		System.out.println("Conincide?" + isMatch);

	}
	*/

}
