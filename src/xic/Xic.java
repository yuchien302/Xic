package xic;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.apache.commons.cli.HelpFormatter;


import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;



/**
 * @author yuchien
 *
 */
public class Xic {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption( "h", "help", false, "Print a synopsis of options" );
		options.addOption( "l", "lex", false, "Generate output from lexical analysis" );
		options.addOption( "p", "parse", false, "Generate output from syntactic analysis" );

		CommandLine commandLine;
		try {
			commandLine = parser.parse(options, args);
			final String[] remainingArguments = commandLine.getArgs();
			HelpFormatter formatter = new HelpFormatter();
//			formatter.printHelp( "xic", options );
			
			String pathXi = remainingArguments[0];
			String pathLexed = pathXi.substring(0,pathXi.length()-2)+"lexed";
			
			Reader fr = new FileReader(pathXi);
//			FileWriter fw = new FileWriter(pathLexed);
			
			Lexer lexer = new Lexer(fr);
			
//			parser p = new parser(lexer, sf);
			System.out.print(String.format("Compiling: %s\n", pathXi));
			try {

				parser p = new parser(lexer);
	            System.out.println(p.parse().value);								

//	            for (Symbol sym = lexer.next_token(); sym.sym != 0; sym = lexer.next_token()) {
//	                System.out.println(String.format("(%d, %d) | Token %s: %s ", 
//	                				sym.left, sym.right, sym, sym.value));
//	            }

			} catch (Exception e) {
				e.printStackTrace();
			}
						
		} catch (ParseException e) {
			System.out.println( "Unexpected exception:" + e.getMessage() );
		}
	

		
	}
}
