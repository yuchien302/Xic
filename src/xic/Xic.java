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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import xic.Lexer.Token;
import xic.Lexer.TokenType;

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
			FileWriter fw = new FileWriter(pathLexed);
			
			Lexer lexer = new Lexer(fr);
			
			Token tok = lexer.yylex();
			while (tok != null){
				int numLine = tok.getLine() + 1 ;
				int numCol = tok.getCol() + 1; 
				String lineVal = new String ();
				if (tok.getType() == TokenType.ERROR){
					lineVal = tok.getValue();
					String s = String.format("%d:%d %s\n", numLine, numCol, lineVal);
					fw.write(s);
					break;
				}
				else if (tok.getType() == TokenType.SYMBOL ||tok.getType() == TokenType.KEYWORD){
					lineVal = lexer.yytext();
				}
				else if (tok.getType() == TokenType.INTEGER) {
					lineVal = tok.getType().toString().toLowerCase()+" "+ lexer.yytext();
				}
				else {
					lineVal = tok.getType().toString().toLowerCase()+" "+ tok.getValue();
				}
				String s = String.format("%d:%d %s\n", numLine, numCol, lineVal);
				fw.write(s); 
//				System.out.print(s);
				
				tok = lexer.yylex();
			}
			fw.close();
		} catch (ParseException e) {
			System.out.println( "Unexpected exception:" + e.getMessage() );
		}
	

		
	}
}
