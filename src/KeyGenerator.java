import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {

	SecureRandom secure_random = new SecureRandom();
	BigInteger one = new BigInteger("1");
	private BigInteger modulus, e_public, d_private;

	public KeyGenerator() {

		// this(1024);
	}

	public KeyGenerator(int N) {

		BigInteger p = new BigInteger(N / 2, 100, secure_random);
		BigInteger q = new BigInteger(N / 2, 100, secure_random);
		BigInteger Totient_fn = (p.subtract(one)).multiply(q.subtract(one));
		modulus = p.multiply(q);
		e_public = new BigInteger("3");
		while (Totient_fn.gcd(e_public).intValue() > 1) {
			e_public = e_public.add(new BigInteger("2"));
		}
		d_private = e_public.modInverse(Totient_fn);
	}

	//function to get private key
	String getPrivateKey() {
		String privateKey = d_private + "\n" + modulus;
		return privateKey;
	}

	//function to get public key
	String getPublicKey() {
		String publicKey = e_public + "\n" + modulus;
		return publicKey;
	}

	public void writeFile() throws IOException {

		// file for private key
		File privatekey_file = new File("privatekey_file.key");

		// if file doesn't exists, then create it
		if (!privatekey_file.exists()) {
			privatekey_file.createNewFile();
		}

		//writing file for private key
		FileWriter fw = new FileWriter(privatekey_file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(getPrivateKey());
		bw.close();

		// file for public key
		File publickey_file = new File("publickey_file.key");

		// if file doesn't exists, then create it
		if (!publickey_file.exists()) {
			publickey_file.createNewFile();
		}

		//writing file for public key
		FileWriter fw1 = new FileWriter(publickey_file.getAbsoluteFile());
		BufferedWriter bw1 = new BufferedWriter(fw1);
		bw1.write(getPublicKey());
		bw1.close();

	}

	

}
