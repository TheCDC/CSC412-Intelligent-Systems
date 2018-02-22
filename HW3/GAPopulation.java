import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;


public class GAPopulation {
	private List<GASolution> population = new ArrayList<GASolution> ();
	private static final int DEFAULT_POP_SIZE = 1000;
	private Random rand = new Random();
	private int popSize = 0;
	private List<Integer> indices = new ArrayList<>();
	public GAPopulation(int size) {
		this.popSize = size;
		for (int i = 0; i < size; i++) {
			this.population.add(new GASolution());
			this.indices.add(i);
		}
	}
	public String toString() {
		SudokuBoard b = new SudokuBoard();
		b.fill(getBestSolution().genome);
		return "" + b;
	}
	public GASolution getBestSolution() {

		SudokuBoard b = new SudokuBoard();
		b.fill(this.population.get(0).genome);
		int best  = b.getFitness();
		GASolution bs = null;
		for (GASolution s : this.population) {
			if (bs == null) {
				bs = s;
			}
			b.fill(s.genome);
			int f = b.getFitness();
			if (f > best) {
				best = f;
				bs = s;
			}
		}
		return bs;
	}
	public GAPopulation() {
		this(DEFAULT_POP_SIZE);
	}
	public void initialize() {


	}
	public int sort() {
		SudokuBoard b = new SudokuBoard();
		b.fill(getBestSolution().genome);
		return b.getFitness();
	}
	public String getFitnessString() {
		return "not implemented";
	}
	public void mutate(double prob) {
		for ( GASolution s : this.population) {
			double p = prob;
			while (p > 0) {
				double d = rand.nextDouble();
				if (d < prob) {
					s.mutate();
				}
				p -= 1;
			}
		}

	}

	public GAPopulation breed() {
		Collections.shuffle(this.indices);
		// System.out.println(this.indices);
		int numTournaments = 10;
		int tournamentSize = this.popSize / numTournaments;

//        System.out.println(this.popSize / tournamentSize);
		for (int tournamentI = 0; tournamentI < this.popSize / tournamentSize; tournamentI++) {
			SudokuBoard b = new SudokuBoard();
			int bestFitness  = 0;

			int windowStart = tournamentI * tournamentSize;
			int windowEnd = windowStart + tournamentSize;
			int passes = 3;
			// performe multiple tournaments per generation to
			// smooth out losses due to mutation?
			for (int p = 0; p < passes; p ++) {
				GASolution bestSolution = null;
				for (int i = windowStart; i < windowEnd; i++) {
					GASolution s = this.population.get(this.indices.get(i));
					if (bestSolution == null) {
						bestSolution = s;
						b.fill(s.genome);
						bestFitness = b.getFitness();
					} else {
						b.fill(s.genome);
						int f = b.getFitness();
						if (f > bestFitness) {
							// System.out.println("Tournament winner:\n" + f);
							bestSolution = s;
							bestFitness = f;
						}
					}

				}
				for (int i = windowStart; i < windowEnd; i++) {
					GASolution s = this.population.get(this.indices.get(i));
					bestSolution.crossover(s);
				}
			}
		}
		return this;
	}
}