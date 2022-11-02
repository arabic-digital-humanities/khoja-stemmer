import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * @author Dafne van Kuppevelt
 *
 * This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

    This file was modified by Tijmen Baarda (Utrecht Digital Humanities Lab)
    
 * Usage:
 * 
 * Usage: java CLIStemmer <inputfile> <outputfile> [<encoding=UTF8>] [<staticfilesdir=StemmerFiles>]
 *
 */
public class CLIStemmer {


	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java CLIStemmer <inputfile> <outputfile> [<encoding=UTF8>] [<staticfilesdir=StemmerFiles>]");
			return;
		}
		File fileIn = new File(args[0]);
		File fileOut = new File(args[1]);
		String encoding = args.length > 2 ? args[2] : "UTF-8";
		String staticPath = args.length > 3 ? args[3] : null;
		StatsVector statsVector;
		if (staticPath != null) {
			statsVector = new StatsVector(new File(staticPath).getAbsolutePath());
		} else {
			statsVector = new StatsVector();
		}
		Stem stemmedText = new Stem ( fileIn, statsVector.getStaticFiles(), encoding);
		System.out.println(stemmedText.displayText());
		String[][] stemmedDocument = stemmedText.returnStatistics();
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileOut);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));
			for(String[] sList : stemmedDocument){
				if(sList[0]!=null){
					bw.write(String.join("\t", sList));
					bw.newLine();
				}
			}
			bw.close();
		} catch (IOException e) {
			System.out.println("Could not write to file");
			e.printStackTrace();
		}
	}

}
