package Battleships;

import java.util.Arrays;
import java.util.Scanner;

/**
 * game Battleship is a guessing game for two players. In our version player
 * sets his ships on the boards and we randomize ships for computer. After that
 * we switch turns between player and pc(turn for pc is instant).
 */
public class Battleship {

	public static void main(String[] args) {
		// players field
		char[][] matrix = new char[10][10];
		// field with enemy ships, we can't see it
		char[][] enemy = new char[10][10];
		// field tha belong to enemy, we see our hits and misses
		char[][] enemyField = new char[10][10];
		// filling both visible fields with "waves"
		for (int i = 0; i < matrix.length; i++) {
			Arrays.fill(matrix[i], '~');
			Arrays.fill(enemyField[i], '~');
		}
		System.out.println("Enemy field");
		// printing initial fields
		printMatrix(enemyField);
		System.out.println("\n\n Players field");
		printMatrix(matrix);
		randomize(enemy);
		userShips(matrix);
		boolean win = false;
		int playerTurn = 1;
		// player and pc take turns until there is a winner
		while (!win) {
			// in every turn we print both fields
			System.out.println("Enemy field");
			printMatrix(enemyField);
			System.out.println("\nYour field");
			printMatrix(matrix);
			shoot(matrix, enemy, enemyField, playerTurn);
			if (playerTurn % 2 != 0) {
				// after every turn we check for winner(if players turn we check
				// for enemys field)
				win = victory(enemy);
				if (win)
					System.out.println("All ships destroyed, you won!");
			} else {
				// on pc turn we check for existing ships on players field
				win = victory(matrix);
				if (win)
					System.out.println("All ships defeated, you lost!");
			}
			// playerTurn varibale is used to switch turns
			playerTurn++;
		}

	}

	/**
	 * method used to invoke methods that places ship for pc (we randomize
	 * position H or V first)
	 * 
	 * @param a
	 *            - 2D array
	 */
	public static void randomize(char[][] a) {
		boolean set = false;
		while (!set) {
			set = placeShip(a, ((int) (Math.random() * 2)), 2);
		}
		set = false;
		while (!set) {
			set = placeShip(a, ((int) (Math.random() * 2)), 3);
		}
		set = false;
		while (!set) {
			set = placeShip(a, ((int) (Math.random() * 2)), 3);
		}
		set = false;
		while (!set) {
			set = placeShip(a, ((int) (Math.random() * 2)), 4);
		}
		set = false;
		while (!set) {
			set = placeShip(a, ((int) (Math.random() * 2)), 5);
		}
	}

	/**
	 * method that invokes method that will ask user to place his ships,
	 * depending on the size, also method prints updated matrix after every
	 * placement
	 * 
	 * @param a
	 *            - 2D matrix
	 */
	/**
	 * invoking method that asks user for ship placement depending on the ship
	 * size
	 * 
	 * @param a
	 *            - 2D array(players field)
	 */
	public static void userShips(char[][] a) {
		placeShipsUser(a, 2);
		printMatrix(a);
		placeShipsUser(a, 3);
		printMatrix(a);
		placeShipsUser(a, 3);
		printMatrix(a);
		placeShipsUser(a, 4);
		printMatrix(a);
		placeShipsUser(a, 5);
		printMatrix(a);
	}

	/**
	 * method that places randomly sets PC ships
	 * 
	 * @param a
	 *            - 2D matrix
	 * @param vert
	 *            - randomized number 0 or 1
	 * @param ship
	 *            - ship size
	 * @return - returns true if placement was successful
	 */
	public static boolean placeShip(char[][] a, int vert, int ship) {
		// vert 0 horisontal
		if (vert == 0) {
			int row = (int) (Math.random() * 10);
			int column = (int) (Math.random() * (10 - ship));
			if (isFreeHorisontal(row, column, a, ship)) {
				for (int i = 0; i < ship; i++) {
					a[row][column + i] = 'O';
				}
				return true;
			} else {
				return false;
			}
		}
		if (vert == 1) {
			int row = (int) (Math.random() * (10 - ship));
			int column = (int) (Math.random() * 10);
			if (isFreeVertical(row, column, a, ship)) {
				for (int i = 0; i < ship; i++) {
					a[row + i][column] = 'O';
				}
				return true;
			}
		} else {
			return false;
		}
		return true;

	}

	/**
	 * method that checks if it's possible to place ship horizontaly from
	 * initial coordinate
	 * 
	 * @param row
	 *            - row coordinate
	 * @param column
	 *            - column coordinate
	 * @param a
	 *            - 2D matrix
	 * @param ship
	 *            - ship size
	 * @return - returns true if there are no other ships in path
	 */
	public static boolean isFreeHorisontal(int row, int column, char[][] a,
			int ship) {
		for (int i = 0; i < ship; i++) {
			if (a[row][column + i] == 'O')
				return false;
		}
		return true;

	}

	/**
	 * method that checks if it's possible to place ship verticaly from initial
	 * coordinate
	 * 
	 * @param row
	 *            - row coordinate
	 * @param column
	 *            - column coordinate
	 * @param a
	 *            - 2D matrix
	 * @param ship
	 *            - ship size
	 * @return - returns true if there are no other ships in path
	 */
	public static boolean isFreeVertical(int row, int column, char[][] a,
			int ship) {
		for (int i = 0; i < ship; i++) {
			if (a[row + i][column] == 'O') {
				return false;
			}
		}
		return true;
	}

	/**
	 * method that deals with placing user ships(user is first asked for placing
	 * mode(vertical or horizontal) and than for coordinates
	 * 
	 * @param a
	 *            - 2D matrix
	 * @param ship
	 *            - ship size
	 */
	public static void placeShipsUser(char[][] a, int ship) {
		Scanner input = new Scanner(System.in);
		System.out
				.println("Chose placement mode(H - horisontal, V - vertical): ");
		char ch = Character.toUpperCase(input.next().charAt(0));

		boolean ok = false;
		int row = 0;
		int column = 0;
		if (ch == 'H') {
			while (!ok) {
				System.out.println("Chose first coordinate(row): ");
				row = input.nextInt();
				if (row < 10)
					ok = true;
			}
			ok = false;
			while (!ok) {
				System.out
						.println("Chose second coordinate(column) can't be more than "
								+ (10 - ship));
				column = input.nextInt();
				if (column < (10 - ship))
					ok = true;
			}
			for (int i = 0; i < ship; i++) {
				a[row][column + i] = 'O';
			}
		} else if (ch == 'V') {
			while (!ok) {
				System.out
						.println("Chose first coordinate(row)can't be more than "
								+ (10 - ship));
				row = input.nextInt();
				if (row < (10 - ship))
					ok = true;
			}
			ok = false;
			while (!ok) {
				System.out.println("Chose second coordinate(column):");
				column = input.nextInt();
				if (column < 10)
					ok = true;
			}

			for (int i = 0; i < ship; i++) {
				a[row + i][column] = 'O';
			}
		}
	}

	/**
	 * method used to print matrix
	 * 
	 * @param a
	 *            - 2D array we're going to print
	 */
	public static void printMatrix(char[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "  ");
			}
			System.out.println("\t" + i);
		}
		System.out.println("0  1  2  3  4  5  6  7  8  9");
	}

	/**
	 * method that deals with shooting, both user and pc
	 * 
	 * @param a
	 *            - 2D array(user field)
	 * @param enemy
	 *            - 2D array(enemy field we can't see)
	 * @param enemyField
	 *            - 2D array(enemy field shown to player)
	 * @param player
	 *            - used to see who's turn is it
	 */
	public static void shoot(char[][] a, char[][] enemy, char[][] enemyField,
			int player) {
		Scanner input = new Scanner(System.in);
		if (player % 2 != 0) {
			System.out.println("Enter coordiantes you want fire at(0-9):");
			int row = input.nextInt();
			int column = input.nextInt();
			if (enemy[row][column] == 'O') {
				enemy[row][column] = 'X';
				enemyField[row][column] = 'X';
			} else if (enemy[row][column] == 'X') {
				enemy[row][column] = 'X';
			} else {
				enemyField[row][column] = '*';
			}
		} else {
			boolean fired = false;
			// loop that prevents PC to "fire" two times on the same spot
			while (!fired) {
				int row = (int) (Math.random() * 10);
				int column = (int) (Math.random() * 10);
				if (a[row][column] == 'O' || a[row][column] == 'X') {
					a[row][column] = 'X';
					fired = true;

				} else if (a[row][column] == '~') {
					a[row][column] = '*';
					fired = true;
				}
			}
		}
	}

	/**
	 * method that checks if there are "undamaged" ship pieces on the field
	 * 
	 * @param a
	 *            - 2D array that is beeing checked
	 * @return - returns true if there are no more ship parts undamaged
	 */
	public static boolean victory(char[][] a) {
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == 'O') {
					count++;
				}
			}
		}
		if (count > 0)
			return false;
		else
			return true;

	}
}
