import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class GAPopulation {
	private List<SudokuBoard> boards = new ArrayList<SudokuBoard> ();
	private static final  int DEFAULT_POP_SIZE = 1000;
	private Random rand = new Random();
	private int popSize = 0;
	public GAPopulation(int size) {
		this.popSize = size;
	}
	public GAPopulation() {
		this(DEFAULT_POP_SIZE);
	}
	public void initialize() {
		for (int i = 0 ; i < this.popSize; i ++) {
			SudokuBoard b  = new SudokuBoard();
			Integer[] cells = new Integer[81];
			for (int ci = 0; ci < 81; ci ++){
				cells[ci] = rand.nextInt(9)+1;
			}
			b.fill(cells);
			this.boards.add(b);

		}

	}
	public int sort() {
		Collections.sort(this.boards);
		return this.boards.get(this.boards.getLength()-1);
	}
	public String getFitnessString() {
		return "not implemented";
	}
	public void mutate(double prob) {

	}
	public GAPopulation crossover() {
		return this;
	}
	public GAPopulation breed() {
		return this;
	}
}