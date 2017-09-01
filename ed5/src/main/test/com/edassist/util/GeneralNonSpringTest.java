package com.edassist.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneralNonSpringTest {

	public GeneralNonSpringTest() {
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void test001() throws Exception {
		try {
			System.out.println("deanhere deanhere");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
