package sudoku;

import java.util.ArrayList;
import java.util.List;

import sudoku.Constraints;

public class CSP {
	SudokuSquare[][] variables;
	int[] domain;
	List<Constraints> constraints;

	// Constructor
	public CSP(SudokuSquare[][] variables, int[] domain, List<Constraints> constraints) {
		this.variables = variables;
		this.domain = domain;
		this.constraints = constraints;
	}

	// AC-3 Algorithm
	public boolean AC_3() {
		List<Constraints> list = new ArrayList<Constraints>();
		list.addAll(constraints);

		Constraints x;

		while (list.size() > 0) {
			x = list.remove(0);
			if (x.variables[0].getArea().size() > 1 && revise(x)) {
				if (!x.isItAValidDomain()) {
					return false;
				}
				addNeighbour(list, x.variables[0], x.variables[1]);

			}
		}

		return true;

	}

	public boolean revise(Constraints x) {
		boolean revised = false;
		List<Integer> domain1 = x.variables[0].getArea();
		int i = 0;

		while (i < domain1.size()) {
			int val = domain1.get(i);
			if (!x.isItAValidCombination(val)) {
				x.variables[0].removeArea(val);
				revised = true;
			} else {
				i++;
			}
		}
		return revised;
	}

	public void addNeighbour(List<Constraints> queue, SudokuSquare i, SudokuSquare j) {
		for (Constraints x : constraints) {
			if (x.contains(i.numbers) && !x.contains(j.numbers) && !queue.contains(x)) {
				queue.add(x);
			}
		}
	}

	public boolean isComplete() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (variables[i][j].getArea().size() != 1) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean domainValid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (variables[i][j].getArea().size() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean cellConsistent(SudokuSquare cell) {
		for (Constraints x : constraints) {
			if (x.variables[0].numbers == cell.numbers) {
				if (revise(x)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean backtrack() {
		List<SudokuSquare> list = new ArrayList<SudokuSquare>();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (variables[i][j].getArea().size() > 1) {
					list.add(variables[i][j]);
				}
			}
		}
		return backtrackAux(list);
	}

	private boolean backtrackAux(List<SudokuSquare> list) {
		if (list.size() == 0) {
			if (isComplete()) {
				return true;
			} else {
				return false;
			}
		}

		List<Integer> current_domain = new ArrayList<Integer>();
		SudokuSquare current = list.remove(0);
		current_domain.addAll(current.getArea());
		for (int i : current_domain) {
			current.setArea(i);
			if (cellConsistent(current) && backtrackAux(list)) {
				return true;
			}
		}

		current.setArea(current_domain);
		list.add(0, current);
		return false;
	}

}
