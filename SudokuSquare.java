package sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuSquare {
	public String numbers;
	public int position_x;
	public int position_y;
	private List<Integer> area = new ArrayList<Integer>();
	
	public SudokuSquare(int position_x, int position_y, int area) {
		numbers = Integer.toString((position_y * 10) + position_x);
		this.position_x = position_x;
		this.position_y = position_y;
		this.area.clear();
		this.area.add(area);
	}
	
	public SudokuSquare(int position_x, int position_y)
	{
		numbers = Integer.toString((position_y * 10) + position_x);
		this.position_x = position_x;
		this.position_y = position_y;
		area.clear();
		for (int t = 1; t <=9; t++)
		{
			area.add(t);
		}
	}
	
	// Getters and Setters

	public List<Integer> getArea() {
		return area;
	}

	public void setArea(int num) {
		area.clear();
		area.add(num);
	}

	public void setArea(List<Integer> nums) {
		area.clear();
		area.addAll(nums);
	}

	// Additional helper methods

	public void removeArea(int val) {
		area.remove(area.indexOf(val));
	}

	public String toString() {
		if (area.size() == 1) {
			return area.get(0).toString();
		} else if (area.size() > 1) {
			return "O";
		} else {
			return "X";
		}
	}

}