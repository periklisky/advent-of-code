package aoc2015;

// --- Day 11: Corporate Policy ---
// Santa's previous password expired, and he needs help choosing a new one.
// 
// To help him remember his new password after the old one expires, Santa has devised a method of coming up with a password 
// based on the previous one. Corporate policy dictates that passwords must be exactly eight lowercase letters (for security reasons), 
// so he finds his new password by incrementing his old password string repeatedly until it is valid.
// 
// Incrementing is just like counting with numbers: xx, xy, xz, ya, yb, and so on. Increase the rightmost letter one step; 
// if it was z, it wraps around to a, and repeat with the next letter to the left until one doesn't wrap around.
// 
// Unfortunately for Santa, a new Security-Elf recently started, and he has imposed some additional password requirements:
// 
// Passwords must include one increasing straight of at least three letters, like abc, bcd, cde, and so on, up to xyz. 
// They cannot skip letters; abd doesn't count.
// Passwords may not contain the letters i, o, or l, as these letters can be mistaken for other characters and are therefore confusing.
// Passwords must contain at least two different, non-overlapping pairs of letters, like aa, bb, or zz.
//
// For example:
// 
// hijklmmn meets the first requirement (because it contains the straight hij) but fails the second requirement requirement 
//  (because it contains i and l).
// abbceffg meets the third requirement (because it repeats bb and ff) but fails the first requirement.
// abbcegjk fails the third requirement, because it only has one double letter (bb).
// The next password after abcdefgh is abcdffaa.
// The next password after ghijklmn is ghjaabcc, because you eventually skip all the passwords that start with ghi..., 
//	since i is not allowed.
//
// Given Santa's current password (your puzzle input), what should his next password be?
// hepxxyzz


// --- Part Two ---
// Santa's password expired again. What's the next one?
// heqaabcc

public class Day11 {
	private static final String INPUT_STRING = "hepxcrrq";
//	private static final String INPUT_STRING = "hepxxzaa";

	public static void main(String[] args) {
		String password = INPUT_STRING;

		while(!isPasswordValid(password)) {
			password = createNewPassword(password);
		}
		System.out.println(password);
	}
	
	private static String createNewPassword(String password) {
		char pass[] = password.toCharArray();
		int i = password.length() - 1;

		pass[i]++;
		
		while ((int)pass[i] == 123) { // first ASCII after 'z'
			pass[i] = 'a';
			i--;
			pass[i]++;
		}
		
		return String.valueOf(pass);
	}

	public static boolean isPasswordValid(String password) {
		if (!containsThreeConsecutiveLetters(password)) return false;
		if (containsForbiddenLetter(password)) return false;
		if (!containsTwoPais(password)) return false;
		
		return true;
	}
	
	private static boolean containsThreeConsecutiveLetters(String password) {
		for (int i = 0; i < password.length() - 2; i++) {
			if ((int)password.charAt(i) + 1 == (int)password.charAt(i+1)) {
				if ((int)password.charAt(i+1) + 1 == (int)password.charAt(i+2)) {
					return true;
				} else {
					i++;
				}
			}
		}
		
		return false;
	}

	private static boolean containsTwoPais(String password) {
		int found = 0;
		for (int i = 0; i < password.length() - 1; i++) {
			if (password.charAt(i) == password.charAt(i+1)) {
				if (found == 0) {
					found++;
					i++;
				}
				else return true;
			}
		}

		return false;
	}

	public static boolean containsForbiddenLetter(String password) {
		if (password.contains("i") || 
				password.contains("o") || 
				password.contains("l")) 
			return true;
		return false;
	}
}
