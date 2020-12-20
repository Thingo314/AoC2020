import java.util.*;

public class Day_20 {

	static char[][] monster = {
		{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#',' '},
		{'#',' ',' ',' ',' ','#','#',' ',' ',' ',' ','#','#',' ',' ',' ',' ','#','#','#'},
		{' ','#',' ',' ','#',' ',' ','#',' ',' ','#',' ',' ','#',' ',' ','#',' ',' ',' '}
	};

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HashMap<Integer, ArrayList<String>> tileEdges = new HashMap<>();
		HashMap<Integer, char[][]> tiles = new HashMap<>();

		// input reading
		while (sc.hasNextLine()) {
			String tileHead = sc.nextLine();
			if (tileHead.equals(""))
				break;

			int tileID = Integer.parseInt(tileHead.substring(tileHead.indexOf(" ") + 1, tileHead.indexOf(":")));
			char[][] tile = new char[10][10];
			for (int i = 0; i < 10; i++) {
				String row = sc.nextLine();
				for (int j = 0; j < row.length(); j++)
					tile[i][j] = row.charAt(j);
			}
			sc.nextLine();

			// for each tile, create the edges that will be compared to other tile edges
			ArrayList<String> edges = new ArrayList<>();
			String top = "";
			String left = "";
			String right = "";
			String bottom = "";
			for (int i = 0; i < 10; i++) {
				top += tile[0][i];
				left += tile[i][0];
				right += tile[i][9];
				bottom += tile[9][i];
			}

			edges.add(top);
			edges.add(right);
			edges.add(bottom);
			edges.add(left);
			edges.add(reverse(top));
			edges.add(reverse(right));
			edges.add(reverse(bottom));
			edges.add(reverse(left));

			tileEdges.put(tileID, edges);
			tiles.put(tileID, tile);
		}
		sc.close();

		long part1 = 1;
		int corner_id = 0;
		HashMap<Integer, HashMap<Integer, Integer>> connections = connections(tileEdges);
		for (int n : connections.keySet())
			if (connections.get(n).size() == 2) {
				part1 *= n;
				corner_id = n;
			}

		int imgSize = (int) Math.sqrt(connections.size());

		// figure out where the tiles go
		HashMap<Integer, int[]> placed = new HashMap<>();
		int[][] location = new int[imgSize][imgSize];
		placed.put(corner_id, new int[] {0, 0});
		location[0][0] = corner_id;

		ArrayDeque<Integer> tilesToPlace = new ArrayDeque<>();
		ArrayDeque<ArrayList<Integer>> nextToLocation = new ArrayDeque<>();
		for (int adj : connections.get(corner_id).keySet()) {
			tilesToPlace.addLast(adj);
			nextToLocation.addLast(new ArrayList<>(Arrays.asList(0, 0)));
		}

		while (tilesToPlace.size() != 0) {
			int nextTile = tilesToPlace.removeFirst();
			ArrayList<Integer> prevloc = nextToLocation.removeFirst();
			if (placed.containsKey(nextTile))
				continue;

			int alreadyPlaced = 0;
			HashSet<Integer> nextTo = new HashSet<>();
			for (int n : connections.get(nextTile).keySet())
				if (placed.containsKey(n)) {
					alreadyPlaced++;
					nextTo.add(n);
				}

			int down = prevloc.get(0);
			int right = prevloc.get(1);

			// this builds the placement of tiles from the top left corner outwards
			// this means that there is a maximum of 2 already placed tiles
			// the new tile will be right or below the previous
			if (alreadyPlaced == 1) {
				right++;
				if (right > location[down].length || location[down][right] != 0) {
					right--;
					down++;
				}
			} else {
				for (int adj : nextTo) {
					down = Math.max(down, placed.get(adj)[0]);
					right = Math.max(right, placed.get(adj)[1]);
				}
			}

			placed.put(nextTile, new int[] {down, right});
			location[down][right] = nextTile;
			for (int n : connections.get(nextTile).keySet())
				if (!placed.containsKey(n)) {
					tilesToPlace.addLast(n);
					nextToLocation.addLast(new ArrayList<>(Arrays.asList(down, right)));
				}
		}

		char[][] image = new char[imgSize * 8][imgSize * 8];

		char[][] firstTile = tiles.get(location[0][0]);
		// orient the firstTile so it matches the adjacent tiles
		// the shared edges must be oriented bottom and right
		int rightTile = location[0][1];
		int downTile = location[1][0];
		int rightMatchingEdge = connections.get(corner_id).get(rightTile);
		int downMatchingEdge = connections.get(corner_id).get(downTile);

		// rotate until it lines up with the tile on the right
		// modulo math helps as the edges for each tile are a certain order
		int timesToRotate = (5 - (rightMatchingEdge % 4)) % 4 ;
		for (int i = 0; i < timesToRotate; i++)
			firstTile = rotate(firstTile);

		// the corner tile is oriented with the tile on the right
		// the edge matching the tile below is either in the correct position
		// or is on top and the tile must be flipped
		if (rightMatchingEdge % 4 > downMatchingEdge % 4)
			firstTile = rotate(rotate(flip(firstTile)));

		// place the first tile in the image
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				image[i][j] = firstTile[i+1][j+1];

		// put back the tile so the orientation of other tiles
		// can be matched to it
		tiles.put(location[0][0], firstTile);


		// orient the top tiles
		for (int c = 1; c < imgSize; c++) {
			// orient so the left edge of the tile matches the tile on the left
			char[][] tile = tiles.get(location[0][c]);
			tile = orient(tiles.get(location[0][c - 1]), tile, true);

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					image[i][c * 8 + j] = tile[i + 1][j + 1];
				}
			}
			tiles.put(location[0][c], tile);
		}

		// from the top tiles, work down, matching the top and bottom edge
		for (int r = 1; r < imgSize; r++)
			for (int c = 0; c < imgSize; c++) {
				char[][] tile = orient(tiles.get(location[r - 1][c]), tiles.get(location[r][c]), false);

				for (int i = 0; i < 8; i++)
					for (int j = 0; j < 8; j++)
						image[r * 8 + i][c * 8 + j] = tile[i + 1][j + 1];

				tiles.put(location[r][c], tile);
			}

		long roughness = 0;
		for (int i = 0; i < image.length; i++)
			for (int j = 0; j < image[i].length; j++)
				if (image[i][j] == '#')
					roughness++;

		int occPerSeaMonster = 0;
		for (int i = 0; i < monster.length; i++)
			for (int j = 0; j < monster[i].length; j++)
				if (monster[i][j] == '#')
					occPerSeaMonster++;

		int numSeaMonster = 0;
		int tries = 0;
		while (true) {
			numSeaMonster = 0;
			for (int i = 0; i <= image.length - monster.length; i++) {
				for (int j = 0; j <= image[i].length - monster[0].length; j++) {
					boolean matches = true;
					match:
					for (int k = 0; k < monster.length; k++) {
						for (int l = 0; l < monster[k].length; l++) {
							if (monster[k][l] == ' ')
								continue;

							if (image[i + k][j + l] != monster[k][l]) {
								matches = false;
								break match;
							}
						}
					}

					if (matches)
						numSeaMonster++;
				}
			}

			tries++;
			if (numSeaMonster == 0)
				if (tries == 4)
					image = flip(image);
				else
					image = rotate(image);
			else
				break;
		}

		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + (roughness - (numSeaMonster * occPerSeaMonster)));
	}

	static char[][] flip(char[][] c) {
		char[][] f = new char[c[0].length][c.length];

		for (int i = 0; i < f.length; i++)
			for (int j = 0; j < f[i].length; j++)
				f[i][j] = c[i][f[i].length - j - 1];

		return f;
	}

	// rotate 90 clockwise
	static char[][] rotate(char[][] c) {
		char[][] r = new char[c[0].length][c.length];

		for (int i = 0; i < r.length; i++)
			for (int j = 0; j < r[i].length; j++)
				r[i][j] = c[c.length - 1 - j][i];

		return r;
	}

	static char[][] orient(char[][] placed, char[][] toPlace, boolean left) {
		for (int i = 0; i < 4; i++) {
			if ((left) ? matchesVertEdge(placed, toPlace) : matchesHoriEdge(placed, toPlace))
				return toPlace;
			toPlace = rotate(toPlace);
		}

		toPlace = flip(toPlace);
		for (int i = 0; i < 4; i++) {
			if ((left) ? matchesVertEdge(placed, toPlace) : matchesHoriEdge(placed, toPlace))
				return toPlace;
			toPlace = rotate(toPlace);
		}

		return toPlace;
	}

	static boolean matchesVertEdge(char[][] l, char[][] r) {
		for (int i = 0; i < l.length; i++)
			if (l[i][l[i].length - 1] != r[i][0])
				return false;

		return true;
	}

	static boolean matchesHoriEdge(char[][] t, char[][] b) {
		for (int i = 0; i < b[0].length; i++)
			if (t[t.length - 1][i] != b[0][i])
				return false;

		return true;
	}

	static String reverse(String s) {
		String r = "";
		for (char c : s.toCharArray())
			r = c + r;

		return r;
	}

	static HashMap<Integer, HashMap<Integer, Integer>> connections(HashMap<Integer, ArrayList<String>> edges) {
		HashMap<Integer, HashMap<Integer, Integer>> connections = new HashMap<>();
		for (int t : edges.keySet())
			connections.put(t, new HashMap<>());

		for (int t : edges.keySet()) {
			ArrayList<String> tEdges = edges.get(t);
			for (int u : edges.keySet()) {
				if (t == u)
					continue;

				ArrayList<String> uEdges = edges.get(u);

				boolean common = false;
				int fromT = -1;
				int fromU = -1;
				for (int i = 0; i < tEdges.size(); i++)
					for (int j = 0; j < uEdges.size(); j++)
						if (tEdges.get(i).equals(uEdges.get(j))) {
							common = true;
							fromT = i;
							fromU = j;
						}

				if (common) {
					connections.get(t).put(u, fromT);
					connections.get(u).put(t, fromU);
				}
			}
		}

		return connections;
	}
}
