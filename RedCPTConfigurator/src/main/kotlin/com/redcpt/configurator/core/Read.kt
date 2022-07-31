package com.redcpt.configurator.core

import org.apache.commons.codec.binary.Base64
import java.io.BufferedReader
import java.io.FileReader
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

object Read {
    private val keyFactory = SecretKeyFactory.getInstance("DES")
    private val decryptCipher: Cipher = Cipher.getInstance("DES")

    private fun decryptBase64(encryptedString: String): String {
        val decodedBytes = Base64.decodeBase64(encryptedString.toByteArray())
        val unencryptedByteArray = decryptCipher.doFinal(decodedBytes)
        return String(unencryptedByteArray, charset("UTF8"))
    }

    fun openFile(name: String): String {
        val kunci = "y!nDJ1nc0nF!igu12#t0r"
        val key = DESKeySpec(kunci.toByteArray())
        decryptCipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(key))
        val fstream = FileReader(name)
        val `in` = BufferedReader(fstream)
        val hasilEnkrip = `in`.readLine()
        return decryptBase64(hasilEnkrip)
    }
}