package com.redcpt.demohsm.codec

import java.util.*
import kotlin.math.abs
import kotlin.random.Random

class GeneratorCodec {
    companion object {
        fun productConverter(product: String): String {
            return product.substring(0, 3) + "INQ"
        }

        fun generateReferenceNumberOnly(length: Int): String {
            val code = StringBuilder("")
            for (i in 0 until length) {
                code.append((Random.nextInt(10) + '0'.code).toChar())
            }
            return code.toString()
        }
    }


    fun generateLoketNameJatelindo(partner: String, Low: Int, High: Int): String {
        val result: Int = Random.nextInt(High - Low) + Low
        return "UNIVAPI" + partner + "000$result".substring(result.toString().length)
    }

    fun generateReference(): String {
        val uuid = UUID.randomUUID().toString()
        val result = uuid.replace("[\\s\\-()]".toRegex(), "")
        return result.substring(0, 10)
    }

    fun generateXor(a: String, b: String): String? {
        val sb = StringBuilder()
        for (k in a.indices) {
            sb.append(a[k].code xor b[k + abs(a.length - b.length)].code)
        }
        return sb.toString()
    }
}