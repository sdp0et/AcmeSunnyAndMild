import net.urover.acme.logging.CamelCaseClassConverter
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.*

//def alignedPattern = "%-94.-94(%d{yyyy-MM-dd-HH:mm:ss.SSS} [%-20.20t] %-5p %-25.-25cc{25,10}.%-15.-15M): %m%n"
def alignedPattern = "%-74.-74(%d{yyyy-MM-dd-HH:mm:ss.SSS} [%-10.10t] %-5p %-20.-20cc{20,5}.%-10.-10M): %m%n"

conversionRule("cc", CamelCaseClassConverter)
appender("STDOUT", ConsoleAppender) {
	encoder(PatternLayoutEncoder) {
//		pattern = "%d{yyyy-M-dd-HH:mm:ss.SSS} [%t] %-5p %c.%M: \t%m%n"
		pattern = alignedPattern
	}
}
root(DEBUG, ["STDOUT"])