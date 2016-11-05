import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RSAAlgorithm {

	public static void main(String[] args) {

		Scanner userInputScanner = new Scanner(System.in);
		String input = null;
		// asking user for key size:
		do {
			try {
				System.out.println(
						"Enter the key size if you want to generate key: You can generate key of length = 512, 1024, 2048");
				System.out.println("Or,");
				System.out.println("Choose r if you want to restore the keys and decrypt the file \"Encrypted_file\"");
				System.out.println("Enter Q to exit.");
				input = userInputScanner.nextLine();
				if (input.equalsIgnoreCase("q")) {
					System.exit(0);
				} else if (input.equals("r")) {
					try {
						decryptFile();
					} catch (IOException e) {
						
						System.out.println("Error!!! Could not decrypt file.");
						throw e;
					}
				} else {
					int a = Integer.parseInt(input);
					Integer keysize = new Integer(a);

					generateKey(keysize);

					System.out.println("Enter the file path you want to encrypt");
					String inputtext = userInputScanner.nextLine();

					encryptText(inputtext);

					System.out.println("press d if you want to decrpt the file. else press any other key to exit");
					inputtext = userInputScanner.nextLine();

					if ("d".equals(inputtext)) {
						decryptFile();
					}
				}
			} catch (Exception e) {
				System.out.println("Exception: ");
				e.printStackTrace();
			}
			System.out.println("=====================================================================================");
		} while (!"q".equalsIgnoreCase(input));

	}

	//function to encrypt the file using encrypt function in RSACryptoSystem
	private static void encryptText(String inputtext) throws IOException {
		
		long startTime = System.currentTimeMillis();
		
		RSACryptoSystem rsaEncryptObj = new RSACryptoSystem();
		BufferedReader br = null;

		List<String> encryptedText = new ArrayList<>();

		try {
			br = new BufferedReader(new FileReader(inputtext));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				byte[] b = sCurrentLine.getBytes();
				BigInteger message = new BigInteger(b);
				BigInteger CipherText = message;
				for (int iteration = 1; iteration <= 5; iteration++) {
					CipherText = rsaEncryptObj.encrypt(CipherText);
				}
				encryptedText.add(CipherText.toString(16));

			}

			writeToFile(encryptedText, "Encrypted_file");

			System.out.println("Your encrypted file has been stored with the name \"Encrypted_file\"");
		} catch (IOException e) {
			System.out.println("Oops!!! Could not find the file.");
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Encryption took " + (endTime - startTime) + " milliseconds");
	}

	private static void writeToFile(List<String> textLines, String fileName) throws IOException {
		File Encrypted_file = new File(fileName);

		// if file doesn't exists, then create it
		if (!Encrypted_file.exists()) {
			Encrypted_file.createNewFile();
		}

		FileWriter fw = new FileWriter(Encrypted_file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		for (String line : textLines) {
			bw.write(line);
			bw.write("\n");
		}

		bw.close();
	}

	private static void generateKey(Integer keysize) throws IOException {
		KeyGenerator key = new KeyGenerator(keysize);

		key.writeFile();
		System.out.println("Your public and private key file have been generated now!");
	}

	//function to decrypt the file using decrypt function in RSACryptoSystem
	private static void decryptFile() throws FileNotFoundException, IOException {
		
		long startTime = System.currentTimeMillis();
		
		BufferedReader br1 = new BufferedReader(new FileReader("Encrypted_file"));
		List<String> decryptedLines = new ArrayList<>();
		
		RSACryptoSystem rsaDecrptObj = new RSACryptoSystem();
		// String privateKeyLine = br1.readLine();
		String oneLine;
		System.out.println("Decrypted File Data:");
		while ((oneLine = br1.readLine()) != null) {
			// for (String oneLine : encryptedText) {
			BigInteger bigIntegerLine = new BigInteger(oneLine, 16);
			BigInteger decryptedTextInBigInteger = bigIntegerLine;
			for (int iteration = 1; iteration <= 5; iteration++) {
				decryptedTextInBigInteger = rsaDecrptObj.decrypt(decryptedTextInBigInteger);
			}
			String decryptedLine = new String(decryptedTextInBigInteger.toByteArray());
			decryptedLines.add(decryptedLine);

			System.out.println(decryptedLine);
		}
		System.out.println("");
		System.out.println("Decrypted File File saved to  \"Decrypted_file.txt\" ");
		writeToFile(decryptedLines, "Decrypted_file.rtf");
		
		long endTime = System.currentTimeMillis();
		System.out.println("Decryption took " + (endTime - startTime) + " milliseconds");
	}
}
