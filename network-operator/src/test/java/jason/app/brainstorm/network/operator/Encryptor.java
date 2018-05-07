package jason.app.brainstorm.network.operator;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {
    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV

        System.out.println(decrypt(key, initVector,
                encrypt(key, initVector, "{\n" + 
                		"	\"header\":{\n" + 
                		"		\"app\": {\n" + 
                		"			\"system\":\"Sample\",\n" + 
                		"			\"country\":\"cn\",\n" + 
                		"			\"version\":\"1.0-sit\",\n" + 
                		"			\"session\":\"1c00fe06-7a9f-4603-80d6-0b51f132e95c\",\n" + 
                		"			\"time\":\"2009-06-15T13:45:30\",\n" + 
                		"			\"nounce\":\"423feceb-0f7b-4945-8049-9f3ebd6c7915\",\n" + 
                		"			\"transactionId\":\"cn-1.0sit-1122323232212342442343\",\n" + 
                		"			\"deviceId\":\"32324234234\"\n" + 
                		"		},\n" + 
                		"		\"client\": {\n" + 
                		"			\"platform\":\"Android\",\n" + 
                		"			\"model\":\"Samsung S8\",\n" + 
                		"			\"version\":\"6.0.1\"\n" + 
                		"		},\n" + 
                		"		\"authentication\": {\n" + 
                		"			\"username\":\"\",\n" + 
                		"			\"password\":\"\",\n" + 
                		"			\"otp\":\"\",\n" + 
                		"			\"soft-token\":\"\"\n" + 
                		"		}\n" + 
                		"	},\n" + 
                		"	\"body\": {\n" + 
                		"		\"name\":\"user\",\n" + 
                		"		\"password\":\"password\"\n" + 
                		"	}\n" + 
                		"	\n" + 
                		"}")));
        System.out.println(decrypt(key, initVector, "3KlAPxZVsFNOzmaJSv+IJsNuMkSibBho7t7m2zMikto+kV/Vv5o6Cx/fwjR0DbwenFxGyonsbQX2\n" + 
        		"jsUWN4xVu1k8QJK6pTuG4LFuBRiv7vj6iIm+fhucQJZwPt7xdKQo+37WvjqgGZ+yz66vjmMjRQu/\n" + 
        		"NJuGEx4oKo9wj8S631Q="));
    }
}