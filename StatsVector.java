import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;


/**
 * @author dafne
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
 *
 */
public class StatsVector {

	private String filePath;
	private Vector staticFiles;
	
	public Vector getStaticFiles() {
		return staticFiles;
	}

	public StatsVector(String filePath)  {
		this.filePath = filePath;
		readInStaticFiles ( );
	}
	
	public StatsVector() {
        this.filePath = new File("StemmerFiles").getAbsolutePath();
        readInStaticFiles ( );
	}
	
    protected void readInStaticFiles ( ) 
    {
        // create the vector composed of vectors containing the static files
    	this.staticFiles = new Vector();
        String[] staticFileNames = { "definite_article.txt",
        		"diacritics.txt",
        		"duplicate.txt",
        		"first_waw.txt",
        		"first_yah.txt",
        		"last_alif.txt",
        		"last_hamza.txt",
        		"last_maksoura.txt",
        		"last_yah.txt",
        		"mid_waw.txt",
        		"mid_yah.txt",
        		"prefixes.txt",
        		"punctuation.txt",
        		"quad_roots.txt",
        		"stopwords.txt",
        		"strange.txt",
        		"suffixes.txt",
        		"tri_patt.txt",
        		"tri_roots.txt" };
        for(String fileName : staticFileNames){
        	addVectorFromFile (new File(this.filePath, fileName).getAbsolutePath() , this.staticFiles);
        }
    }
	
	  protected boolean addVectorFromFile ( String fileName, Vector staticFiles ) 
	    {
	        boolean returnValue;
	        try
	        {
	            // the vector we are going to fill
	            Vector vectorFromFile = new Vector ( );

	            // create a buffered reader
	            File file = new File ( fileName );
	            FileInputStream fileInputStream = new FileInputStream ( file );
	            InputStreamReader inputStreamReader = new InputStreamReader ( fileInputStream, "UTF-16" );

	            //If the bufferedReader is not big enough for a file, I should change the size of it here
	            BufferedReader bufferedReader = new BufferedReader ( inputStreamReader, 20000 );

	            // read in the text a line at a time
	            String part;
	            StringBuffer word = new StringBuffer ( );
	            while ( ( part = bufferedReader.readLine ( ) ) != null )
	            {
	                // add spaces at the end of the line
	                part = part + "  ";

	                // for each line
	                for ( int index = 0; index < part.length ( ) - 1; index ++ )
	                {
	                    // if the character is not a space, append it to a word
	                    if ( ! ( Character.isWhitespace ( part.charAt ( index ) ) ) )
	                    {
	                        word.append ( part.charAt ( index ) );
	                    }
	                    // otherwise, if the word contains some characters, add it
	                    // to the vector
	                    else
	                    {
	                        if ( word.length ( ) != 0 )
	                        {
	                            vectorFromFile.addElement ( word.toString ( ) );
	                            word.setLength ( 0 );
	                        }
	                    }
	                }
	            }

	            // trim the vector
	            vectorFromFile.trimToSize ( );

	            // destroy the buffered reader
	            bufferedReader.close ( );
	   	        fileInputStream.close ( );

	            // add the vector to the vector composed of vectors containing the
	            // static files
	            this.staticFiles.addElement ( vectorFromFile );
	            returnValue = true;
	        }
	        catch ( IOException exception )
	        {
	            System.out.println("Could not open '" + fileName + "'.");
	            returnValue = false;
	        }
	        return returnValue;
	    }

}
