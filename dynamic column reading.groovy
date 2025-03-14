import com.sap.gateway.ip.core.customdev.util.Message
import groovy.xml.*
import groovy.json.JsonBuilder

def processSegment(xmlNode, parentPath) {
    def metrics = []
    
    // Process all child elements
    xmlNode.children().each { child ->
        def nodeName = child.name()
        if (nodeName) {  // Skip text nodes
            // Create path for current node
            def currentPath = "${parentPath}/${nodeName}"
            
            // Create metric for current node
            def metric = [:]
            metric.name = currentPath
            metric.datatype = 16
            metric.dataset_value = [:]
            
            // Get all possible column names from child elements
            def columns = child.children().collect { it.name() }.findAll { it }
            
            if (columns) {
                metric.dataset_value.num_of_columns = columns.size()
                metric.dataset_value.columns = columns
                metric.dataset_value.types = (1..columns.size()).collect { 12 }
                
                // Create row with values
                def elements = columns.collect { column ->
                    [string_value: child."${column}".text()]
                }
                metric.dataset_value.rows = [[elements: elements]]
                
                metrics << metric
            }
            
            // Recursively process child segments
            if (child.children().find { it.children().size() > 0 }) {
                metrics.addAll(processSegment(child, currentPath))
            }
        }
    }
    
    return metrics
}

def Message processData(Message message) {
    def ReadPayload = message.getBody(String)
    def xmlParser = new XmlSlurper().parseText(ReadPayload)
    
    def finalOutput = [:]
    finalOutput.timestamp = System.currentTimeMillis() / 1000 as long
    
    if (xmlParser.IDOC) {
        // Start processing from IDOC node with the parent name
        finalOutput.metrics = processSegment(xmlParser.IDOC, xmlParser.name())
    } else {
        finalOutput.metrics = []
    }
    
    finalOutput.seq = 2
    
    def jsonOutput = new JsonBuilder(finalOutput).toPrettyString()
    message.setBody(jsonOutput)
    return message
}