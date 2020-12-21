import java.util.*;

public class Day_21 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HashSet<String> allergies = new HashSet<>();
		HashMap<ArrayList<String>, ArrayList<String>> foods = new HashMap<>();
		HashMap<String, String> allergyIngredient = new HashMap<>();

		while (sc.hasNextLine()) {
			String[] food = sc.nextLine().split(" ");
			int allergyStart = 0;
			while (!food[allergyStart].equals("(contains"))
				allergyStart++;

			ArrayList<String> ingredients = new ArrayList<>();
			for (int i = 0; i < allergyStart; i++)
				ingredients.add(food[i]);

			ArrayList<String> allergy = new ArrayList<>();
			for (int i = allergyStart + 1; i < food.length; i++)
				allergy.add(food[i].substring(0, food[i].length() - 1));

			foods.put(ingredients, allergy);
			allergies.addAll(allergy);
		}
		sc.close();

		// only one ingredient per allergen
		// we can figure out which are the bad ingredients
		// by taking the common ingredients in each food that has the allergen
		while (allergyIngredient.size() != allergies.size()) {
			for (String allergy : allergies) {
				// already found the ingredient for the allergen
				if (allergyIngredient.containsKey(allergy))
					continue;

				HashSet<String> candidates = new HashSet<>();

				// find a food which contains allergy
				// this is the starting point for the possible bad ingredients
				for (Map.Entry<ArrayList<String>, ArrayList<String>> food : foods.entrySet()) {
					if (food.getValue().contains(allergy)) {
						candidates.addAll(food.getKey());
						break;
					}
				}

				// remove ingredient if it already has an assigned allergy
				// one ingredient per allergen
				candidates.removeAll(allergyIngredient.values());

				for (Map.Entry<ArrayList<String>, ArrayList<String>> food : foods.entrySet())
					if (food.getValue().contains(allergy))
						// get the common ingredients
						candidates.retainAll(food.getKey());

				// we cannot remove ingredients from candidates if the allergy is not listed for the food
				// the problem text states that there may be allergies missing

				// if there is one candidate, we have found the bad ingredient
				// we can store the allergy and the ingredient for later use
				if (candidates.size() == 1)
					allergyIngredient.put(allergy, candidates.iterator().next());
			}
		}

		int part1 = 0;
		for (ArrayList<String> ingredients : foods.keySet())
			for (String ingredient : ingredients)
				if (!allergyIngredient.containsValue(ingredient))
					part1++;

		ArrayList<String> sortedAllergies = new ArrayList<>(allergyIngredient.keySet());
		sortedAllergies.sort(null);

		ArrayList<String> badIngredients = new ArrayList<>();
		for (String allergy : sortedAllergies)
			badIngredients.add(allergyIngredient.get(allergy));

		String part2 = badIngredients.get(0);
		for (int i = 1; i < badIngredients.size(); i++)
			part2 = part2 + "," + badIngredients.get(i);

		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}
}
