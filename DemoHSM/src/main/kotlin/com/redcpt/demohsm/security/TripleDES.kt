package com.redcpt.demohsm.security

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class TripleDES(MYKEY: String, MYIV: String) {
    private val MY_KEY // = "5oquil2oo2vb63e8ionujny6".getBytes();//24-byte
            : ByteArray
    private val MY_IV // = "3oco1v52".getBytes();//8-byte
            : ByteArray

    init {
        MY_KEY = MYKEY.toByteArray()
        MY_IV = MYIV.toByteArray()
    }

    /**
     * Encrypt text to encrypted-text
     *
     * @param text
     * @return
     */
    fun encrypt(text: String?): String? {
        if (text == null) {
            return null
        }
        var retVal: String? = null
        try {
            val secretKeySpec = SecretKeySpec(MY_KEY, CRYPT_ALGORITHM)
            val iv = IvParameterSpec(MY_IV)
            val cipher = Cipher.getInstance(PADDING)
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv)
            val encrypted = cipher.doFinal(text.toByteArray(charset(CHAR_ENCODING)))
            retVal = String(encodeHex(encrypted))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retVal
    }

    /**
     * Decrypt encrypted-text
     *
     * @param text
     * @return
     */
    fun decrypt(text: String?): String? {
        if (text == null) {
            return null
        }
        var retVal: String? = null
        try {
            val secretKeySpec = SecretKeySpec(MY_KEY, CRYPT_ALGORITHM)
            val iv = IvParameterSpec(MY_IV)
            val cipher = Cipher.getInstance(PADDING)
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv)
            val decrypted = cipher.doFinal(decodeHex(text.toCharArray()))
            retVal = String(decrypted, charset(CHAR_ENCODING))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retVal
    }

    /**
     *
     * Converts an array of characters representing hexadecimal values into an
     * array of bytes of those same values. The returned array will be half the
     * length of the passed array, as it takes two characters to represent any
     * given byte. An exception is thrown if the passed char array has an odd
     * number of elements.
     * <br></br>
     * Portion of Apache Software Foundation
     *
     * @param data An array of characters containing hexadecimal digits
     * @return A byte array containing binary data decoded from the supplied
     * char array.
     * @throws Exception Thrown if an odd number or illegal of characters is
     * supplied
     */
    @Throws(Exception::class)
    private fun decodeHex(data: CharArray): ByteArray {
        val len = data.size
        if (len and 0x01 != 0) {
            throw Exception("Odd number of characters.")
        }
        val out = ByteArray(len shr 1)

        // two characters form the hex value.
        var i = 0
        var j = 0
        while (j < len) {
            var f = toDigit(data[j], j) shl 4
            j++
            f = f or toDigit(data[j], j)
            j++
            out[i] = (f and 0xFF).toByte()
            i++
        }
        return out
    }

    /**
     * Converts a hexadecimal character to an integer. <br></br>
     * Portion of Apache Software Foundation
     *
     * @param ch A character to convert to an integer digit
     * @param index The index of the character in the source
     * @return An integer
     * @throws Exception Thrown if ch is an illegal hex character
     */
    @Throws(Exception::class)
    private fun toDigit(ch: Char, index: Int): Int {
        val digit = ch.digitToIntOrNull(16) ?: -1
        if (digit == -1) {
            throw Exception("Illegal hexadecimal character $ch at index $index")
        }
        return digit
    }

    /**
     * Converts an array of bytes into an array of characters representing the
     * hexadecimal values of each byte in order. The returned array will be
     * double the length of the passed array, as it takes two characters to
     * represent any given byte. <br></br>
     * Portion of Apache Software Foundation
     *
     * @param data a byte[] to convert to Hex characters
     * @param toDigits the output alphabet
     * @return A char[] containing hexadecimal characters
     */
    private fun encodeHex(data: ByteArray): CharArray {
        val DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')
        val l = data.size
        val out = CharArray(l shl 1)
        // two characters form the hex value.
        var i = 0
        var j = 0
        while (i < l) {
            out[j++] = DIGITS[0xF0 and data[i].toInt() ushr 4]
            out[j++] = DIGITS[0x0F and data[i].toInt()]
            i++
        }
        return out
    }

    companion object {
        private const val CRYPT_ALGORITHM = "DESede"
        private const val PADDING = "DESede/CBC/NoPadding"
        private const val CHAR_ENCODING = "UTF-8"
    }
}