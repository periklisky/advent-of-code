// --- Day 7: Internet Protocol Version 7 ---
// While snooping around the local network of EBHQ, you compile a list of IP addresses (they're IPv7, of course; IPv6 is much too limited). 
// You'd like to figure out which IPs support TLS (transport-layer snooping).
// 
// An IP supports TLS if it has an Autonomous Bridge Bypass Annotation, or ABBA. An ABBA is any four-character sequence which consists of a pair 
// of two different characters followed by the reverse of that pair, such as xyyx or abba. However, the IP also must not have an ABBA within 
// any hypernet sequences, which are contained by square brackets.
// 
// For example:
// 
// abba[mnop]qrst supports TLS (abba outside square brackets).
// abcd[bddb]xyyx does not support TLS (bddb is within square brackets, even though xyyx is outside square brackets).
// aaaa[qwer]tyui does not support TLS (aaaa is invalid; the interior characters must be different).
// ioxxoj[asdfgh]zxcvbn supports TLS (oxxo is outside square brackets, even though it's within a larger string).
// How many IPs in your puzzle input support TLS?
// 117

// --- Part Two ---
// You would also like to know which IPs support SSL (super-secret listening).
// 
// An IP supports SSL if it has an Area-Broadcast Accessor, or ABA, anywhere in the supernet sequences (outside any square bracketed sections), and a
// corresponding Byte Allocation Block, or BAB, anywhere in the hypernet sequences. An ABA is any three-character sequence which consists of the 
// same character twice with a different character between them, such as xyx or aba. A corresponding BAB is the same characters but in reversed 
// positions: yxy and bab, respectively.
// 
// For example:
// 
// aba[bab]xyz supports SSL (aba outside square brackets with corresponding bab within square brackets).
// xyx[xyx]xyx does not support SSL (xyx, but no corresponding yxy).
// aaa[kek]eke supports SSL (eke in supernet with corresponding kek in hypernet; the aaa sequence is not related, because the interior 
// character must be different).
// zazbz[bzb]cdb supports SSL (zaz has no corresponding aza, but zbz has a corresponding bzb, even though zaz and zbz overlap).
// How many IPs in your puzzle input support SSL?

package aoc2016;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Utils;

public class Day07 {
	private static final String INPUT_NAME = "aoc2016_day07.txt";

	public static void main(String[] args) {
		BufferedReader reader = Utils.getBufferedReader(INPUT_NAME);

		int countTLS = 0;
		int countSSL = 0;
		String ip;
		try {
			while ((ip = reader.readLine()) != null) {
				countTLS = supportsTLS(ip) ? countTLS + 1 : countTLS;
				countSSL = supportsSSL(ip) ? countSSL + 1 : countSSL;
			}
			System.out.println(countTLS);
			System.out.println(countSSL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean supportsSSL(String ip) {
		List<String> hypernets = getHypernets(ip);

		Set<String> babs = new HashSet<>();
		for (int i = 0; i < hypernets.size(); i++)
			if (containsABA(hypernets.get(i)))
				babs.addAll(getAllABAS(hypernets.get(i)));

		if (babs.isEmpty())
			return false;

		String ipParts[] = getIpParts(ip);

		for (String bab : babs) {
			String aba = bab.substring(1,3) + bab.substring(1, 2);
			for (int i = 0; i < ipParts.length; i++)
				if (ipParts[i].contains(aba))
					return true;
		}

		return false;
	}

	private static boolean supportsTLS(String ip) {
		List<String> hypernets = getHypernets(ip);

		for (int i = 0; i < hypernets.size(); i++)
			if (containsABBA(hypernets.get(i)))
				return false;

		String ipParts[] = getIpParts(ip);

		for (int i = 0; i < ipParts.length; i++)
			if (containsABBA(ipParts[i]))
				return true;

		return false;
	}

	private static String[] getIpParts(String ip) {
		return ip.split("\\[(.*?)\\]");
	}

	private static List<String> getHypernets(String ip) {
		List<String> hypernets = new ArrayList<>();

		Pattern pattern = Pattern.compile("(?<=\\[)([^\\]]+)(?=\\])");
		Matcher matcher = pattern.matcher(ip);
		while (matcher.find()) {
			hypernets.add(matcher.group());
		}

		return hypernets;
	}

	private static boolean containsABBA(String sequence) {
		for (int i = 0; i < sequence.length() - 3; i++) {
			if (sequence.charAt(i) != sequence.charAt(i + 1) && sequence.charAt(i) == sequence.charAt(i + 3)
					&& sequence.charAt(i + 1) == sequence.charAt(i + 2))
				return true;
		}

		return false;
	}

	private static boolean containsABA(String sequence) {
		for (int i = 0; i < sequence.length() - 2; i++)
			if (sequence.charAt(i) == sequence.charAt(i + 2) && sequence.charAt(i) != sequence.charAt(i + 1))
				return true;

		return false;
	}

	private static Set<String> getAllABAS(String sequence) {
		Set<String> abas = new HashSet<>();

		for (int i = 0; i < sequence.length() - 2; i++) {
			if (sequence.charAt(i) == sequence.charAt(i + 2) && sequence.charAt(i) != sequence.charAt(i + 1))
				abas.add(sequence.substring(i, i + 3));
		}

		return abas;
	}
}
