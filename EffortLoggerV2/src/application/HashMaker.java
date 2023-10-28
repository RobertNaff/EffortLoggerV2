package application;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//class that creates a hash based off of the hex value of sign-in details
//
public class HashMaker {

	   //method that creates a hash from the hex value of an input
	   //
	   public String createMD5Hash(final String input)
	           throws NoSuchAlgorithmException {

	      String hashtext = null;
	      MessageDigest md = MessageDigest.getInstance("MD5");

	      // Compute message digest of the input
	      byte[] messageDigest = md.digest(input.getBytes());

	      hashtext = convertToHex(messageDigest);

	      return hashtext;
	   }
	   //
	   
	   //method that converts an inputed string to hex
	   //
	   private String convertToHex(final byte[] messageDigest) {
	      BigInteger bigint = new BigInteger(1, messageDigest);
	      String hexText = bigint.toString(16);
	      while (hexText.length() < 32) {
	         hexText = "0".concat(hexText);
	      }
	      return hexText;
	   }
	   //
}
//