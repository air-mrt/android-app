package com.android.airmart.utilities

import android.content.Context
import android.preference.PreferenceManager
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

object AESUtil {

    fun hashAndSavePasswordHash(context: Context, clearPassword: String) {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(clearPassword.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        val hashedPassword = sb.toString()
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("password_hash", hashedPassword)
        editor.apply()
    }
    fun getSavedPasswordHash(context: Context): String {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return if (sharedPref.contains("password_hash"))
            sharedPref.getString("password_hash", "")!!
        else
            ""
    }
    fun getHashForString(context: Context, stringToHash: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(stringToHash.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        val hashedString = sb.toString()
        return hashedString
    }
    fun encryptAndSavePassword(context:Context, strToEncrypt: String): ByteArray {
        val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)
        val key = keygen.generateKey()
        saveSecretKey(context, key)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherText = cipher.doFinal(plainText)
        saveInitializationVector(context, cipher.iv)
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(cipherText)
        val strToSave = String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("encrypted_password", strToSave)
        editor.apply()
        return cipherText
    }
    fun getDecryptedPassword(context: Context): String {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val strEncryptedPassword = sharedPref.getString("encrypted_password", "")
        val bytes = android.util.Base64.decode(strEncryptedPassword, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val passwordToDecryptByteArray = ois.readObject() as ByteArray
        val decryptedPasswordByteArray = decrypt(context, passwordToDecryptByteArray)
        val decryptedPassword = StringBuilder()
        for (b in decryptedPasswordByteArray) {
            decryptedPassword.append(b.toChar())
        }
        return decryptedPassword.toString()
    }
    private fun decrypt(context:Context, dataToDecrypt: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(getSavedInitializationVector(context))
        cipher.init(Cipher.DECRYPT_MODE, getSavedSecretKey(context), ivSpec)
        val cipherText = cipher.doFinal(dataToDecrypt)
/*val sb = StringBuilder()
for (b in cipherText) {
sb.append(b.toChar())
}
Toast.makeText(context, "dbg decrypted = [" + sb.toString() + "]", Toast.LENGTH_LONG).show()*/
        return cipherText
    }
    private fun saveSecretKey(context:Context, secretKey: SecretKey) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(secretKey)
        val strToSave = String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("secret_key", strToSave)
        editor.apply()
    }
    private fun getSavedSecretKey(context: Context): SecretKey {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val strSecretKey = sharedPref.getString("secret_key", "")
        val bytes = android.util.Base64.decode(strSecretKey, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val secretKey = ois.readObject() as SecretKey
        return secretKey
    }
    private fun saveInitializationVector(context: Context, initializationVector: ByteArray) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(initializationVector)
        val strToSave = String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("initialization_vector", strToSave)
        editor.apply()
    }
    private fun getSavedInitializationVector(context: Context) : ByteArray {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val strInitializationVector = sharedPref.getString("initialization_vector", "")
        val bytes = android.util.Base64.decode(strInitializationVector, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val initializationVector = ois.readObject() as ByteArray
        return initializationVector
    }
}