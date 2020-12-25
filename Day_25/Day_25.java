import java.util.*;

public class Day_25 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long cardKey = sc.nextLong();
		long doorKey = sc.nextLong();
		sc.close();

		System.out.println("Part 1: " + encrypt(doorKey, findLoop(cardKey)));
		System.out.println("Part 2: Have a happy new year! :D");
	}

	static long transform(long val, long subject) {
		val *= subject;
		val %= 20201227;
		return val;
	}

	static long findLoop(long pub) {
		long i = 1;
		long iter = 0;
		while (i != pub) {
			i = transform(i, 7);
			iter++;
		}

		return iter;
	}

	static long encrypt(long pub, long size) {
		long i = 1;
		for (long j = 0; j < size; j++)
			i = transform(i, pub);

		return i;
	}
}
