package com.redcpt.demohsm.main

import com.redcpt.demohsm.codec.GeneratorCodec
import com.redcpt.demohsm.engine.KeyManagementService.encryptAndDecryptSample
import com.redcpt.demohsm.main.PropertiesActivator.run
import com.redcpt.demohsm.main.ServerConfig.gatewayPort
import com.redcpt.demohsm.main.ServerConfig.getGatewayProperties
import com.redcpt.demohsm.main.ServerConfig.maxClient
import com.redcpt.demohsm.main.ServerConfig.whitelist
import com.redcpt.demohsm.security.PinBlockTool.format0Encode
import com.redcpt.demohsm.security.TripleDES
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator
import org.jpos.iso.ISOMsg
import org.jpos.iso.ISOUtil
import org.jpos.iso.packager.EuroPackager
import org.json.JSONObject
import org.simpleframework.http.Request
import org.simpleframework.http.Response
import org.simpleframework.http.core.Container
import org.simpleframework.http.core.ContainerSocketProcessor
import org.simpleframework.transport.SocketProcessor
import org.simpleframework.transport.connect.Connection
import org.simpleframework.transport.connect.SocketConnection
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.PrintStream
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext

class MainClass(size: Int) : Container {
    private val executor: Executor

    class Task(private val request: Request, private val response: Response) : Runnable {
        private fun clear(msg: ISOMsg) {
            for (i in 1..128) {
                msg.unset(i)
            }
        }

        override fun run() {
            val statusMessage: String
            val statusRC: String
            val now = System.currentTimeMillis()
            var objJSONBodyResp: JSONObject
            val transmissionDateTime = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
            val workerName = "[HSM - " + request.clientAddress + "] "
            val path = request.path
            val directory = path.directory
            try {
                val streamMessage = request.content
                val commandName = directory.replace("[^a-zA-Z0-9]".toRegex(), "")
                logger.info(workerName + "IP = " + request.clientAddress + " , Command = " + commandName + " , message = " + streamMessage)
                objJSONBodyResp = JSONObject()
                val ipCheck =
                    request.clientAddress.toString().split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val hasilWhitelistIP = whitelist
                val lisIP = hasilWhitelistIP.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var checkWLIP = false
                var i = 0
                while (i < lisIP.size) {
                    if (ipCheck[0].substring(1).equals(lisIP[i], ignoreCase = true)) {
                        checkWLIP = true
                        i = lisIP.size
                    }
                    i++
                }
                val requestJSON: JSONObject
                if (commandName.equals("activatecust", ignoreCase = true)) {
                    requestJSON = JSONObject(streamMessage)
                    logger.info(workerName + "SCENARIO 1 [ show data encryption ] = " + streamMessage)
                    objJSONBodyResp.put("Card_Number", requestJSON.getString("Card_Number"))

                    // PARAMETER
                    val keyArn = "arn:aws:kms:ap-south-1:998547497418:key/514e1f04-9289-45c3-bf5c-8b14951ccec2"
                    logger.info(workerName + "sample arn = " + keyArn)
                    objJSONBodyResp.put("Sample_Arn", keyArn)
                    logger.info(workerName + "sample card number = " + requestJSON.getString("Card_Number"))
                    objJSONBodyResp.put("Card_Number", requestJSON.getString("Card_Number"))
                    val validationData = "19283742937"
                    logger.info(workerName + "sample validation data = " + validationData)
                    objJSONBodyResp.put("Sample_validation_Data", validationData)
                    val dataProcess = validationData + requestJSON.getString("Card_Number")
                        .substring(requestJSON.getString("Card_Number").length - 5)
                    logger.info(workerName + "sample dataprocess = " + dataProcess)
                    objJSONBodyResp.put("Sample_Data_Process", dataProcess)

                    //FIRST PIN GENERATOR
                    val threedes = TripleDES("5oquil2oo2vb63e8ionujny6", "3oco1v52")
                    val resultEncrypt = threedes.encrypt(dataProcess)
                    logger.info(workerName + "Result threedesencrypt = " + resultEncrypt)
                    logger.info(workerName + "raw PIN digit 1 = " + resultEncrypt!!.substring(0, 1))
                    logger.info(workerName + "raw PIN digit 2 = " + resultEncrypt.substring(1, 2))
                    logger.info(workerName + "raw PIN digit 3 = " + resultEncrypt.substring(2, 3))
                    logger.info(workerName + "raw PIN digit 4 = " + resultEncrypt.substring(3, 4))
                    val pinDigit1 = "" + resultEncrypt.substring(0, 1).toInt(16) % 10
                    logger.info(workerName + "PIN digit 1 = " + pinDigit1)
                    val pinDigit2 = "" + resultEncrypt.substring(1, 2).toInt(16) % 10
                    logger.info(workerName + "PIN digit 2 = " + pinDigit2)
                    val pinDigit3 = "" + resultEncrypt.substring(2, 3).toInt(16) % 10
                    logger.info(workerName + "PIN digit 3 = " + pinDigit3)
                    val pinDigit4 = "" + resultEncrypt.substring(3, 4).toInt(16) % 10
                    logger.info(workerName + "PIN digit 4 = " + pinDigit4)
                    val offsetPIN = "2525"
                    logger.info(workerName + "offset PIN = " + offsetPIN)
                    val pinResult1 = "" + (pinDigit1.toInt() + offsetPIN.substring(0, 1).toInt()) % 10
                    logger.info(workerName + "PIN Result digit 1 = " + pinResult1)
                    val pinResult2 = "" + (pinDigit2.toInt() + offsetPIN.substring(1, 2).toInt()) % 10
                    logger.info(workerName + "PIN Result digit 2 = " + pinResult2)
                    val pinResult3 = "" + (pinDigit3.toInt() + offsetPIN.substring(2, 3).toInt()) % 10
                    logger.info(workerName + "PIN Result digit 3 = " + pinResult3)
                    val pinResult4 = "" + (pinDigit4.toInt() + offsetPIN.substring(3, 4).toInt()) % 10
                    logger.info(workerName + "PIN Result digit 4 = " + pinResult4)
                    logger.info(workerName + "First PIN = " + pinResult1 + pinResult2 + pinResult3 + pinResult4)
                    objJSONBodyResp.put("First_PIN", pinResult1 + pinResult2 + pinResult3 + pinResult4)
                    logger.info(
                        workerName + "PIN BLOCK ENCODE FOR BIT 52 = " + format0Encode(
                            pinResult1 + pinResult2 + pinResult3 + pinResult4,
                            requestJSON.getString("Card_Number")
                        )
                    )
                    objJSONBodyResp.put(
                        "PIN_Block_Bit52",
                        format0Encode(
                            pinResult1 + pinResult2 + pinResult3 + pinResult4,
                            requestJSON.getString("Card_Number")
                        )
                    )

                    //ISO GENERATOR
                    val nowISO = System.currentTimeMillis()
                    var transYear: SimpleDateFormat
                    var transMonth: SimpleDateFormat
                    val transmissionTime = SimpleDateFormat("MMddhhmmss")
                    val transTime = SimpleDateFormat("hhmmss")
                    val transDate = SimpleDateFormat("MMdd")
                    val packager = EuroPackager()
                    val isoMsgSend = ISOMsg()
                    isoMsgSend.packager = packager
                    clear(isoMsgSend)
                    isoMsgSend.mti = "0200"
                    isoMsgSend[2] = requestJSON.getString("Card_Number")
                    isoMsgSend[3] = "380000"
                    isoMsgSend[7] = transmissionTime.format(now)
                    isoMsgSend[11] = GeneratorCodec.generateReferenceNumberOnly(6)
                    isoMsgSend[12] = transTime.format(now)
                    isoMsgSend[13] = transDate.format(now)
                    isoMsgSend[15] = transDate.format(now + 86400000)
                    isoMsgSend[18] = "6021"
                    isoMsgSend[32] = "008"
                    isoMsgSend[49] = "360"
                    isoMsgSend[52] = format0Encode(
                        pinResult1 + pinResult2 + pinResult3 + pinResult4,
                        requestJSON.getString("Card_Number")
                    )
                    logger.info(workerName + "Plain Text Dumpstring = " + ISOUtil.dumpString(isoMsgSend.pack()))
                    objJSONBodyResp.put("Sample_ISO_Dumpstring", ISOUtil.dumpString(isoMsgSend.pack()))
                    logger.info(workerName + "Plain Text Hexstring = " + ISOUtil.hexString(isoMsgSend.pack()))
                    objJSONBodyResp.put("Sample_ISO_Hexstring", ISOUtil.hexString(isoMsgSend.pack()))
                    logger.info(
                        workerName + "Plain Text in byte= " + ISOUtil.dumpString(isoMsgSend.pack()).toByteArray(
                            StandardCharsets.UTF_8
                        )
                    )
                    objJSONBodyResp.put(
                        "Sample_ISO_PlainByte", ISOUtil.dumpString(isoMsgSend.pack()).toByteArray(
                            StandardCharsets.UTF_8
                        )
                    )
                    val KMSCipherResult = encryptAndDecryptSample(
                        keyArn, ISOUtil.dumpString(isoMsgSend.pack()).toByteArray(
                            StandardCharsets.UTF_8
                        ), workerName
                    )
                    objJSONBodyResp.put(
                        "KMS_Cipher_Result", ISOUtil.dumpString(isoMsgSend.pack()).toByteArray(
                            StandardCharsets.UTF_8
                        )
                    )
                    statusRC = "0000"
                    statusMessage = "Success"
                } else if (commandName.equals("createcust", ignoreCase = true)) {
                    val rand = Random()
                    val randomNum = rand.nextInt(10 - 1 + 1) + 1
                    when (randomNum) {
                        1 -> {
                            objJSONBodyResp.put("Card_Number", "378282246310005")
                            objJSONBodyResp.put("Card_Number", "371449635398431")
                            objJSONBodyResp.put("Card_Number", "5610591081018250")
                            objJSONBodyResp.put("Card_Number", "30569309025904")
                            objJSONBodyResp.put("Card_Number", "6011111111111117")
                            objJSONBodyResp.put("Card_Number", "3530111333300000")
                            objJSONBodyResp.put("Card_Number", "5555555555554444")
                            objJSONBodyResp.put("Card_Number", "5105105105105100")
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        2 -> {
                            objJSONBodyResp.put("Card_Number", "371449635398431")
                            objJSONBodyResp.put("Card_Number", "5610591081018250")
                            objJSONBodyResp.put("Card_Number", "30569309025904")
                            objJSONBodyResp.put("Card_Number", "6011111111111117")
                            objJSONBodyResp.put("Card_Number", "3530111333300000")
                            objJSONBodyResp.put("Card_Number", "5555555555554444")
                            objJSONBodyResp.put("Card_Number", "5105105105105100")
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        3 -> {
                            objJSONBodyResp.put("Card_Number", "5610591081018250")
                            objJSONBodyResp.put("Card_Number", "30569309025904")
                            objJSONBodyResp.put("Card_Number", "6011111111111117")
                            objJSONBodyResp.put("Card_Number", "3530111333300000")
                            objJSONBodyResp.put("Card_Number", "5555555555554444")
                            objJSONBodyResp.put("Card_Number", "5105105105105100")
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        4 -> {
                            objJSONBodyResp.put("Card_Number", "30569309025904")
                            objJSONBodyResp.put("Card_Number", "6011111111111117")
                            objJSONBodyResp.put("Card_Number", "3530111333300000")
                            objJSONBodyResp.put("Card_Number", "5555555555554444")
                            objJSONBodyResp.put("Card_Number", "5105105105105100")
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        5 -> {
                            objJSONBodyResp.put("Card_Number", "6011111111111117")
                            objJSONBodyResp.put("Card_Number", "3530111333300000")
                            objJSONBodyResp.put("Card_Number", "5555555555554444")
                            objJSONBodyResp.put("Card_Number", "5105105105105100")
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        6 -> {
                            objJSONBodyResp.put("Card_Number", "3530111333300000")
                            objJSONBodyResp.put("Card_Number", "5555555555554444")
                            objJSONBodyResp.put("Card_Number", "5105105105105100")
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        7 -> {
                            objJSONBodyResp.put("Card_Number", "5555555555554444")
                            objJSONBodyResp.put("Card_Number", "5105105105105100")
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        8 -> {
                            objJSONBodyResp.put("Card_Number", "5105105105105100")
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        9 -> {
                            objJSONBodyResp.put("Card_Number", "4111111111111111")
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        10 -> {
                            objJSONBodyResp.put("Card_Number", "4012888888881881")
                            objJSONBodyResp.put("Card_Number", "4222222222222")
                        }
                        else -> objJSONBodyResp.put("Card_Number", "4222222222222")
                    }
                    statusRC = "0000"
                    statusMessage = "Success"
                } else {
                    statusMessage = "Command not found"
                    statusRC = "0901"
                }
                objJSONBodyResp.put("Result_RC", statusMessage)
                objJSONBodyResp.put("RC", statusRC)
                objJSONBodyResp.put("timestamp", transmissionDateTime.format(now))
                val body: PrintStream = response.printStream
                val time = System.currentTimeMillis()
                response.setValue("Content-Type", "application/json")
                response.setValue("Server", "hsm/1.0 (Simple 4.0)")
                response.setDate("Date", time)
                response.setDate("Last-Modified", time)
                logger.info(workerName + "Response to client = " + objJSONBodyResp.toString())
                body.println(objJSONBodyResp.toString())
                body.close()
            } catch (ex: IOException) {
                try {
                    objJSONBodyResp = JSONObject()
                    objJSONBodyResp.put("Result_RC", "Message failed, unknown error")
                    objJSONBodyResp.put("RC", "0999")
                    logger.error(workerName + "Catch Error = " + ex.message)
                    val body: PrintStream
                    body = response.printStream
                    val time = System.currentTimeMillis()
                    response.setValue("Content-Type", "application/json")
                    response.setValue("Server", "hsm/1.0 (Simple 4.0)")
                    response.setDate("Date", time)
                    response.setDate("Last-Modified", time)
                    logger.info(workerName + "Response Unknown Error to client | Connection Error = " + objJSONBodyResp.toString())
                    body.println(objJSONBodyResp.toString())
                    body.close()
                } catch (ex1: IOException) {
                    logger.error(workerName + "Connection Error | Udah ngga ada obatnya lagi versi koneksi = " + ex1)
                } catch (ex1: Exception) {
                    logger.error(workerName + "Connection Error | Udah ngga ada obatnya lagi versi enkripsi = " + ex1)
                }
            } catch (ex: Exception) {
                try {
                    objJSONBodyResp = JSONObject()
                    objJSONBodyResp.put("Result_RC", "Message failed, unatuthorized connection")
                    objJSONBodyResp.put("RC", "0998")
                    logger.error(workerName + "Catch Error = " + ex.message)
                    val body: PrintStream
                    body = response.printStream
                    val time = System.currentTimeMillis()
                    response.setValue("Content-Type", "application/json")
                    response.setValue("Server", "hsm/1.0 (Simple 4.0)")
                    response.setDate("Date", time)
                    response.setDate("Last-Modified", time)
                    logger.error(workerName + "Response Unknown Error to client | Decrypt Error = " + objJSONBodyResp.toString())
                    body.println(objJSONBodyResp.toString())
                    body.close()
                } catch (ex1: IOException) {
                    logger.error(workerName + "Decrypt Error | Udah ngga ada obatnya lagi versi koneksi = " + ex1)
                } catch (ex1: Exception) {
                    logger.error(workerName + "Decrypt Error | Udah ngga ada obatnya lagi versi enkripsi = " + ex1)
                }
            }
        }
    }

    init {
        executor = Executors.newFixedThreadPool(size)
    }

    override fun handle(request: Request, response: Response) {
        val task = Task(request, response)
        executor.execute(task)
    }

    companion object {
        private val logger = Logger.getLogger(MainClass::class.java)
        private var EMPTY_STRING = ""
        private var KEYSTORE_PROPERTY = "javax.net.ssl.keyStore"
        private var KEYSTORE_PASSWORD_PROPERTY = "javax.net.ssl.keyStorePassword"
        private var KEYSTORE_TYPE_PROPERTY = "javax.net.ssl.keyStoreType"
        var KEYSTORE_ALIAS_PROPERTY = "javax.net.ssl.keyStoreAlias"
        private const val SESSION_COOKIE_NAME = "Fixd-Session"

        @Throws(Exception::class)
        fun createSSLContext(): SSLContext {
            val keyStoreFile = "/app/" + System.getProperty(KEYSTORE_PROPERTY)
            val keyStorePassword = System.getProperty(
                KEYSTORE_PASSWORD_PROPERTY,
                EMPTY_STRING
            )
            val keyStoreType = System.getProperty(KEYSTORE_TYPE_PROPERTY, KeyStore.getDefaultType())
            var keyStore = loadKeyStore(keyStoreFile, keyStorePassword, null)
            var keyStoreFileInpuStream: FileInputStream? = null
            try {
                if (keyStoreFile != null) {
                    keyStoreFileInpuStream = FileInputStream(keyStoreFile)
                    keyStore = KeyStore.getInstance(keyStoreType)
                    keyStore.load(keyStoreFileInpuStream, keyStorePassword.toCharArray())
                }
            } finally {
                keyStoreFileInpuStream?.close()
            }
            val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray())
            val sslContext = SSLContext.getInstance("SSLv3")
            // sslContext.init(keyManagerFactory.getKeyManagers(), new TrustManager[]{new NaiveX509TrustManager()}, null);
            sslContext.init(keyManagerFactory.keyManagers, null, null)
            return sslContext
        }

        @Throws(Exception::class)
        fun loadKeyStore(
            keyStoreFilePath: String, keyStorePassword: String, keyStoreType: String?
        ): KeyStore? {
            var keyStore: KeyStore? = null
            val keyStoreFile = File(keyStoreFilePath)
            if (keyStoreFile.isFile) {
                keyStore = KeyStore.getInstance(keyStoreType ?: KeyStore.getDefaultType())
                keyStore.load(
                    FileInputStream(keyStoreFile),
                    keyStorePassword.toCharArray()
                )
            }
            return keyStore
        }

        @Throws(IOException::class, Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            PropertyConfigurator.configure("/app/Properties/log4j.properties")
            getGatewayProperties()
            System.setProperty(KEYSTORE_PROPERTY, "redcpt.keystore")
            System.setProperty(KEYSTORE_PASSWORD_PROPERTY, "redcptkeystore")
            run()
            val container: Container = MainClass(maxClient)
            val server: SocketProcessor = ContainerSocketProcessor(container)
            val connection: Connection = SocketConnection(server)
            val address: SocketAddress = InetSocketAddress(gatewayPort)
            connection.connect(address, createSSLContext())
            logger.debug("31072022 | 1.1  | RedCPT HSM demo!!! ")
        }
    }
}