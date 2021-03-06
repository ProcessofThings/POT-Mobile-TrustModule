package com.example.osbg.pot.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.osbg.pot.utilities.Base58Helper;
import com.example.osbg.pot.utilities.HashCalculator;
import com.example.osbg.pot.utilities.encryption.HexHelper;

import java.security.SecureRandom;
import java.util.UUID;

public class KeyGenerator {

    private final String PREFERENCES_PASSWORD_NAME = "Password"; //file name
    private final String PREFERENCES_PASSWORD_KEY = "mypassword"; //key name"

    private Context context;

    public KeyGenerator(Context context) {
        this.context = context;
    }

    public String generate(){
        String uuid = UUID.randomUUID().toString();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_PASSWORD_NAME, 0);
        String passwordHash = sharedPreferences.getString(PREFERENCES_PASSWORD_KEY, null);
        String uuidHash = HashCalculator.calculateHash(uuid, passwordHash);
        String key = Base58Helper.encode(HexHelper.hexStringToByteArray(uuidHash));
        if (key.length() == 43){
            key += "a";
        }
        return key;
    }

    public static byte[] genRandomBytes(int x) {
        SecureRandom r = new SecureRandom();
        byte[] ivBytes = new byte[x];
        r.nextBytes(ivBytes);
        return ivBytes;
    }
}
