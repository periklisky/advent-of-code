// --- Day 4: Security Through Obscurity ---
// Finally, you come across an information kiosk with a list of rooms. Of course, the list is encrypted and full of decoy data,
// but the instructions to decode the list are barely hidden nearby. Better remove the decoy data first.
// 
// Each room consists of an encrypted name (lowercase letters separated by dashes) followed by a dash, a sector ID, and a checksum 
// in square brackets.
// 
// A room is real (not a decoy) if the checksum is the five most common letters in the encrypted name, in order, with ties broken by alphabetization. For example:
// 
// aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common letters are a (5), b (3), and then a tie between x, y, and z, 
// which are listed alphabetically.
// a-b-c-d-e-f-g-h-987[abcde] is a real room because although the letters are all tied (1 of each), the first five are listed alphabetically.
// not-a-real-room-404[oarel] is a real room.
// totally-real-room-200[decoy] is not.
// Of the real rooms from the list above, the sum of their sector IDs is 1514.
// 
// What is the sum of the sector IDs of the real rooms?

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Day04 {
	private static final String INPUT_NAME = "aoc2016_day04.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		List<Room> rooms = new ArrayList<>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				int length = line.length();
				String name = line.substring(0, length - 10).replaceAll("-", "");
				int sectorId = Integer.valueOf(line.substring(length - 10, length - 7));
				String checksum = line.substring(length - 6, length - 1);
				rooms.add(new Room(name, sectorId, checksum));
//				System.out.println(name + " " + sectorId + " " + checksum);
//				System.out.println(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int count = 0;
		for (int i = 0; i < rooms.size(); i++) {
			count = (rooms.get(i).isReal()) ? count + rooms.get(i).getSectorId() : count;
		}
		System.out.println(count);
	}

	public static class Room {
		private String name;
		private int sectorId;
		private String checksum;

		public Room(String name, int sectorId, String checksum) {
			this.name = name;
			this.sectorId = sectorId;
			this.checksum = checksum;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getSectorId() {
			return sectorId;
		}

		public void setSectorId(int sectorId) {
			this.sectorId = sectorId;
		}

		public String getChecksum() {
			return checksum;
		}

		public void setChecksum(String checksum) {
			this.checksum = checksum;
		}

		public boolean isReal() {
			int freqs[] = new int[5];

			for (int i = 0; i < checksum.length(); i++) {
				for (int j = 0; j < name.length(); j++) {
					freqs[i] = (name.charAt(j) == checksum.charAt(i)) ? freqs[i] + 1 : freqs[i];
				}
			}

			for (int i = 0; i < checksum.length() - 1; i++) {
				if (freqs[i] < freqs[i+1]) {
					return false;
				}
				else if (freqs[i] == freqs[i+1] && checksum.charAt(i) > checksum.charAt(i + 1)) {
					return false;
				}
			}
			
			System.out.println(name + " " + sectorId + " " + checksum);

			return true;
		}
	}
}
