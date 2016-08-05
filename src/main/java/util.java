import com.google.gson.Gson;
import edu.stanford.nlp.util.ArrayUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * Created by garrethdottin on 7/19/16.
 */
public class Util {
    public static String readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int)file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while(scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    public  static ArrayList<Email> cleanInput(ArrayList<Email> theEmailList) {
        ArrayList<Email> cleanedDataSet = new ArrayList<Email>();

        for (int i = 0; i < theEmailList.size(); i++) {
            if (theEmailList.get(i).getText() != null ) {
                cleanedDataSet.add(theEmailList.get(i));
            }
        }

        return cleanedDataSet;
    }

    public static void outputToJSON(HashMap<String, ArrayList<Email>> wordFrequency) {
        Gson gson = new Gson();
        String json = gson.toJson(wordFrequency);
        try {
            FileWriter writer = new FileWriter("src/wordFrequency.json");
            writer.write(json);
            writer.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Email> sortByDate(ArrayList<Email> emailList) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");

        try {
            for (Email email : emailList) {
                Date parsedDate = dateFormat.parse(email.getDate());
                email.setTimestamp(parsedDate);
            }
            emailList.sort((o1, o2) -> o2.getTimestamp().compareTo(o1.getTimestamp()));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return emailList;
    }
}
