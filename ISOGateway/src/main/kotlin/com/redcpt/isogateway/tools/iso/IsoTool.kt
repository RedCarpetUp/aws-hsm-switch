package com.redcpt.isogateway.tools.iso

import org.jpos.iso.ISOMsg

object IsoTool {
    fun clearISO(iso: ISOMsg): ISOMsg {
        val bit = IntArray(128)
        for (i in 0..127) {
            bit[i] = i + 1
        }
        iso.unset(*bit)
        return iso
    }
}