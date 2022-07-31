package com.redcpt.isogateway.log.level

import org.apache.log4j.Level
import java.util.*

class LogTrx private constructor(arg0: Int, arg1: String?, arg2: Int) : Level(arg0, arg1, arg2) {
    companion object {
        private const val LOGTRXINT = DEBUG_INT + 1
        private val LOGTRX: Level = LogTrx(LOGTRXINT, "LOGTRX", 7)
        fun toLevel(sArg: String?): Level {
            return if (sArg != null && sArg.uppercase(Locale.getDefault()) == "LOGTRX") {
                LOGTRX
            } else toLevel(
                sArg,
                FATAL
            )
        }

        fun toLevel(`val`: Int): Level {
            return if (`val` == LOGTRXINT) {
                LOGTRX
            } else toLevel(`val`, FATAL)
        }

        fun toLevel(`val`: Int, defaultLevel: Level?): Level {
            return if (`val` == LOGTRXINT) {
                LOGTRX
            } else Level.toLevel(`val`, defaultLevel)
        }

        fun toLevel(sArg: String?, defaultLevel: Level?): Level {
            return if (sArg != null && sArg.uppercase(Locale.getDefault()) == "LOGTRX") {
                LOGTRX
            } else Level.toLevel(sArg, defaultLevel)
        }
    }
}