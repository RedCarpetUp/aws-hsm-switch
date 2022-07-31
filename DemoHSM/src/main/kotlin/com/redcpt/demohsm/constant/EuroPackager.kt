package com.redcpt.demohsm.constant

import org.jpos.iso.*


class EuroPackager : ISOBasePackager() {
    private var hp = HashMap<String, Int>()

    private var fld: Array<ISOFieldPackager> = arrayOf(
        /*000*/
        IFA_NUMERIC(4, "MESSAGE TYPE INDICATOR"),
        /*001*/
        IFB_BITMAP(16, "BIT MAP"),
        /*002*/
        IFA_LLNUM(19, "PAN - PRIMARY ACCOUNT NUMBER"),
        /*003*/
        IFA_NUMERIC(6, "PROCESSING CODE"),
        /*004*/
        IFA_NUMERIC(12, "AMOUNT, TRANSACTION"),
        /*005*/
        IFA_NUMERIC(12, "AMOUNT, SETTLEMENT"),
        /*006*/
        IFA_NUMERIC(12, "AMOUNT, CARDHOLDER BILLING"),
        /*007*/
        IFA_NUMERIC(10, "TRANSMISSION DATE AND TIME"),
        /*008*/
        IFA_NUMERIC(8, "AMOUNT, CARDHOLDER BILLING FEE"),
        /*009*/
        IFA_NUMERIC(8, "CONVERSION RATE, SETTLEMENT"),
        /*010*/
        IFA_NUMERIC(8, "CONVERSION RATE, CARDHOLDER BILLING"),
        /*011*/
        IFA_NUMERIC(6, "SYSTEM TRACE AUDIT NUMBER"),
        /*012*/
        IFA_NUMERIC(6, "TIME, LOCAL TRANSACTION"),
        /*013*/
        IFA_NUMERIC(4, "DATE, LOCAL TRANSACTION"),
        /*014*/
        IFA_NUMERIC(4, "DATE, EXPIRATION"),
        /*015*/
        IFA_NUMERIC(4, "DATE, SETTLEMENT"),
        /*016*/
        IFA_NUMERIC(4, "DATE, CONVERSION"),
        /*017*/
        IFA_NUMERIC(4, "DATE, CAPTURE"),
        /*018*/
        IFA_NUMERIC(4, "MERCHANTS TYPE"),
        /*019*/
        IFA_NUMERIC(3, "ACQUIRING INSTITUTION COUNTRY CODE"),
        /*020*/
        IFA_NUMERIC(3, "PAN EXTENDED COUNTRY CODE"),
        /*021*/
        IFA_NUMERIC(3, "FORWARDING INSTITUTION COUNTRY CODE"),
        /*022*/
        IFA_NUMERIC(3, "POINT OF SERVICE ENTRY MODE"),
        /*023*/
        IFA_NUMERIC(3, "CARD SEQUENCE NUMBER"),
        /*024*/
        IFA_NUMERIC(3, "NETWORK INTERNATIONAL IDENTIFIEER"),
        /*025*/
        IFA_NUMERIC(2, "POINT OF SERVICE CONDITION CODE"),
        /*026*/
        IFA_NUMERIC(2, "POINT OF SERVICE PIN CAPTURE CODE"),
        /*027*/
        IFA_NUMERIC(1, "AUTHORIZATION IDENTIFICATION RESP LEN"),
        /*028*/
        IFA_AMOUNT(9, "AMOUNT, TRANSACTION FEE"),
        /*029*/
        IFA_AMOUNT(9, "AMOUNT, SETTLEMENT FEE"),
        /*030*/
        IFA_AMOUNT(9, "AMOUNT, TRANSACTION PROCESSING FEE"),
        /*031*/
        IFA_AMOUNT(9, "AMOUNT, SETTLEMENT PROCESSING FEE"),
        /*032*/
        IFA_LLNUM(11, "ACQUIRING INSTITUTION IDENT CODE"),
        /*033*/
        IFA_LLNUM(11, "FORWARDING INSTITUTION IDENT CODE"),
        /*034*/
        IFA_LLCHAR(28, "PAN EXTENDED"),
        /*035*/
        IFA_LLNUM(37, "TRACK 2 DATA"),
        /*036*/
        IFA_LLLCHAR(104, "TRACK 3 DATA"),
        /*037*/
        IF_CHAR(12, "RETRIEVAL REFERENCE NUMBER"),
        /*038*/
        IF_CHAR(6, "AUTHORIZATION IDENTIFICATION RESPONSE"),
        /*039*/
        IF_CHAR(2, "RESPONSE CODE"),
        /*040*/
        IF_CHAR(3, "SERVICE RESTRICTION CODE"),
        /*041*/
        IF_CHAR(8, "CARD ACCEPTOR TERMINAL IDENTIFICACION"),
        /*042*/
        IF_CHAR(15, "CARD ACCEPTOR IDENTIFICATION CODE"),
        /*043*/
        IF_CHAR(40, "CARD ACCEPTOR NAME/LOCATION"),
        /*044*/
        IFA_LLCHAR(25, "ADITIONAL RESPONSE DATA"),
        /*045*/
        IFA_LLCHAR(76, "TRACK 1 DATA"),
        /*046*/
        IFA_LLLCHAR(999, "ADITIONAL DATA - ISO"),
        /*047*/
        IFA_LLLCHAR(999, "ADITIONAL DATA - NATIONAL"),
        /*048*/
        IFA_LLLBINARY(999, "EUROPAY FIELD 48"),
        /*049*/
        IF_CHAR(3, "CURRENCY CODE, TRANSACTION"),
        /*050*/
        IF_CHAR(3, "CURRENCY CODE, SETTLEMENT"),
        /*051*/
        IF_CHAR(3, "CURRENCY CODE, CARDHOLDER BILLING"),
        /*052*/
        IFA_BINARY(8, "PIN DATA"),
        /*053*/
        IFA_NUMERIC(16, "SECURITY RELATED CONTROL INFORMATION"),
        /*054*/
        IFA_LLLCHAR(120, "ADDITIONAL AMOUNTS"),
        /*055*/
        IFA_LLLCHAR(999, "RESERVED ISO"),
        /*056*/
        IFA_LLLCHAR(999, "RESERVED ISO"),
        /*057*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL"),
        /*058*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL"),
        /*059*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL"),
        /*060*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE"),
        /*061*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE"),
        /*062*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE"),
        /*063*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE"),
        /*064*/
        IFA_BINARY(8, "MESSAGE AUTHENTICATION CODE FIELD"),
        /*065*/
        IFA_BINARY(8, "BITMAP, EXTENDED"),
        /*066*/
        IFA_NUMERIC(1, "SETTLEMENT CODE"),
        /*067*/
        IFA_NUMERIC(2, "EXTENDED PAYMENT CODE"),
        /*068*/
        IFA_NUMERIC(3, "RECEIVING INSTITUTION COUNTRY CODE"),
        /*069*/
        IFA_NUMERIC(3, "SETTLEMENT INSTITUTION COUNTRY CODE"),
        /*070*/
        IFA_NUMERIC(3, "NETWORK MANAGEMENT INFORMATION CODE"),
        /*071*/
        IFA_NUMERIC(4, "MESSAGE NUMBER"),
        /*072*/
        IFA_NUMERIC(4, "MESSAGE NUMBER LAST"),
        /*073*/
        IFA_NUMERIC(6, "DATE ACTION"),
        /*074*/
        IFA_NUMERIC(10, "CREDITS NUMBER"),
        /*075*/
        IFA_NUMERIC(10, "CREDITS REVERSAL NUMBER"),
        /*076*/
        IFA_NUMERIC(10, "DEBITS NUMBER"),
        /*077*/
        IFA_NUMERIC(10, "DEBITS REVERSAL NUMBER"),
        /*078*/
        IFA_NUMERIC(10, "TRANSFER NUMBER"),
        /*079*/
        IFA_NUMERIC(10, "TRANSFER REVERSAL NUMBER"),
        /*080*/
        IFA_NUMERIC(10, "INQUIRIES NUMBER"),
        /*081*/
        IFA_NUMERIC(10, "AUTHORIZATION NUMBER"),
        /*082*/
        IFA_NUMERIC(12, "CREDITS, PROCESSING FEE AMOUNT"),
        /*083*/
        IFA_NUMERIC(12, "CREDITS, TRANSACTION FEE AMOUNT"),
        /*084*/
        IFA_NUMERIC(12, "DEBITS, PROCESSING FEE AMOUNT"),
        /*085*/
        IFA_NUMERIC(12, "DEBITS, TRANSACTION FEE AMOUNT"),
        /*086*/
        IFA_NUMERIC(16, "CREDITS, AMOUNT"),
        /*087*/
        IFA_NUMERIC(16, "CREDITS, REVERSAL AMOUNT"),
        /*088*/
        IFA_NUMERIC(16, "DEBITS, AMOUNT"),
        /*089*/
        IFA_NUMERIC(16, "DEBITS, REVERSAL AMOUNT"),
        /*090*/
        IFA_NUMERIC(42, "ORIGINAL DATA ELEMENTS"),
        /*091*/
        IF_CHAR(1, "FILE UPDATE CODE"),
        /*092*/
        IF_CHAR(2, "FILE SECURITY CODE"),
        /*093*/
        IF_CHAR(5, "RESPONSE INDICATOR"),
        /*094*/
        IF_CHAR(7, "SERVICE INDICATOR"),
        /*095*/
        IF_CHAR(42, "REPLACEMENT AMOUNTS"),
        /*096*/
        IFA_BINARY(8, "MESSAGE SECURITY CODE"),
        /*097*/
        IFA_AMOUNT(17, "AMOUNT, NET SETTLEMENT"),
        /*098*/
        IF_CHAR(25, "PAYEE"),
        /*099*/
        IFA_LLNUM(11, "SETTLEMENT INSTITUTION IDENT CODE"),
        /*100*/
        IFA_LLNUM(11, "RECEIVING INSTITUTION IDENT CODE"),
        /*101*/
        IFA_LLCHAR(17, "FILE NAME"),
        /*102*/
        IFA_LLCHAR(28, "ACCOUNT IDENTIFICATION 1"),
        /*103*/
        IFA_LLCHAR(28, "ACCOUNT IDENTIFICATION 2"),
        /*104*/
        IFA_LLLCHAR(100, "TRANSACTION DESCRIPTION"),
        /*105*/
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        /*106*/
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        /*107*/
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        /*108*/
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        /*109*/
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        /*110*/
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        /*111*/
        IFA_LLLCHAR(999, "RESERVED ISO USE"),
        /*112*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        /*113*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        /*114*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        /*115*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        /*116*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        /*117*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        /*118*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        /*119*/
        IFA_LLLCHAR(999, "RESERVED NATIONAL USE"),
        /*120*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        /*121*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        /*122*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        /*123*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        /*124*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        /*125*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        /*126*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        /*127*/
        IFA_LLLCHAR(999, "RESERVED PRIVATE USE"),
        /*128*/
        IFA_LLLCHAR(999, "MAC 2")
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