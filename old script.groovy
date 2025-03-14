import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*
import groovy.json.JsonBuilder

def convertToJSON(xmlNode, parentName) {
    def metrics = []

    // Read EDI_DC40 data
    def ediDC40 = xmlNode.'EDI_DC40'
    if (ediDC40) {
        def metric = [:]
        metric.name = "${parentName}/IDOC/EDI_DC40"
        metric.datatype = 16
        metric.dataset_value = [:]
        
        def columns = ["TABNAM", "MANDT", "DOCNUM", "DOCREL", "STATUS", "DIRECT", "OUTMOD", "EXPRSS", "TEST", "IDOCTYP", "CIMTYP", "MESTYP", "MESCOD", "MESFCT", "STD", "STDVRS", "STDMES", "SNDPOR", "SNDPRT", "SNDPFC", "SNDPRN", "SNDSAD", "SNDLAD", "RCVPOR", "RCVPRT", "RCVPFC", "RCVPRN", "RCVSAD", "RCVLAD", "CREDAT", "CRETIM", "REFINT", "REFGRP", "REFMES", "ARCKEY", "SERIAL", "SEGMENT"]
        def types = (1..37).collect { 12 }
        def rows = [[elements: columns.collect { [string_value: ediDC40."${it}".text()] }]]

        metric.dataset_value.num_of_columns = columns.size()
        metric.dataset_value.columns = columns
        metric.dataset_value.types = types
        metric.dataset_value.rows = rows
        
        metrics << metric
    }
	
	// Read _-SCWM_-E1LTORH Data
	
	 def E1LTORH = xmlNode.'_-SCWM_-E1LTORH'
	if (E1LTORH) {
        def Elements_E1LTORH = [:]
        Elements_E1LTORH.name = "${parentName}/IDOC/_-SCWM_-E1LTORH"
        Elements_E1LTORH.datatype = 16
        Elements_E1LTORH.dataset_value = [:]
        
        def torhcolumns = ["LGNUM", "WHO", "QUEUE", "BNAME", "LSD_DATE", "LSD_TIME", "PLANDURA", "UNIT_T"]
        def torhtypes = (1..8).collect { 12 }
        def torhrows = [[elements: torhcolumns.collect { [string_value: E1LTORH."${it}".text()] }]]

        Elements_E1LTORH.dataset_value.num_of_columns = torhcolumns.size()
        Elements_E1LTORH.dataset_value.columns = torhcolumns
        Elements_E1LTORH.dataset_value.types = torhtypes
        Elements_E1LTORH.dataset_value.rows = torhrows
        
        metrics << Elements_E1LTORH
    }
	
	//Read _-SCWM_-E1LPHUX Data
	
	 def E1LPHUX = xmlNode.'_-SCWM_-E1LPHUX'
	if (E1LPHUX) {
        def Elements_E1LPHUX = [:]
        Elements_E1LPHUX.name = "${parentName}/IDOC/_-SCWM_-E1LTORH/_-SCWM_-E1LPHUX"
        Elements_E1LPHUX.datatype = 16
        Elements_E1LPHUX.dataset_value = [:]
        
        def phuxcolumns = ["LGNUM", "WHO", "HUIDENT", "HUKNG", "PMAT", "ANZHU", "KZRTN", "LETYP", "LGTYP", "LGPLA", "TANUM", "RSRC", "SEGMENT"]
        def phuxtypes = (1..13).collect { 12 }
        def phuxrows = [[elements: phuxcolumns.collect { [string_value: E1LPHUX."${it}".text()] }]]

        Elements_E1LPHUX.dataset_value.num_of_columns = phuxcolumns.size()
        Elements_E1LPHUX.dataset_value.columns = phuxcolumns
        Elements_E1LPHUX.dataset_value.types = phuxtypes
        Elements_E1LPHUX.dataset_value.rows = phuxrows
        
        metrics << Elements_E1LPHUX
    }
    
	// Read _-SCWM_-E1LTORI Data
	
	 def E1LTORI = xmlNode.'_-SCWM_-E1LTORI'
	if (E1LTORI) {
        def Elements_E1LTORI = [:]
        Elements_E1LTORI.name = "${parentName}/IDOC/_-SCWM_-E1LTORH/_-SCWM_-E1LTORI"
        Elements_E1LTORI.datatype = 16
        Elements_E1LTORI.dataset_value = [:]
        
        def toricolumns = ["TANUM", "PROCTY", "MATNR", "ENTITLED", "ENTITLED_ROLE", "OWNER", "OWNER_ROLE", "CHARG", "LETYP", "ANFME", "ALTME", "OPUNIT", "VSOLA", "VSOLM", "MEINS", "VLTYP", "VLBER", "VLPLA", "NLTYP", "NLBER", "NLPLA", "VLENR", "NLENR", "TRART", "PROCS", "PRCES", "WDATU", "WDATZ", "VFDAT", "ZEUGN", "KOMPL", "SQUIT", "KZQUI", "MAKTX", "DOCCAT", "DOCNO", "ITEMNO", "KZNKO", "CAT", "STOCK_DOCCAT", "STOCK_DOCNO", "STOCK_ITMNO", "WAVE", "FLGHUTO", "PRIORITY", "PICK_COMP_DATE", "PICK_COMP_TIME", "IDPLATE", "PICK_ALL", "DSTGRP", "STOCK_USAGE"]
        def toritypes = (1..51).collect { 12 }
        def torirows = [[elements: toricolumns.collect { [string_value: E1LTORI."${it}".text()] }]]

        Elements_E1LTORI.dataset_value.num_of_columns = toricolumns.size()
        Elements_E1LTORI.dataset_value.columns = toricolumns
        Elements_E1LTORI.dataset_value.types = toritypes
        Elements_E1LTORI.dataset_value.rows = torirows
        
        metrics << Elements_E1LTORI
    }

	// Read _-SCWM_-E1LCRSE Data
	
	 def E1LCRSE = xmlNode.'_-SCWM_-E1LCRSE'
	if (E1LCRSE) {
        def Elements_E1LCRSE = [:]
        Elements_E1LCRSE.name = "${parentName}/IDOC/_-SCWM_-E1LTORH/_-SCWM_-E1LCRSE"
        Elements_E1LCRSE.datatype = 16
        Elements_E1LCRSE.dataset_value = [:]
        
        def crsecolumns = ["SERID", "UII", "SEGMENT"]
        def crsetypes = (1..3).collect { 12 }
        def crserows = [[elements: crsecolumns.collect { [string_value: E1LCRSE."${it}".text()] }]]

        Elements_E1LCRSE.dataset_value.num_of_columns = crsecolumns.size()
        Elements_E1LCRSE.dataset_value.columns = crsecolumns
        Elements_E1LCRSE.dataset_value.types = crsetypes
        Elements_E1LCRSE.dataset_value.rows = crserows
        
        metrics << Elements_E1LCRSE
    }


    return metrics
}

def Message processData(Message message) {
    def ReadPayload = message.getBody(String)
    def xmlParser = new XmlSlurper().parseText(ReadPayload)

    def finalOutput = [:]
    def unixT = (long) (System.currentTimeMillis() / 1000)
    finalOutput.timestamp = unixT
    if (xmlParser.IDOC) {
        finalOutput.metrics = convertToJSON(xmlParser.IDOC, xmlParser.name())
    } else {
        finalOutput.metrics = []
    }
        finalOutput.seq = 2
    def jsonOutput = new JsonBuilder(finalOutput).toPrettyString()

    message.setBody(jsonOutput)
    return message
}