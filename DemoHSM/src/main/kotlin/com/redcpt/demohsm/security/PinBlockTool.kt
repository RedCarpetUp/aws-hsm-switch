package com.redcpt.demohsm.security

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.Hex
import org.apache.commons.lang3.StringUtils
import java.util.*

object PinBlockTool {
    /**
     * Decode pinblock format 0 (ISO 9564)
     *
     * @param pin pin
     * @param pan primary account number (PAN/CLN/CardNumber)
     * @return pinblock in HEX format
     */
    fun format0Encode(pin: String, pan: String): String {
        return try {
            val pinLenHead: String = StringUtils.leftPad(pin.length.toString(), 2, '0') + pin
            val pinData: String = StringUtils.rightPad(pinLenHead, 16, 'F')
            val bPin = Hex.decodeHex(pinData.toCharArray())
            val panPart = extractPanAccountNumberPart(pan)
            val panData: String = StringUtils.leftPad(panPart, 16, '0')
            val bPan = Hex.decodeHex(panData.toCharArray())
            val pinblock = ByteArray(8)
            for (i in 0..7) {
                pinblock[i] = (bPin[i].toInt() xor bPan[i].toInt()).toByte()
            }
            Hex.encodeHexString(pinblock).uppercase(Locale.getDefault())
        } catch (e: DecoderException) {
            throw RuntimeException("Hex decoder failed!", e)
        }
    }

    /**
     * @param accountNumber PAN - primary account number
     * @return extract right-most 12 digits of the primary account number (PAN)
     */
    fun extractPanAccountNumberPart(accountNumber: String): String? {
        var accountNumberPart: String? = null
        accountNumberPart = if (accountNumber.length > 12) {
            accountNumber.substring(accountNumber.length - 13, accountNumber.length - 1)
        } else {
            accountNumber
        }
        return accountNumberPart
    }

    /**
     * decode pinblock format 0 - ISO 9564
     *
     * @param pinblock pinblock in format 0 - ISO 9564 in HEX format
     * @param pan primary account number (PAN/CLN/CardNumber)
     * @return clean PIN
     */
    fun format0decode(pinblock: String, pan: String): String {
        return try {
            val panPart = extractPanAccountNumberPart(pan)
            val panData: String = StringUtils.leftPad(panPart, 16, '0')
            val bPan = Hex.decodeHex(panData.toCharArray())
            val bPinBlock = Hex.decodeHex(pinblock.toCharArray())
            val bPin = ByteArray(8)
            for (i in 0..7) {
                bPin[i] = (bPinBlock[i].toInt() xor bPan[i].toInt()).toByte()
            }
            val pinData = Hex.encodeHexString(bPin)
            val pinLen = pinData.substring(0, 2).toInt()
            pinData.substring(2, 2 + pinLen)
        } catch (e: NumberFormatException) {
            throw RuntimeException("Invalid pinblock format!")
        } catch (e: DecoderException) {
            throw RuntimeException("Hex decoder failed!", e)
        }
    }
}