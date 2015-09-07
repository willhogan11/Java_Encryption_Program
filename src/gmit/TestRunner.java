package gmit;

import java.io.*;
import java.util.*;


public class TestRunner 
{
	// HashMap Declaration which contains Key Value pairs as Defined in the Method 
    private static Map<String, String> polybius = new HashMap<String, String>();
	public static ArrayList<Character> chars = new ArrayList<Character>();

    // User Key Entered
    private static String key; 

    // ArrayList of ArrayList (Matrix used for Columnar Transposition)
    public static List<List<Character>> lists = new ArrayList<List<Character>>();
    // public static List<List<Character>> decryptLists = new ArrayList<List<Character>>();
    private static long StartTime;

    
    public static void main(String[] args) throws Exception
    {
        initialisePolybius();  
        readFileKeyEncrypt(); 
        columnarTrans();
        
    } // End of main method
   

    // Prompts user for keyword and File to Encrypt and completes the first part of the Encryption
    // process by Reading each line in and encrypting using the polybius square. 
    // Running Time, O(n) Linear as the part has to be read in
    public static void readFileKeyEncrypt() throws Exception
    {
    	Scanner in = new Scanner(System.in);
		System.out.println("Enter your Key: ");
		key = in.nextLine();
        
		try
		{
	        Scanner file = new Scanner(System.in);
	        System.out.println("\nEnter the File to Encrypt.\nOptions:\n" +
	        		"WarAndPeaceLeoTolstoy.txt\n" +
	        		"DeBelloGallico.txt\n" + 
	        		"PoblachtNaHEireann.txt\n");
	        String chooseFile = file.nextLine(); 
		
	        // Start Timer
	        StartTime = System.currentTimeMillis()/1000;
	        
	        // Create Buffered reader object 'br' with contents of file
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(chooseFile)));
	
	        // Sets the line = null
	        String line = null;
	
	        // While the file isn't empty, Encrypt each word
	        while( (line = br.readLine()) != null )
	        {
	        	encryptWord(line);
	        }
	        br.close(); 
	        file.close();
	        in.close();
		}
		// Throws error if issue with File
		catch(IOException e)
		{
			System.out.println("Please Select a Valid File");
			return;
			
		} // End try / catch
        
    } // End of readFileThroughPolybius 

    
    // Encrypts Each word when a space is reached
    public static void encryptWord(String line) throws IOException
    {
        // Splits into Words, wherever there are spaces.
        String [] words = line.split(" ");  

        for (int i = 0; i < words.length; i++)
        {
            String word = words [i];
            for (int j = 0; j < word.length(); j++)
            {
                char letter = word.charAt(j);
                encryptLetter(letter, j); //  Encrypt Letter Method, see below Declaration / Definition
            }
        }
        
    } // End of encryptWord method
    
    
    // Encrypts each letter
    public static void encryptLetter(char letter, int j) throws IOException
    {    
        String current = polybius.get(Character.toString(letter).toUpperCase().trim());
        chars.add(current.charAt(0)); 
        chars.add(current.charAt(1));
        
    } // End of encryptLetter method
    
    

/*    Performs the Columnar Transposition / Column Shifting, gets each Encrypted Character from the
	Polybius square and using the keyword as a foundation, populates the matrix. 
	The first index in each ArrayList will be a letter in the KeyWord entered by the user. 
	Each Encrypted character is added to the new Arraylist of Arraylists and when the keyword length has been reached
	The Counter is reset and the Arraylist is filled from the first index again and so on until all text has been Encrypted*/

    // Constant time operation for the For loops as accessing and getting values at known indicies
    public static void columnarTrans() throws Exception
    {
    	PrintWriter out = new PrintWriter("encryptedFile.txt"); 
    	
        // Dynamically create columns for matrix
        for (int i = 0; i < key.length(); i++) 
        {
            List<Character> list = new ArrayList<Character>();
            lists.add(list);
        }
        int count = 0;

        // write encrypted text to columns
        while (count < (chars.size() -1))
        {
            for (int j = 0; j < key.length() && count < (chars.size() -1); j++) 
            {
                lists.get(j).add(chars.get(count++));
            }
        }
        ArrayList<Character> keyArray = new ArrayList<Character>();
        ArrayList<Character> sortedKeyArray = new ArrayList<Character>();
                
        for (char c : key.toLowerCase().toCharArray())
        {
            keyArray.add(c);
        }
        
        sortedKeyArray = (ArrayList<Character>) keyArray.clone();
        Collections.sort(sortedKeyArray);
        
        for (int j = 0; j < keyArray.size(); j++) 
        {
            for (int i = 0; i < keyArray.size(); i++) 
            {
                if (sortedKeyArray.get(j) == keyArray.get(i)) 
                {    
                    // Sets j to null which fixes errors with duplicate characters in a string
                    keyArray.set(i, null);
                    
                    int z = lists.get(i).size();
                    
                    for (int p = 0; p < z; p++) 
                    {
                        out.print(lists.get(i).get(p));
                        
                    } // End for
                    out.print("\n");
                    
                    // To exit inner for loop
                    i = 20;
                    
                } // End if (sortedKeyArray.get(j) == keyArray.get(i)) 
                
            } // End for (int i = 0; i < keyArray.size(); i++) 
            
        } // End for (int j = 0; j < keyArray.size(); j++)
        out.close();
        System.out.println("\nEncryption Completed");
        
        // Stop Timer and output to screen
        System.out.println("Running Time in secs: " + ((System.currentTimeMillis()/1000) - StartTime));
        
    } // End of columnarTransposition
    
    

    // Definition of the Polybius Square, with it's associated Key|value pairing system.
 	public static void initialisePolybius() throws Exception
 	{	
         // Encryption key/value pairs
         polybius.put("AA", "P"); // AA = Key and so on....
         polybius.put("AD", "H");
         polybius.put("AF", "0");
         polybius.put("AG", "Q"); 
         polybius.put("AV", "6"); 
         polybius.put("AX", "G"); 
         polybius.put("DA", "4"); 
         polybius.put("DD", "M"); 
         polybius.put("DF", "E"); 
         polybius.put("DG", "A"); 
         polybius.put("DV", "Y"); 
         polybius.put("DX", "1"); 
         polybius.put("FA", "L"); 
         polybius.put("FD", "2");
         polybius.put("FF", "N"); 
         polybius.put("FG", "O"); 
         polybius.put("FV", "D"); 
         polybius.put("FX", "F"); 
         polybius.put("GA", "X");
         polybius.put("GD", "K");
         polybius.put("GF", "R"); 
         polybius.put("GG", "3"); 
         polybius.put("GV", "V"); 
         polybius.put("GX", "C");
         polybius.put("VA", "J");
         polybius.put("VD", "9");
         polybius.put("VF", "U");
         polybius.put("VG", "T");
         polybius.put("VV", "8");
         polybius.put("VX", "I");
         polybius.put("XA", "S");
         polybius.put("XD", "5");
         polybius.put("XF", "Z");
         polybius.put("XG", "W");
         polybius.put("XV", "B");
         polybius.put("XX", "7");
         polybius.put("YA", "-");
         polybius.put("YD", ".");
         polybius.put("YF", "(");
         polybius.put("YG", ")");
         polybius.put("YX", ",");
         polybius.put("YV", "?");
         polybius.put("YY", "!");
         polybius.put("AY", ";");
         polybius.put("DY", ":");
         polybius.put("FY", "_");
         polybius.put("GY", "/");
         polybius.put("XY", "=");
         polybius.put("VY", "*");
         polybius.put("AZ", "\"");   
         polybius.put("DZ", " ");   
         polybius.put("FZ", "&");   
         polybius.put("GZ", "'");  
         polybius.put("XZ", "|");  
         polybius.put("VZ", "~");    
         polybius.put("YZ", "#");    
         polybius.put("ZA", "£");
         polybius.put("ZD", "€");
         polybius.put("ZF", "$");
         polybius.put("ZG", "<");
         polybius.put("ZX", ">");
         polybius.put("ZV", "@");
         polybius.put("ZY", "`");
         polybius.put("ZZ", "^");
         

         // Decryption key/value pairs.
         polybius.put("P", "AA"); 
         polybius.put("H", "AD");  
         polybius.put("0", "AF");  
         polybius.put("Q", "AG"); 
         polybius.put("G", "AX");
         polybius.put("6", "AV");
         polybius.put("4", "DA");
         polybius.put("M", "DD");
         polybius.put("E", "DF");
         polybius.put("A", "DG");
         polybius.put("1", "DX");
         polybius.put("Y", "DV");
         polybius.put("L", "FA");
         polybius.put("2", "FD");
         polybius.put("N", "FF");
         polybius.put("O", "FG");
         polybius.put("F", "FX");
         polybius.put("D", "FV");
         polybius.put("X", "GA");
         polybius.put("K", "GD");
         polybius.put("R", "GF");
         polybius.put("3", "GG");
         polybius.put("C", "GX");
         polybius.put("V", "GV");
         polybius.put("S", "XA");
         polybius.put("5", "XD");
         polybius.put("Z", "XF");
         polybius.put("W", "XG");
         polybius.put("7", "XX");
         polybius.put("B", "XV");
         polybius.put("J", "VA");
         polybius.put("9", "VD");
         polybius.put("U", "VF");
         polybius.put("T", "VG");
         polybius.put("I", "VX");
         polybius.put("8", "VV");
         polybius.put("-", "YA");
         polybius.put(".", "YD");
         polybius.put("(", "YF");
         polybius.put(")", "YG");
         polybius.put(",", "YX");
         polybius.put("?", "YV");
         polybius.put("!", "YY");
         polybius.put(";", "AY");
         polybius.put(":", "DY");
         polybius.put("_", "FY");
         polybius.put("=", "XY");
         polybius.put("*", "VY");   
         polybius.put("\"", "AZ"); 
         polybius.put(" ", "DZ");   
         polybius.put("&", "FZ");    
         polybius.put("'", "GZ");  
         polybius.put("|", "XZ");   
         polybius.put("~", "VZ");    
         polybius.put("/", "GY");
         polybius.put("#", "YZ");
         polybius.put("£", "ZA");
         polybius.put("$", "ZF");
         polybius.put("<", "ZG");
         polybius.put(">", "ZX");
         polybius.put("@", "ZF");
         polybius.put("~", "ZY");
         polybius.put("^", "ZZ");
         
     } //End of initialisePolybius method
    
} // End of RunnerClass