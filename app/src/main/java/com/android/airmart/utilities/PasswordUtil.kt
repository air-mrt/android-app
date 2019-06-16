package com.android.airmart.utilities


import android.content.Context
import android.preference.PreferenceManager
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
object PasswordUtil {
    lateinit var secretKey: SecretKey
    lateinit var initializationVector:ByteArray
fun encrypt(strToEncrypt: String): String {
    val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
    val keygen = KeyGenerator.getInstance("AES")
    keygen.init(256)
    secretKey = keygen.generateKey()
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
    val cipherText = cipher.doFinal(plainText)
    initializationVector = cipher.iv
    val baos = ByteArrayOutputStream()
    val oos = ObjectOutputStream(baos)
    oos.writeObject(cipherText)
    val strToSave = String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
    return strToSave
}

fun decrypt(strEncryptedPassword: String): String{
    val bytes = android.util.Base64.decode(strEncryptedPassword, android.util.Base64.DEFAULT)
    val ois = ObjectInputStream(ByteArrayInputStream(bytes))
    val passwordToDecryptByteArray = ois.readObject() as ByteArray
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    val ivSpec = IvParameterSpec(initializationVector)
    cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
    val cipherText = cipher.doFinal(passwordToDecryptByteArray)
    val decryptedPassword = StringBuilder()
    for (b in cipherText) {
        decryptedPassword.append(b.toChar())
    }
    return decryptedPassword.toString()
}
}