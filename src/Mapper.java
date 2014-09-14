import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * This is the project 1.2 of 15-619.
 * This project is working on EMR.
 * This class is used as Mapper in hadoop streaming.
 *
 * @author Kaiyu Liu(kaiyul)
 */

public class Mapper {
    /**
     * This method is used to filter the wikipedia articles, and sort the articles by popularity.
     * First, I read the data from the given dataset, and design some condition to meet the demand.
     * Second, I put the article name and page views into the the data structure of HashMap.
     * Third, the HashMap is sorted by the value(page views) by the method of comparator.
     * Finally, the sorted data is written into the result.txt.
     */
    String[] filterStartString = {"Media:", "Special:", "Talk:", "User:", "User_talk:", "Project:",
            "Project_talk:", "File:", "File_talk:", "MediaWiki:", "MediaWiki_talk:", "Template:",
            "Template_talk:", "Help:", "Help_talk:", "Category:", "Category_talk:", "Portal:",
            "Wikipedia:", "Wikipedia_talk:"};
    String[] filterEndString = {".jpg", ".gif", ".png", ".JPG", ".GIF", ".PNG", ".txt", ".ico"};
    String[] filterEqualString = {"404_error/", "Main_Page", "Hypertext_Transfer_Protocol",
            "Favicon.ico", "Search"};

    public static void main(String[] args) throws Exception {

        Mapper mapper = new Mapper();

        String str;  /*The String in every line*/
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>(); /*Store the data*/
        String part1, part2, part3, date;

        /* Read the data from the dataset */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while ((str = br.readLine()) != null) {

            /* Every line is split into three parts, the first part is the project name,
            * the second part is the page title, and the third part is the number of accesses.*/
            String[] parts = str.split(" ");
            part1 = parts[0];
            part2 = parts[1];
            part3 = parts[2];
            date = System.getenv("map_input_file").substring(46,48);
           /* The data is filtered by the given condition*/
            if (!part1.matches("en")) {
                continue;
            }

            if (!mapper.filterStart(part2)) {
                continue;
            }

            if (!mapper.filterLowerCase(part2)) {
                continue;
            }

            if (!mapper.filterEnd(part2)) {
                continue;
            }

            if (!mapper.filterEqual(part2)) {
                continue;
            }
            /* Input the Filtered Data */
            System.out.println(part2 + "\t" + date + "\t" + part3);
        }

    }

    private boolean filterStart(String str) {
        for (String s : filterStartString) {
            if (str.startsWith(s)) {
                return false;
            }
        }
        return true;
    }

    private boolean filterLowerCase(String str) {
        if ((str.charAt(0) >= 'a') && (str.charAt(0) <= 'z')) {
            return false;
        }
        return true;
    }

    private boolean filterEnd(String str) {
        for (String s : filterEndString) {
            if (str.endsWith(s)) {
                return false;
            }
        }
        return true;
    }

    private boolean filterEqual(String str) {
        for (String s : filterEqualString) {
            if (str.equals(s)) {
                return false;
            }
        }
        return true;
    }

}

