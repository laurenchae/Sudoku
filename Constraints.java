package sudoku;

import java.util.List;

public class Constraints {
	public SudokuSquare[] variables;
	public List<int[]> values;

	// Constructor
	public Constraints(SudokuSquare[] variables, List<int[]> values) {
		this.variables = variables;
		this.values = values;
	}

	public boolean isItAValidDomain() {
		if (variables[0].getArea().size() > 0 && variables[1].getArea().size() > 0) {
			return true;
		}

		return false;
	}

	public boolean isItAValidCombination(int value) {
		for (int i : variables[1].getArea()) {
			for (int[] v : values) {
				if (value == v[0] && i == v[1]) {
					return true;
				}
			}
		}
		return true;
	}

	public boolean contains(String number) {
		if (number == variables[0].numbers || number == variables[1].numbers) {
			return true;
		}

		return false;
	}

}
