package com.redcpt.demohsm.main

import org.apache.log4j.Logger

object PropertiesActivator {
    private val logger = Logger.getLogger(
        PropertiesActivator::class.java
    )

    /**
     * Menjalankan seluruh hasil konfigurasi
     */
    fun run() {
        logger.info("Activator Running !!!")
    }
}