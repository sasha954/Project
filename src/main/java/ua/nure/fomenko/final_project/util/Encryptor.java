package ua.nure.fomenko.final_project.util;

import org.apache.log4j.Logger;
import ua.nure.fomenko.final_project.constants.Messages;
import ua.nure.fomenko.final_project.exception.AppException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fomenko on 26.08.2017.
 */
public class Encryptor {
    private static final Logger LOG = Logger.getLogger(Encryptor.class);
    private static final String ENCODING = "UTF-8";

    public String encryptPassword(String password) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            messageDigest.update(password.getBytes(ENCODING));
            byte[] hashBite = messageDigest.digest();
            for (byte b : hashBite) {
                stringBuilder.append(Integer.toHexString((b & 240) >> 4));
                stringBuilder.append(Integer.toHexString(b & 15));
            }
            return stringBuilder.toString().toLowerCase();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOG.error(Messages.ERR_CANNOT_ENCRYPT_PASSWORD, e);
            throw new AppException(Messages.ERR_CANNOT_ENCRYPT_PASSWORD, e);
        }
    }
}
