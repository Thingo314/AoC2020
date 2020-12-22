import java.util.*;

public class Day_22 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayDeque<Integer> player1 = new ArrayDeque<>();
		sc.nextLine();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals(""))
				break;
			player1.addLast(Integer.parseInt(line));
		}

		ArrayDeque<Integer> player2 = new ArrayDeque<>();
		sc.nextLine();
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals(""))
				break;
			player2.addLast(Integer.parseInt(line));
		}
		sc.close();

		ArrayDeque<Integer> startPlayer1 = new ArrayDeque<>(player1);
		ArrayDeque<Integer> startPlayer2 = new ArrayDeque<>(player2);

		while (player1.size() != 0 && player2.size() != 0) {
			int player1Card = player1.removeFirst();
			int player2Card = player2.removeFirst();

			ArrayDeque<Integer> winningDeck = null;
			int a = 0;
			int b = 0;

			if (player1Card > player2Card) {
				winningDeck = player1;
				a = player1Card;
				b = player2Card;
			} else {
				winningDeck = player2;
				a = player2Card;
				b = player1Card;
			}

			winningDeck.addLast(a);
			winningDeck.addLast(b);
		}

		long res1 = 0;
		ArrayDeque<Integer> won = (player1.size() == 0) ? player2 : player1;
		long mul = won.size();

		while (won.size() != 0) {
			res1 += mul * won.removeFirst();
			mul--;
		}

		player1 = startPlayer1;
		player2 = startPlayer2;
		int winner = winner(player1, player2);
		won = (winner == 0) ? player1 : player2;
		mul = won.size();
		long res2 = 0;

		while (won.size() != 0) {
			res2 += mul * won.removeFirst();
			mul--;
		}

		System.out.println("Part 1: " + res1);
		System.out.println("Part 2: " + res2);
	}

	static int winner(ArrayDeque<Integer> a, ArrayDeque<Integer> b) {
		// 0 if a wins, 1 if b wins
		HashSet<String> seenStates = new HashSet<>();
		String gameState = a.toString() + b.toString();

		while (a.size() != 0 && b.size() != 0) {
			if (!seenStates.add(gameState))
				return 0;

			int aCard = a.removeFirst();
			int bCard = b.removeFirst();

			int roundWinner = 0;

			if (aCard <= a.size() && bCard <= b.size()) {
				ArrayDeque<Integer> aSub = new ArrayDeque<>(a);
				ArrayDeque<Integer> bSub = new ArrayDeque<>(b);
				while (aSub.size() != aCard)
					aSub.removeLast();
				while (bSub.size() != bCard)
					bSub.removeLast();

				roundWinner = winner(aSub, bSub);
			} else {
				if (aCard > bCard)
					roundWinner = 0;
				else
					roundWinner = 1;
			}

			if (roundWinner == 0) {
				a.addLast(aCard);
				a.addLast(bCard);
			} else {
				b.addLast(bCard);
				b.addLast(aCard);
			}

			gameState = a.toString() + b.toString();
		}

		if (a.size() != 0)
			return 0;
		else
			return 1;
	}
}
