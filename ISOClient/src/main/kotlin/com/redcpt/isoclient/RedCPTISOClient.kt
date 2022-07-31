/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redcpt.isoclient

import org.jpos.iso.*
import org.jpos.iso.channel.ASCIIChannel
import org.jpos.iso.packager.EuroPackager
import java.io.IOException
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

object RedCPTISOClient {
    /**
     * @param args the command line arguments
     */
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            var host = "localhost"
            var port = 16806
            if (args.isNotEmpty()) {
                host = args[0]
            }
            if (args.size > 1) {
                port = args[1].toInt()
            }
            val packager: ISOPackager = EuroPackager()
            val channel = ASCIIChannel(host, port, packager)
            channel.connect()
            val msg = ISOMsg()
            msg.packager = packager
            msg.mti = "0800"
            msg.set(70, "301")
            msg.set(60, Date().toString())
            println("DUMPSTRING = " + ISOUtil.dumpString(msg.pack()))
            channel.send(msg)
            val receivedRequest: ISOMsg = channel.receive()
            println("DUMPSTRING RESPONSE = " + ISOUtil.dumpString(receivedRequest.pack()))
        } catch (ex: IOException) {
            Logger.getLogger(RedCPTISOClient::class.java.name).log(Level.SEVERE, null, ex)
        } catch (ex: ISOException) {
            Logger.getLogger(RedCPTISOClient::class.java.name).log(Level.SEVERE, null, ex)
        }
    }
}