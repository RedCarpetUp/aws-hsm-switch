/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redcpt.demohsm.security

import com.redcpt.demohsm.main.MainClass
import javax.crypto.*
import javax.crypto.spec.DESKeySpec
import org.apache.commons.codec.binary.Base64
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.net.URL
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException

object DES {
    private var decryptCipher: Cipher? = null
    @Throws(
        NoSuchAlgorithmException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        InvalidKeySpecException::class,
        FileNotFoundException::class,
        IOException::class,
        Exception::class
    )
    fun readCLWFile(fileName: String) {
        val kunci = "y!nDJ1nc0nF!igu12#t0r"
        val key = DESKeySpec(kunci.toByteArray())
        val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance("DES")
        decryptCipher = Cipher.getInstance("DES")
        decryptCipher!!.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(key))
        val fstream = FileReader("/app/Properties/$fileName.clw")
        val `in` = BufferedReader(fstream)
        val hasilEnkrip: String = `in`.readLine()
        val plainText = decryptBase64(hasilEnkrip)
        try {
            // Create file
            val fstream2 = FileWriter("$fileName.dmp")
            val out = BufferedWriter(fstream2)
            out.write(plainText)
            //Close the output stream
            out.close()
        } catch (e: Exception) { //Catch exception if any
            System.err.println("Error: " + e.message)
        }
    }

    fun eraseDMPfile(fileName: String) {
        val fas = File("$fileName.dmp")
        val success = fas.delete()
        if (!success) {
            System.err.println("Unable to delete dump file")
        }
    }

    @Throws(Exception::class)
    fun decryptBase64(encryptedString: String): String {
        val decodedBytes: ByteArray = Base64.decodeBase64(encryptedString.toByteArray())
        val unencryptedByteArray = decryptCipher!!.doFinal(decodedBytes)
        return String(unencryptedByteArray, charset("UTF-8"))
    }
}