package com.redcpt.isogateway.main.server

import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator
import org.jpos.core.ConfigurationException
import org.jpos.core.SimpleConfiguration
import org.jpos.iso.*
import org.jpos.iso.channel.ASCIIChannel
import org.jpos.iso.packager.EuroPackager
import org.jpos.util.ThreadPool
import java.io.IOException


object Main {
    private val logger: Logger = Logger.getLogger(Main::class.java)

    /**
     * Aplikasi dijalankan dan sedikit 'credit title'
     *
     * @param args the command line arguments
     */
    @JvmStatic
    fun main(args: Array<String>) {
        PropertyConfigurator.configure("/app/Properties/log4j.properties")
        try {
            val packager: ISOPackager = EuroPackager()
            val channel: ISOChannel = ASCIIChannel(packager)

            //PORT , CHANNEL, ThreadPool( Min PoolSize, Max PoolSize, Name )
            val server = ISOServer(16806, channel as ServerChannel, ThreadPool(1, 2, "Sample Euro Packager"))
            server.setConfiguration(SimpleConfiguration())
            server.addISORequestListener(messageListener())
            val serverThread = Thread(server)
            serverThread.start()
            logger.info("Listener Started !!!")
        } catch (ex: IOException) {
            logger.error("Listener Failed = " + ex.message)
        } catch (ex: ConfigurationException) {
            logger.error("Configuration Failed = " + ex.message)
        }
        logger.debug("31072022 | 1.1  | RedCPT HSM demo!!! ")
    }

    private fun messageListener(): ISORequestListener {
        return ISORequestListener { source, m ->
            try {
                logger.info("Message From Source = " + ISOUtil.dumpString(m.pack()))
                val response: ISOMsg = m.clone() as ISOMsg
                try {
                    response.setResponseMTI()
                    response.set(39, "00")
                    response.set(60, "Success")
                    source.send(response)
                } catch (e: Exception) {
                    logger.error("Listener Failed = " + e.message)
                }
            } catch (ex: ISOException) {
                logger.error("Listener Failed = " + ex.message)
            }
            true
        }
    }
}