package net.urover.acme.logging

import ch.qos.logback.classic.pattern.Abbreviator
import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator
import ch.qos.logback.classic.pattern.LoggerConverter
import ch.qos.logback.classic.pattern.TargetLengthBasedClassNameAbbreviator
import ch.qos.logback.classic.spi.ILoggingEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class CamelCaseClassConverter extends LoggerConverter {
	Abbreviator abbreviator;

	private static Logger log = LoggerFactory.getLogger(CamelCaseClassConverter.class);

	public String convert(ILoggingEvent event){
		String fqn = getFullyQualifiedName(event);
		if (this.abbreviator == null) {
			return fqn;
		}
		return this.abbreviator.abbreviate(fqn);
	}

	@Override
	public void start() {
		String optStr = getFirstOption();
		def wordLength = null
		if(getOptionList().size()>1){
			wordLength = Integer.parseInt(getOptionList()[1])
		}
		if(wordLength && wordLength < 1){
			wordLength = null
		}

		if (optStr == null) return;
		try {
			int targetLen = Integer.parseInt(optStr);
			if (targetLen == 0)
				if(wordLength){
					abbreviator  = new CamelCaseStringAbbreviator(targetLen,wordLength )
				}else{
					this.abbreviator = new ClassNameOnlyAbbreviator();

				}
			else if (targetLen > 0)
				if(wordLength){
					abbreviator  = new CamelCaseStringAbbreviator(targetLen,wordLength )
				}else{
					abbreviator  = new CamelCaseStringAbbreviator(targetLen)
				}
		}catch (NumberFormatException nfe) {
		}
	}
}


class CamelCaseStringAbbreviator extends TargetLengthBasedClassNameAbbreviator{

	final int totalTargetLength;
	int wordTargetLength = 4;

	ClassNameOnlyAbbreviator delegate

	public CamelCaseStringAbbreviator(int totalTargetLength) {
		super(totalTargetLength)
		this.totalTargetLength = totalTargetLength;
		if(totalTargetLength == 0){
			delegate = new ClassNameOnlyAbbreviator()
		}
	}

	public CamelCaseStringAbbreviator(int totalTargetLength, int wordTargetLength) {
		super(totalTargetLength)
		this.totalTargetLength = totalTargetLength;

		this.wordTargetLength = wordTargetLength
		if(totalTargetLength == 0){
			delegate = new ClassNameOnlyAbbreviator()
		}
	}

	def nameChange = {fqClassName ->
//        def cn = fqClassName.substring(fqClassName.lastIndexOf(".")+1).replaceAll(/\[[]()\/\\\]/,"")
		fqClassName = fqClassName.replaceAll(/[\[({$]/,"")
		def cn = fqClassName.substring(fqClassName.lastIndexOf(".")+1)
		def mcn = cn.replaceAll(/\B[A-Z]/) { '_' + it }
		def parts = mcn.split('_')
		def nb = new StringBuilder()

		int packageLevels = fqClassName.count(".")
		int packageStringLength = packageLevels+packageLevels;
		int lengthLeftForClassName = totalTargetLength - packageStringLength;
		int calcWorgLength = (int) Math.max(wordTargetLength, (double)lengthLeftForClassName / parts.size())
//            calcWorgLength = (int) Math.max(wordTargetLength, (double)lengthLeftForClassName / parts.size())
//        println "PSL: $packageStringLength\tLLFC: $lengthLeftForClassName\twords: ${parts.size()}\tper word: $calcWorgLength"

		parts.each{
			try{
				nb.append(it.substring(0, Math.min(calcWorgLength, it.length())))
			}catch(Exception e){
				println "   "+e.getMessage()
			}
		}
		fqClassName.replace(cn, nb.toString())
	}.memoizeAtMost(50)


	@Override
	public String abbreviate(String fqClassName) {
		String shorter = nameChange(fqClassName)
		if(delegate){
			return delegate.abbreviate(shorter)
		}else{
			return super.abbreviate(shorter)
		}
	}
}
