import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*
import groovy.json.JsonBuilder

// Function to process each segment and its nested segments
def processSegment(xmlNode, parentPath, segmentDefinitions) {
    def metrics = []
    
    // Process predefined segment types first
    segmentDefinitions.each { segment, definition ->
        def segmentNode = xmlNode."$segment"
        if (segmentNode) {
            def currentPath = "${parentPath}/$segment"
            
            // Create metric for current segment
            def metric = [:]
            metric.name = currentPath
            metric.datatype = 16
            metric.dataset_value = [:]
            
            // Set columns and types from hardcoded definitions
            def columns = definition.columns
            def types = definition.types
            
            metric.dataset_value.num_of_columns = columns.size()
            metric.dataset_value.columns = columns
            metric.dataset_value.types = types
            
            // Create rows using values from segmentNode
            def rows = [[elements: columns.collect { column ->
                [string_value: segmentNode."$column".text()]
            }]]
            metric.dataset_value.rows = rows
            
            metrics << metric
        }
    }
    
    // Process nested segments recursively
    xmlNode.children().each { child ->
        def nodeName = child.name()
        if (nodeName && !segmentDefinitions.containsKey(nodeName)) { // Skip predefined segments
            def currentPath = "${parentPath}/${nodeName}"
            metrics.addAll(processSegment(child, currentPath, segmentDefinitions)) // Recursively process nested segments
        }
    }
    
    return metrics
}

def Message processData(Message message) {
    def ReadPayload = message.getBody(String)
    def xmlParser = new XmlSlurper().parseText(ReadPayload)
    
    def finalOutput = [:]
    finalOutput.timestamp = System.currentTimeMillis() / 1000 as long
    
    // Hardcoded segment definitions (columns and types)
    def segmentDefinitions = [
        "EDI_DC40": [
            columns: ["TABNAM", "MANDT", "DOCNUM", "DOCREL", "STATUS", "DIRECT", "OUTMOD", "EXPRSS", "TEST", "IDOCTYP", "CIMTYP", "MESTYP", "MESCOD", "MESFCT", "STD", "STDVRS", "STDMES", "SNDPOR", "SNDPRT", "SNDPFC", "SNDPRN", "SNDSAD", "SNDLAD", "RCVPOR", "RCVPRT", "RCVPFC", "RCVPRN", "RCVSAD", "RCVLAD", "CREDAT", "CRETIM", "REFINT", "REFGRP", "REFMES", "ARCKEY", "SERIAL", "SEGMENT"],
            types: (1..37).collect { 12 }
        ],
        "_-SCWM_-E1LTORH": [
            columns: ["LGNUM", "WHO", "QUEUE", "BNAME", "LSD_DATE", "LSD_TIME", "PLANDURA", "UNIT_T"],
            types: (1..8).collect { 12 }
        ],
        "_-SCWM_-E1LPHUX": [
            columns: ["LGNUM", "WHO", "HUIDENT", "HUKNG", "PMAT", "ANZHU", "KZRTN", "LETYP", "LGTYP", "LGPLA", "TANUM", "RSRC", "SEGMENT"],
            types: (1..13).collect { 12 }
        ],
        "_-SCWM_-E1LTORI": [
            columns: ["TANUM", "PROCTY", "MATNR", "ENTITLED", "ENTITLED_ROLE", "OWNER", "OWNER_ROLE", "CHARG", "LETYP", "ANFME", "ALTME", "OPUNIT", "VSOLA", "VSOLM", "MEINS", "VLTYP", "VLBER", "VLPLA", "NLTYP", "NLBER", "NLPLA", "VLENR", "NLENR", "TRART", "PROCS", "PRCES", "WDATU", "WDATZ", "VFDAT", "ZEUGN", "KOMPL", "SQUIT", "KZQUI", "MAKTX", "DOCCAT", "DOCNO", "ITEMNO", "KZNKO", "CAT", "STOCK_DOCCAT", "STOCK_DOCNO", "STOCK_ITMNO", "WAVE", "FLGHUTO", "PRIORITY", "PICK_COMP_DATE", "PICK_COMP_TIME", "IDPLATE", "PICK_ALL", "DSTGRP", "STOCK_USAGE"],
            types: (1..51).collect { 12 }
        ],
        "_-SCWM_-E1LCRSE": [
            columns: ["SERID", "UII", "SEGMENT"],
            types: (1..3).collect { 12 }
        ]
    ]
    
    // Start processing from IDOC node
    if (xmlParser.IDOC) {
        finalOutput.metrics = processSegment(xmlParser.IDOC, xmlParser.name(), segmentDefinitions)
    } else {
        finalOutput.metrics = []
    }
    
    finalOutput.seq = 2
    
    // Convert to JSON and return the message
    def jsonOutput = new JsonBuilder(finalOutput).toPrettyString()
    message.setBody(jsonOutput)
    return message
}
