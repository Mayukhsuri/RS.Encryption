import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class RSACryptoSystem {


	private BigInteger modulus, e_public, d_private;
	
	//constructor of this class to read the private key and public key file
	RSACryptoSystem() throws IOException {
		BufferedReader br1 = new BufferedReader(new FileReader("privatekey_file.key"));
		BufferedReader br2 = new BufferedReader(new FileReader("publickey_file.key"));
		String privateKeyLine = br1.readLine();
		d_private = new BigInteger(privateKeyLine);
		String publicKeyLine = br2.readLine();
		e_public = new BigInteger(publicKeyLine);
		String modulusString = br2.readLine();
		modulus = new BigInteger(modulusString);
		br1.close();
		br2.close();

	}
	
	//function to encrypt the text
	public BigInteger encrypt(BigInteger message) {
		return message.modPow(e_public, modulus);
	}
	//function to decrypt the text
	public BigInteger decrypt(BigInteger encrypted_message) {
		return encrypted_message.modPow(d_private, modulus);
	}



}