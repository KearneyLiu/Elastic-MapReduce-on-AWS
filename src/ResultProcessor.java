import java.io.*;
import java.util.*;

/**
 * This class is to process the output of the EMR.
 *
 * I use a command "cat part* > OutputResult" to integrate all the output files in one file named OutputResult.
 * Then I process that file to answer the questions.
 *
 * Created by liukaiyu on 9/13/14.
 */
public class ResultProcessor {

    public static void main(String[] args) throws Exception {
        ResultProcessor rp = new ResultProcessor();
        FileInputStream is = new FileInputStream("OutputResult");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        int n = 0;
        String input;
        String[] str;
        int monthCount;
        String article;
        int maxCount = 0;
        String maxArticle = null;
        String maxSignalDay = null;

        HashMap<String,Integer> comparePlayer = new HashMap<String, Integer>();
        ArrayList<String> compareOnlyDay = new ArrayList<String>();
        ArrayList<String> goVsAmazon = new ArrayList<String>();

        while((input = br.readLine())!= null){
            str = input.split("\t");
            monthCount = Integer.parseInt(str[0]);
            article = str[1];

            if(monthCount > maxCount){
                maxCount = monthCount;
                maxArticle = article;
            }

            if(article.equals("Cristiano_Ronaldo"))
                comparePlayer.put("Cristiano_Ronaldo", monthCount);
            if(article.equals("Neymar"))
                comparePlayer.put("Neymar", monthCount);
            if(article.equals("Arjen_Robben"))
                comparePlayer.put("Arjen_Robben", monthCount);
            if(article.equals("Tim_Howard"))
                comparePlayer.put("Tim_Howard", monthCount);
            if(article.equals("Miroslav_Klose"))
                comparePlayer.put("Miroslav_Klose", monthCount);


            if(article.equals("Ariana_Grande") || article.equals("Scarlett_Johansson") || article.equals("Dwayne_Johnson")
                    || article.equals("Iggy_Azalea") || article.equals("Kurt_Russell") )
                compareOnlyDay.add(input);


            if(article.equals("Google") || article.equals("Amazon.com"))
                goVsAmazon.add(input);

            if(article.equals("Dawn_of_the_Planet_of_the_Apes"))
                maxSignalDay = input;


            n++;
        }
        /* Question 7*/
        System.out.println("********Question 7********");
        System.out.println("the number of line is " + Integer.toString(n));
        /* Question 8,9 */
        System.out.println("********Question 8,9********");
        System.out.println("the max is " + maxArticle + ":" + Integer.toString(maxCount));


        /* Question 10*/
        /* Display the five soccer player's page view*/
        System.out.println("********Question 10********");
        Iterator iter = comparePlayer.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            System.out.println(entry.getKey().toString() + " = " + entry.getValue().toString());
        }
        /* Question 11*/
        System.out.println("********Question 11********");
        rp.compareSingleDay(compareOnlyDay);
        /* Question 12*/
        System.out.println("********Question 12********");
        rp.GoAma(goVsAmazon);
        /* Question 13*/
        System.out.println("********Question 13********");
        rp.maxSignalDay(maxSignalDay);


    }
    /* Display the five people's max page view on a single day*/
    private void compareSingleDay(ArrayList<String> list){
        String line;

        for(int i = 0; i < list.size(); i++) {
            line = list.get(i);
            maxSignalDay(line);
        }
    }
    /* Compare the "Google" with "Amazon.com"*/
    private void GoAma(ArrayList<String> list){
        String line1;
        String line2;
        String[] date1;
        String[] date2;
        String[] number1;
        String[] number2;

        line1 = list.get(0);
        line2 = list.get(1);

        date1 = line1.split("\t");
        date2 = line2.split("\t");

        int count = 0;

        for(int i = 2; i < 33; i++){
            number1 = date1[i].split(":");
            number2 = date2[i].split(":");
            if(Integer.parseInt(number1[1]) > Integer.parseInt(number2[1])){
                count ++;
                System.out.println(date1[1]+ " > "+date2[1] + " on "+number1[0]);
            }

        }
        System.out.println("the number of "+ date1[1]+ " > "+date2[1] + " is " + Integer.toString(count));
    }


    /* Get the max page view on a single day*/
    private void maxSignalDay(String str){
        String[] date = str.split("\t");
        String[] number;
        String day;
        int count;
        int max = 0;
        String maxDay = null;
        /* Parse the data of line */
        for(int j = 2; j < 33; j++){
            number = date[j].split(":");
            day = number[0];
            count = Integer.parseInt(number[1]);
            if(count > max){
                maxDay = day;
                max = count;
            }
        }
        System.out.println("The single max page view of "+ date[1] + " is "+ Integer.toString(max) + " on "
                + maxDay);
    }
}
