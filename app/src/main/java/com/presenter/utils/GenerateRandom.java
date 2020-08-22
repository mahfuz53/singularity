package com.presenter.utils;

import java.util.Random;

public class GenerateRandom {

	public static int getRandom(int max) {
		int rand = 0;
		final Random r = new Random();
		rand = 1 + r.nextInt(max);
		return rand;

	}

}
