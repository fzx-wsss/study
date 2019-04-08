import java.util.Random;
/**
 * 有10张纸牌，编号分别是1到10，现在要将这10张纸牌分为2堆，其中一堆求和为36，另一堆求积为360，问应该怎么分？
 * @author hasee
 *
 */
public class CardGA {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CardGA().run();
	}

	private final int pop_size = 30; // 人口池容量
	private final int chrom_length = 10; // 染色体长度
	private final float crossover_rate = 0.6f; // 杂交率
	private final float mutation_rate = 0.01f; // 变异率
	private final int max_generation = 10000000; // 繁殖次数的上限
	private final int sum_tagart = 32; // 纸牌求和的目标值
	private final int product_targart = 360; // 纸牌求积的目标值
	private static Random rand = new Random(); // 随机数产生器

	private int[][] population = new int[pop_size][chrom_length]; // 人口池

	public void run() {
		int generation = 0;
		initPopuation(population);
		int winner = 0, loser = 0;
		while (generation < max_generation) {
			generation++;
			winner = rand.nextInt(pop_size);
			loser = rand.nextInt(pop_size);
			if (getFitness(winner) > getFitness(loser)) {
				int temp = winner;
				winner = loser;
				loser = temp;
			}
			if (isAnswer(winner)) break;
			crossover(winner, loser);
			mutation(loser);
			if (isAnswer(loser)) {
				winner = loser;
				break;
			}
		}
		if (generation == max_generation) System.out.println("没找到合适的结果");
		else System.out.println("在第" + generation + "代" + parseToString(winner));
	}

	/**
	 * 将某个染色体解析为内容友好的字符串
	 */
	private String parseToString(int chrom) {
		StringBuilder result = new StringBuilder();
		StringBuilder sum = new StringBuilder();
		StringBuilder prod = new StringBuilder();
		for (int i = 0; i < chrom_length; i++) {
			if (population[chrom][i] == 0) sum.append((i + 1) + "+");
			else prod.append((i + 1) + "*");
		}
		sum.replace(sum.length() - 1, sum.length(), "=" + sum_tagart);
		prod.replace(prod.length() - 1, prod.length(), "=" + product_targart);
		result.append("找到了合适的纸牌组合，分别是：" + System.getProperty("line.separator"));
		result.append(sum.toString() + System.getProperty("line.separator"));
		result.append(prod.toString() + System.getProperty("line.separator"));

		return result.toString();
	}

	/**
	 * 判断某个染色体是否就是最终答案
	 */
	private boolean isAnswer(int chrom) {
		if (getFitness(chrom) == 0f) {
			return true;
		}
		return false;
	}

	/**
	 * 对染色体进行变异
	 */
	private void mutation(int chrom) {
		for (int i = 0; i < chrom_length; i++) {
			if (rand.nextFloat() < mutation_rate) {
				population[chrom][i] = (population[chrom][i] + 1) % 2;
			}
		}
	}

	/**
	 * 将两个染色体进行杂交，winner不变，只修改loser
	 */
	private void crossover(int winner, int loser) {
		for (int i = 0; i < chrom_length; i++) {
			if (rand.nextFloat() < crossover_rate) population[loser][i] = population[winner][i];
		}
	}

	/**
	 * 取得第i个染色体的符合度，符合度越小越好
	 */
	private float getFitness(int chrom) {
		int sum = 0;
		int prod = 1;
		for (int i = 0; i < chrom_length; i++) {
			if (population[chrom][i] == 0) sum += (1 + i);
			else prod *= (1 + i);
		}
		return (float) Math.abs(sum - sum_tagart) / sum_tagart + (float) Math.abs(prod - product_targart)
				/ product_targart;
	}

	/**
	 * 初始化人口池，数组的内容为0或者1， 0标识该扑克牌被分配到求和组 1标识该扑克牌被分配到求积组
	 */
	private void initPopuation(int[][] population2) {
		for (int i = 0; i < pop_size; i++)
			for (int j = 0; j < chrom_length; j++) {
				if (rand.nextFloat() < 0.5f) population[i][j] = 0;
				else population[i][j] = 1;
			}

	}
}