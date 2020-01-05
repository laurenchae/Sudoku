package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sudoku.CSP;
import sudoku.Constraints;


public class Sudoku {
	
	SudokuSquare [][] board  = new SudokuSquare [9][9];
	CSP csp;
	
	public Sudoku() {
		create_puzzle();
		
		System.out.println("Before:");
		print_puzzle();
		
		create_csp();
		
		if(!csp.isComplete())
		{
			System.out.println("The AC-3 Algorithm reached as far as possible.");
			// Using backtrack search
			csp.backtrack();
		}
		else
		{
			System.out.println("The AC-3 Algorithm solved the Sudoku puzzle.");
		}
		
		System.out.println("After:");
		print_puzzle();
		System.out.println();

	}
	
    public void create_csp() {
        List<Constraints> constraints = new ArrayList<Constraints>();
        int[] area = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        List<int[]> puzzle_area = new ArrayList<int[]>();
        
        for (int l = 1; l <= 9; l++) {
            for (int m = 1; m <= 9; m++) {
                if (l != m) {
                    int[] temp = new int[2];
                    temp[0] = l;
                    temp[1] = m;
                    puzzle_area.add(temp);
                }
            }
        }
        for (int h = 0; h < 9; h++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int k = 0; k < 9; k++) {
                        if ((i != k || h != j) && board[i][h].getArea().size() > 1
                                && (i == k || h == j || (i / 3 == k / 3 && h / 3 == j / 3)))

                        {
                            SudokuSquare[] temp = new SudokuSquare[2];
                            temp[0] = board[i][h];
                            temp[1] = board[k][j];
                            constraints.add(new Constraints(temp, puzzle_area));
                        }
                    }
                }
            }
        }
        // Create CSP
        csp = new CSP(board, area, constraints);
    }
	
	private void create_puzzle() 
	{

		String filename = "sudoku.txt";
		BufferedReader read;

		try {

			read = new BufferedReader(new FileReader(filename));
			String ln;
			int ln_count = 0;

			while ((ln = read.readLine()) != null && ln_count < 9) {
				for (int y = 0; y < 9; y++) {
					if (Character.isDigit(ln.charAt(y)) && Integer.parseInt(String.valueOf(ln.charAt(y))) > 0) {
						board[y][ln_count] = new SudokuSquare(ln_count, y,
								Integer.parseInt(String.valueOf(ln.charAt(y))));
					} else {
						board[y][ln_count] = new SudokuSquare(ln_count, y);
					}
				}

				ln_count++;
			}

			read.close();

		} catch (Exception e) {
			System.out.format("Error: unable to open file");
			System.out.println(e);
		}
	}

	
	public void print_puzzle() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.print(board[y][x].toString());
            }
            System.out.print("\n");
        }
    }

}


