import java.util.*;

public class Day_18 {
	public static void main(String[] args) {
		long part1 = 0;
		long part2 = 0;

		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().split(" ");
			ArrayDeque<String> operator1 = new ArrayDeque<>();
			ArrayDeque<String> output1 = new ArrayDeque<>();
			ArrayDeque<String> operator2 = new ArrayDeque<>();
			ArrayDeque<String> output2 = new ArrayDeque<>();

			for (int i = 0; i < line.length; i++) {
				String token = line[i];
				while (token.indexOf("(") != -1) {
					token = token.substring(1);
					operator1.addLast("(");
					operator2.addLast("(");
				}

				int rparenthesis = 0;
				while (token.indexOf(")") != -1) {
					token = token.substring(0, token.indexOf(")")) + token.substring(token.indexOf(")") + 1);
					rparenthesis++;
				}

				if (token.equals("+") || token.equals("*")) {
					while (operator1.size() != 0 && !operator1.getLast().equals("("))
						output1.addLast(operator1.removeLast());
					operator1.addLast(token);

					while (operator2.size() != 0 && !operator2.getLast().equals("(")
						&& ((operator2.getLast().equals("+"))))
						output2.addLast(operator2.removeLast());
					operator2.addLast(token);
				} else {
					output1.addLast(token);
					output2.addLast(token);
				}

				for (int j = 0; j < rparenthesis; j++) {
					while (!operator1.getLast().equals("("))
						output1.addLast(operator1.removeLast());

					if (operator1.size() != 0 && operator1.getLast().equals("("))
						operator1.removeLast();

					while (!operator2.getLast().equals("("))
						output2.addLast(operator2.removeLast());

					if (operator2.size() != 0 && operator2.getLast().equals("("))
						operator2.removeLast();
				}
			}

			while (operator1.size() != 0)
				output1.addLast(operator1.removeLast());
			while (operator2.size() != 0)
				output2.addLast(operator2.removeLast());

			ArrayDeque<String> result1 = new ArrayDeque<>();
			ArrayDeque<String> result2 = new ArrayDeque<>();

			while (output1.size() != 0) {
				result1.addLast(output1.removeFirst());
				if (result1.getLast().matches("\\+|\\*")) {
					String op = result1.removeLast();
					long a = Long.parseLong(result1.removeLast());
					long b = Long.parseLong(result1.removeLast());
					long c = op.equals("+") ? (a + b) : a * b;
					result1.addLast(c+ "");
				}
			}

			while (output2.size() != 0) {
				result2.addLast(output2.removeFirst());
				if (result2.getLast().matches("\\+|\\*")) {
					String op = result2.removeLast();
					long a = Long.parseLong(result2.removeLast());
					long b = Long.parseLong(result2.removeLast());
					long c = op.equals("+") ? (a + b) : a * b;
					result2.addLast(c+ "");
				}
			}
			part1 += Long.parseLong(result1.removeLast());
			part2 += Long.parseLong(result2.removeLast());
		}
		sc.close();

		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}
}
