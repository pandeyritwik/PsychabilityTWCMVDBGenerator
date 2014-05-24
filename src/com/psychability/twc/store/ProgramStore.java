package com.psychability.twc.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.psychability.twc.entities.Program;

public class ProgramStore {

	private static List<Program> programs;
	private static int position;

	public static void setPrograms(int programCount) {
		Random rand = new Random();
		if (programCount == 0) {
			programCount = 10;
		}
		programs = new ArrayList<Program>();
		for (int i = 0; i < programCount; i++) {
			Program program = new Program();
			program.setProgramId(1000 + i);
			program.setTitle("Random Program " + String.valueOf(i + 1));
			program.setRuntime(rand.nextBoolean() ? 1800 : 3600);
			program.setRating(rand.nextBoolean() ? "Y" : rand
					.nextBoolean() ? "Y7" : rand.nextBoolean() ? "PG13" : "MA");
			List<String> genres = new ArrayList<String>();
			if (rand.nextBoolean()) {
				genres.add("Horror");
			}
			if (rand.nextBoolean()) {
				genres.add("Comedy");
			}
			if (rand.nextBoolean()) {
				genres.add("Action");
			}
			if (rand.nextBoolean()) {
				genres.add("Thriller");
			}
			if (rand.nextBoolean()) {
				genres.add("Suspense");
			}
			if (rand.nextBoolean()) {
				genres.add("Teen");
			}
			if (rand.nextBoolean()) {
				genres.add("Classic");
			}
			if (genres.size() == 0) {
				genres.add("Violence");
			}
			program.setProgramGenres(genres);
			programs.add(program);
		}
		position = 0;
	}

	public static synchronized Program getNextProgram() {
		if (position == programs.size()) {
			position = 0;
		}
		return programs.get(position++);
	}
}
