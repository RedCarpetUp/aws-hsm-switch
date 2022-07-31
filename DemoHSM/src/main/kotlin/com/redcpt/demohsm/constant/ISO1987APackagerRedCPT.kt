package com.redcpt.demohsm.constant

import org.jpos.iso.*

class ISO1987APackagerRedCPT : ISOBasePackager() {
    private var hp = HashMap<String, Int>()

    private var fld: Array<ISOFieldPackager> = arrayOf(
        /*000*/ IFA_NUMERIC(4, "MTI"),
        /*001*/ IFA_BITMAP(16, "BITMAP"),
        /*002*/ IFA_LLCHAR(19, "PAN"),
        /*003*/ IFA_NUMERIC(6, "PROCESSING_CODE"),
        /*004*/ IFA_NUMERIC(12, "TOTAL_AMOUNT"),
        /*005*/ IFEP_LLCHAR(0, ""),
        /*006*/ IFEP_LLCHAR(0, ""),
        /*007*/ IFA_NUMERIC(10, "GMT_TIME"),
        /*008*/ IFEP_LLCHAR(0, ""),
        /*009*/ IFEP_LLCHAR(0, ""),
        /*010*/ IFEP_LLCHAR(0, ""),
        /*011*/ IFA_NUMERIC(6, "STAN"),
        /*012*/ IFA_NUMERIC(6, "LOCAL_TIME"),
        /*013*/ IFA_NUMERIC(4, "LOCAL_DATE"),
        /*014*/ IFA_NUMERIC(4, ""),
        /*015*/ IFA_NUMERIC(4, "SETTLEMENT_DATE"),
        /*016*/ IFEP_LLCHAR(0, ""),
        /*017*/ IFEP_LLCHAR(0, ""),
        /*018*/ IFA_NUMERIC(4, "MERCHANT_TYPE"),
        /*019*/ IFEP_LLCHAR(0, ""),
        /*020*/ IFEP_LLCHAR(0, ""),
        /*021*/ IFEP_LLCHAR(0, ""),
        /*022*/ IFEP_LLCHAR(0, ""),
        /*023*/ IFEP_LLCHAR(0, ""),
        /*024*/ IFA_NUMERIC(3, "NII"),
        /*025*/ IFEP_LLCHAR(0, ""),
        /*026*/ IFA_NUMERIC(4, ""),
        /*027*/ IFEP_LLCHAR(0, ""),
        /*028*/ IFEP_LLCHAR(0, ""),
        /*029*/ IFEP_LLCHAR(0, ""),
        /*030*/ IFEP_LLCHAR(0, ""),
        /*031*/ IFEP_LLCHAR(0, ""),
        /*032*/ IFA_LLNUM(99, "CA_CODE"), // IFA_LLCHAR
        /*033*/ IFA_LLNUM(99, "BANK_CODE"), // IFEP_LLCHAR
        /*034*/ IFEP_LLCHAR(0, ""),
        /*035*/ IFEP_LLCHAR(0, ""),
        /*036*/ IFEP_LLCHAR(0, ""),
        /*037*/ IF_CHAR(12, "RRN"),
        /*038*/ IF_CHAR(6, "AIR"),
        /*039*/ IF_CHAR(2, "RESPONSE_CODE"),
        /*040*/ IFA_NUMERIC(3, "ACTION_CODE"),
        /*041*/ IF_CHAR(8, "TERMINAL_ID"),
        /*042*/ IF_CHAR(15, "LOKET_ID"),
        /*043*/ IFEP_LLCHAR(0, ""),
        /*044*/ IFEP_LLCHAR(0, ""),
        /*045*/ IFEP_LLCHAR(0, ""),
        /*046*/ IFEP_LLCHAR(0, ""),
        /*047*/ IFEP_LLCHAR(0, ""),
        /*048*/ IFA_LLLCHAR(999, "DATA48"),
        /*049*/ IF_CHAR(3, "CURRENCY_CODE"),
        /*050*/ IFEP_LLCHAR(0, ""),
        /*051*/ IFEP_LLCHAR(0, ""),
        /*052*/ IFEP_LLCHAR(0, ""),
        /*053*/ IFEP_LLCHAR(0, ""),
        /*054*/ IFEP_LLCHAR(0, ""),
        /*055*/ IFEP_LLCHAR(0, ""),
        /*056*/ IFA_LLNUM(99, ""),
        /*057*/ IFA_NUMERIC(4, ""),
        /*058*/ IFA_NUMERIC(3, ""),
        /*059*/ IFEP_LLCHAR(0, ""),
        /*060*/ IFA_LLLCHAR(999, "DATA60"),
        /*061*/ IFA_LLLCHAR(999, "DATA61"),
        /*062*/ IFA_LLLCHAR(999, "DATA62"),
        /*063*/ IFA_LLLCHAR(999, "DATA63"),
        /*064*/ IFEP_LLCHAR(0, ""),
        /*065*/ IFEP_LLCHAR(0, ""),
        /*066*/ IFEP_LLCHAR(0, ""),
        /*067*/ IFA_LLLCHAR(999, ""),
        /*068*/ IFEP_LLCHAR(0, ""),
        /*069*/ IFEP_LLCHAR(0, ""),
        /*070*/ IFA_NUMERIC(3, "NETWORK_CODE"),
        /*071*/ IFEP_LLCHAR(0, ""),
        /*072*/ IFEP_LLCHAR(0, ""),
        /*073*/ IFEP_LLCHAR(0, ""),
        /*074*/ IFEP_LLCHAR(0, ""),
        /*075*/ IFA_LLNUM(99, ""),
        /*076*/ IFA_NUMERIC(4, ""),
        /*077*/ IFA_NUMERIC(3, ""),
        /*078*/ IFEP_LLCHAR(0, ""),
        /*079*/ IFEP_LLCHAR(0, ""),
        /*080*/ IFEP_LLCHAR(0, ""),
        /*081*/ IFEP_LLCHAR(0, ""),
        /*082*/ IFEP_LLCHAR(0, ""),
        /*083*/ IFEP_LLCHAR(0, ""),
        /*084*/ IFEP_LLCHAR(0, ""),
        /*085*/ IFA_LLLCHAR(999, ""),
        /*086*/ IFEP_LLCHAR(0, ""),
        /*087*/ IFEP_LLCHAR(0, ""),
        /*088*/ IFEP_LLCHAR(0, ""),
        /*089*/ IFEP_LLCHAR(0, ""),
        /*090*/ IFA_NUMERIC(42, "ORIGINAL_DATA_ELEMENT")
    )


    /**
     * Fungsi mengatur bit beserta tipenya
     */
    fun setFieldType(field: Int, tipe: String, namaField: String, jumField: Int) {
        hp[namaField] = field
    }


    /**
     * Fungsi menggenerate packager ISO1987A
     */
    fun setPackager() {
        setFieldPackager(fld)
        var a: Int = 0
        while (a < fld.size) {
            if (fld[a].description.isNotEmpty()) {
                hp[fld[a].description] = a
            }
            a++
        }
    }
}