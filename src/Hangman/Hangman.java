package Hangman;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Game Hangman which picks a word from string array and asks user for a letter,
 * after uses "solves" the word it shows him how many mistakes he had.
 *
 */
public class Hangman {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		// string array that contains words that might be randomized
		String[] words = { "strong", "compile", "random", "vehicle",
				"powerful", "random", "volatile", "painful" };
		// word user will guess is selected randomly from the array
		String word = words[(int) (Math.random() * words.length)];
		// char array that will be shown to user
		char[] forUser = new char[word.length()];
		Arrays.fill(forUser, '*');
		System.out.println(forUser);
		boolean solved = false;
		int count = 0;
		// loop that will run until there are no more "*" signs in the char
		// array
		while (!solved) {
			solved = true;
			System.out.println("Enter letter:");
			char ch = input.next().charAt(0);
			boolean letterFound = false;
			// if letter is found we replace * with that letter in char array
			for (int i = 0; i < forUser.length; i++) {
				if (ch == word.charAt(i)) {
					forUser[i] = ch;
					letterFound = true;
				}
			}
			// if users letter is not part of the word we increment counter used
			// for counting mistakes
			if (!letterFound) {
				System.out.println("Letter not found, try again:");
				count++;
			}
			System.out.println(forUser);
			//if there is * left in the word it's not solved yet
			for (int i = 0; i < forUser.length; i++) {
				if (forUser[i] == '*')
					solved = false;
			}
		}
		//printing out how many mistakes user had in the end
		System.out.println("You won!\nYou had " + count + " mistakes.");

	}
}
