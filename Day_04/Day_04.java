import java.util.*;

public class Day_04 {
	static int valid1 = 0;
	static int valid2 = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HashMap<String, String> creds = new HashMap<>();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals("")) {
				isValid(creds);
				creds.clear();
			} else {
				String[] cred = line.split(" ");
				for (String s : cred) {
					creds.put(s.substring(0, 3), s.substring(4));
				}
			}
		}
		sc.close();
		isValid(creds);

		System.out.println("Part 1: " + valid1);
		System.out.println("Part 2: " + valid2);
	}

	static String[] validEyeColours = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};

	static void isValid(HashMap<String, String> cred) {
		if (cred.size() != 8 && (cred.size() != 7 || cred.containsKey("cid")))
			return;
		valid1++;

		int byr = Integer.parseInt(cred.get("byr"));
		if (byr < 1920 || byr > 2002)
			return;

		int iyr = Integer.parseInt(cred.get("iyr"));
		if (iyr < 2010 || iyr > 2020)
			return;

		int eyr = Integer.parseInt(cred.get("eyr"));
		if (eyr < 2020 || eyr > 2030)
			return;

		String heightField = cred.get("hgt");
		if (heightField.length() < 4)
			return;
		int hgt = Integer.parseInt(heightField.substring(0, heightField.length() - 2));
		if (heightField.charAt(heightField.length()-2) == 'c') {
			if (hgt < 150 || hgt > 193)
				return;
		} else {
			if (hgt < 59 || hgt > 76)
				return;
		}

		String hairColour = cred.get("hcl");
		if (hairColour.length() != 7)
			return;
		if (hairColour.charAt(0) != '#')
			return;
		if (!hairColour.substring(1).matches("[0-9a-f]+"))
			return;

		String eyeColour = cred.get("ecl");
		int notInValidList = 0;
		for (String clr : validEyeColours)
			if (eyeColour.equals(clr))
				break;
			else
				notInValidList++;
		if (notInValidList == validEyeColours.length)
			return;

		String pid = cred.get("pid");
		if (pid.length() != 9)
			return;
		if (!pid.matches("[0-9]+"))
			return;

		valid2++;
	}
}
