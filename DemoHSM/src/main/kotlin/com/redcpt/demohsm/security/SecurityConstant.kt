package com.redcpt.demohsm.security

object SecurityConstant {
    /**
     * @param hexStrings String[] variable generator hexString
     * @param hexArray char[] variable untuk kebutuhan convert byte ke String
     */
    private val hexStrings: Array<String?> = arrayOfNulls(256)
    private val hexArray = "0123456789ABCDEF".toCharArray()

    init {
        for (i in 0..255) {
            val d = StringBuilder(2)
            var ch = Character.forDigit(i.toByte().toInt() shr 4 and 0x0F, 16)
            d.append(ch.uppercaseChar())
            ch = Character.forDigit(i.toByte().toInt() and 0x0F, 16)
            d.append(ch.uppercaseChar())
            hexStrings[i] = d.toString()
        }
    }

    /**
     * Converts hex string ke byte array. Library ini menggunakan library jPOS
     *
     * @param b Byte array
     * @param offset Integer offset
     * @param len Integer panjang
     * @return byte array
     */
    fun hex2byte(b: ByteArray, offset: Int, len: Int): ByteArray {
        val d = ByteArray(len)
        for (i in 0 until len * 2) {
            val shift = if (i % 2 == 1) 0 else 4
            d[i shr 1] =
                ((d[i shr 1].toInt() or (Char(b[offset + i].toUShort()).digitToIntOrNull(16) ?: (-1 shl shift)))).toByte()
        }
        return d
    }

    /**
     * Converts hex string ke byte array. Library ini menggunakan library jPOS
     *
     * @param s String
     * @return byte array
     */
    fun hex2byte(s: String): ByteArray {
        return if (s.length % 2 == 0) {
            hex2byte(s.toByteArray(), 0, s.length shr 1)
        } else {
            // Padding left zero to make it even size #Bug raised by tommy
            hex2byte("0$s")
        }
    }

    /**
     * Converts byte array ke String. Library ini menggunakan library jPOS
     *
     * @param b byte array
     * @return String
     */
    fun hexString(b: ByteArray): String {
        val d = StringBuilder(b.size * 2)
        for (aB in b) {
            d.append(hexStrings[aB.toInt() and 0xFF])
        }
        return d.toString()
    }

    fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v ushr 4]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    fun hexStringToByteArray(s: String): ByteArray {
        val b = ByteArray(s.length / 2)
        for (i in b.indices) {
            val index = i * 2
            val v = s.substring(index, index + 2).toInt(16)
            b[i] = v.toByte()
        }
        return b
    }
}