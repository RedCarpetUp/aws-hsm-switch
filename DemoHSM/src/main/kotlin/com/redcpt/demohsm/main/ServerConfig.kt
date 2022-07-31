/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redcpt.demohsm.main

import com.redcpt.demohsm.security.DES.eraseDMPfile
import com.redcpt.demohsm.security.DES.readCLWFile
import org.apache.log4j.Logger
import org.apache.log4j.Level
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.*
import javax.crypto.NoSuchPaddingException


object ServerConfig {
    private val logger = Logger.getLogger(ServerConfig::class.java)

    /**
     * Mendapatkan port gateway H2H
     *
     * @return gatewayPort
     */
    var gatewayPort = 3025
        private set

    /**
     * Mendapatkan max client dalam waktu bersamaan
     *
     * @return maxClient
     */
    var maxClient = 25
        private set

    /**
     * Mendapatkan nilai max wait database
     *
     * @return maxWait
     */
    const val maxWaitDB = 20

    /**
     * Mendapatkan Jumlah koneksi minimum setiap partisi
     *
     * @return minConn
     */
    const val minConn = 5

    /**
     * Mendapatkan Jumlah koneksi maksimum setiap partisi
     *
     * @return maxConn
     */
    const val maxConn = 10

    /**
     * Mendapatkan Jumlah partisi yang digunakan Default: 1, minimum: 1,
     * recommended: 2-4 (but very app specific)
     *
     * @return partCount
     */
    const val partCount = 1
    private val dbProp: Properties? = null
    private var gwProp: Properties? = null
    private val billerProp: Properties? = null

    /**
     * Mendapatkan driver database
     *
     * @return driver
     */
    const val driverDB = "com.mysql.jdbc.Driver"

    /**
     * Mendapatkan url database
     *
     * @return url
     */
    const val urlDB = ""

    /**
     * Mendapatkan username database
     *
     * @return username
     */
    const val usernameDB = ""

    /**
     * Mendapatkan password database
     *
     * @return password
     */
    const val passwordDB = ""

    /**
     * Get environment Application
     *
     * @return _envApp
     */
    var environmentApp = ""
        private set

    /**
     * Get DB Information Access
     *
     * @return _dbAccess
     */
    var dBAccess = ""
        private set
    var whitelist = ""
        private set

    /**
     * Memanggil konfigurasi server dari folder Properties
     */
    fun getGatewayProperties(){
        val fileName = "gw"
        try {
            readCLWFile(fileName)
        } catch (ex: NoSuchAlgorithmException) {
            Logger.getLogger(ServerConfig::class.java.name).log(Level.FATAL, null, ex)
        } catch (ex: NoSuchPaddingException) {
            Logger.getLogger(ServerConfig::class.java.name).log(Level.FATAL, null, ex)
        } catch (ex: InvalidKeyException) {
            Logger.getLogger(ServerConfig::class.java.name).log(Level.FATAL, null, ex)
        } catch (ex: InvalidKeySpecException) {
            Logger.getLogger(ServerConfig::class.java.name).log(Level.FATAL, null, ex)
        } catch (ex: FileNotFoundException) {
            Logger.getLogger(ServerConfig::class.java.name).log(Level.FATAL, null, ex)
        } catch (ex: IOException) {
            Logger.getLogger(ServerConfig::class.java.name).log(Level.FATAL, null, ex)
        } catch (ex: Exception) {
            Logger.getLogger(ServerConfig::class.java.name).log(Level.FATAL, null, ex)
        }
        try {
            gwProp = Properties()
            gwProp!!.load(FileInputStream("$fileName.dmp"))
        } catch (ex: IOException) {
            logger.error("Gagal load gw.clw : $ex")
        }
        gatewayPort = gwProp!!.getProperty("gateway_port").toInt()
        maxClient = gwProp!!.getProperty("max_client").toInt()
        environmentApp = gwProp!!.getProperty("environment_app")
        dBAccess = gwProp!!.getProperty("db_access")
        whitelist = gwProp!!.getProperty("whitelist")
        logger.debug(
            "DATA GW, gatewayPort = " + gatewayPort + " , maxClient = " + maxClient
                    + " , envApp = " + environmentApp + " , dbAccess = " + dBAccess
        )
        eraseDMPfile(fileName)
    }
}