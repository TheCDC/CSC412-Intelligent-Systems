import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GASolution {
	private static Random rand = new Random();

	private int SIZE = 81;
	public Integer[] genome = new Integer[SIZE];
	public GASolution() {
		for (int i = 0; i < SIZE; i++) {
			this.genome[i] = rand.nextInt(9) + 1;
		}
	}
	public GASolution(GASolution other) {
		for (int i = 0; i < SIZE; i++) {
			this.genome[i] = other.genome[i];
		}
	}
	public void crossover(GASolution other) {
		if (this == other) {
			return;
		}
		int sliceStart = rand.nextInt(SIZE);
		int sliceEnd = rand.nextInt(SIZE - sliceStart) + sliceStart;
		// for (int i = sliceEnd; i < sliceEnd; i++) {
		for (int i = 0; i < SIZE; i++) {
			if (rand.nextDouble() < 0.5) {
				other.genome[i] = this.genome[i];
			}

		}
	}
	public void mutate() {
		this.genome[rand.nextInt(SIZE)] = rand.nextInt(9) + 1;
	}

}
