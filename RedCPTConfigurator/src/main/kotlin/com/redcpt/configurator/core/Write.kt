package com.redcpt.configurator.core

import org.apache.commons.codec.binary.Base64
import java.io.BufferedWriter
import java.io.FileWriter
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

object Write {
    private fun encryptBase64(unencryptedString: String, key: Key): String {
        val encryptCipher = Cipher.getInstance("DES")
        encryptCipher.init(Cipher.ENCRYPT_MODE, key)
        val unencryptedByteArray = unencryptedString.toByteArray(charset("UTF8"))
        val encryptedBytes = encryptCipher.doFinal(unencryptedByteArray)
        val encodedBytes = Base64.encodeBase64(encryptedBytes)
        return String(encodedBytes)
    }

    fun makingFile(data: String, name: String, tipe: String) {
        val kunci = "y!nDJ1nc0nF!igu12#t0r"
        val key = DESKeySpec(kunci.toByteArray())
        val keyFactory = SecretKeyFactory.getInstance("DES")
        val hasilEnkrip = encryptBase64(data, keyFactory.generateSecret(key))
        try {
            val fstream: FileWriter = if (tipe.equals("baru", ignoreCase = true)) {
                FileWriter("$name.clw")
            } else {
                FileWriter(name)
            }
            val out = BufferedWriter(fstream)
            out.write(hasilEnkrip)
            //Close the output stream
            out.close()
        } catch (e: Exception) { //Catch exception if any
            System.err.println("Error: " + e.message)
        }
    }
}