package xic;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.HelpFormatter;


import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

/**
 * @author yuchien
 *
 */
public class Xic {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption( "h", "help", false, "Print a synopsis of options" );
		options.addOption( "l", "lex", false, "Generate output from lexical analysis" );
		options.addOption( "p", "parse", false, "Generate output from syntactic analysis" );
		options.addOption( "v", "verbose", false, "Be extra verbose" );

		CommandLine commandLine = parser.parse(options, args);
		final String[] remainingArguments = commandLine.getArgs();
		
		if(commandLine.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp( "xic [options] <source files>", options );
		} else {
			String pathXi = remainingArguments[0];
			String pathLexed = pathXi.substring(0,pathXi.length()-2)+"lexed";
			String pathParsed = pathXi.substring(0,pathXi.length()-2)+"parsed";
			
			System.out.print(String.format("Compiling: %s\n", pathXi));				

			if(commandLine.hasOption("l")) {
				Reader fr = new FileReader(pathXi);
				Lexer lexer = new Lexer(fr);
				FileWriter fw = new FileWriter(pathLexed);
	            for (ComplexSymbol symbol = (ComplexSymbol) lexer.next_token(); symbol.sym != 0; symbol = (ComplexSymbol) lexer.next_token()) {
	            	String line = String.format("%d:%d %s", symbol.getLeft().getLine(), symbol.getLeft().getColumn(), symbol.getName());
	            	if(symbol.sym == sym.error){
	            		line += ":" + symbol.value() + "\n";
	            		if(commandLine.hasOption("v")){ System.out.print(line); }
	            		fw.write(line);
	            		break;
	            	}
	            	if(symbol.value() != null){
	            		line += " " + symbol.value() + "\n";
	            	} else {
	            		line += "\n";
	            	}
	            	if(commandLine.hasOption("v")){ System.out.print(line); } 
	            	fw.write(line);
	            }
	            fw.close();
			} 
			
			if(commandLine.hasOption("p")) {
				Reader fr = new FileReader(pathXi);
				Lexer lexer = new Lexer(fr);
				FileWriter fw = new FileWriter(pathParsed);
				parser p = new parser(lexer);
				try{
					String program = p.parse().value.toString();
					if(commandLine.hasOption("v")){ System.out.print(program); }
					fw.write(program);
				} catch(Error e) {
					if(commandLine.hasOption("v")){ System.out.print(e.getMessage()); }
					fw.write(e.getMessage());
				}
				
	            fw.close();
			}	
		}		
	}
}
