import java.util.*;

public class Day_02 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int valid1 = 0;
		int valid2 = 0;

		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().split(" ");
			String[] restriction = line[0].split("-");
			int min = Integer.parseInt(restriction[0]);
			int max = Integer.parseInt(restriction[1]);

			int count = 0;
			char piece = line[1].charAt(0);
			char[] pass = line[2].toCharArray();

			for (int i = 0; i < pass.length; i++)
				if (pass[i] == piece)
					count++;

			if (count >= min && count <= max)
				valid1++;

			if (pass[min - 1] != pass[max - 1] && (pass[min - 1] == piece || pass[max - 1] == piece))
				valid2++;
		}
		sc.close();
		System.out.println("Part 1: " + valid1);
		System.out.println("Part 2: " + valid2);
	}
}
