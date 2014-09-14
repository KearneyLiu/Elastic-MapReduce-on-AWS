import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/** This class is used as Reducer in hadoop streaming.
 *
 * Created by liukaiyu on 9/10/14.
 */
public class Reducer {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = null;
        String[] src;
        String article = null;
        int date = 0;
        String currentArticle = null;
        int count = 0;
        int dateCount = 0;
        int monthSum = 0;

        HashMap<Integer, Integer> dateSum = new HashMap<Integer, Integer>();

        while((input = br.readLine()) != null){
            src= input.split("\t");
            article = src[0];
            date = Integer.parseInt(src[1]);
            count = Integer.parseInt(src[2]);

            /* if the article is the same as the previous one*/
            if(currentArticle != null && currentArticle.equals(article)){
                if(dateSum.containsKey(date)){
                    dateCount = dateSum.get(date);
                    dateSum.put(date, dateCount + count);
                }
                else{
                    dateSum.put(date, count);
                }
                monthSum += count;

            }
            else{
             /* a new article */
                if(currentArticle != null) {
                    /*Output the sum of article*/
                    if (monthSum > 100000) {
                        System.out.print(Integer.toString(monthSum) + "\t" + currentArticle);
                        for (int j = 1; j < 32; j++) {
                            if (dateSum.containsKey(j)) {
                                System.out.print("\t7/" + Integer.toString(j) + ":" + dateSum.get(j));
                            } else {
                                System.out.print("\t7/" + Integer.toString(j) + ":0");
                            }
                        }
                        System.out.print("\n");
                    }
                    /* reset the count number*/
                    monthSum = 0;
                    dateSum.clear();
                }
                /* makeup the first one */
                if (dateSum.containsKey(date)) {
                    dateCount = dateSum.get(date);
                    dateSum.put(date, dateCount + count);
                } else {
                    dateSum.put(date, count);
                }
                monthSum += count;

                currentArticle = article;  //a new article
            }

        }

        /* add the last omitted one */
        if(currentArticle != null && currentArticle.equals(article)) {
            /* makeup the last omitted one */
            if (dateSum.containsKey(date)) {
                dateCount = dateSum.get(date);
                dateSum.put(date, dateCount);
            } else {
                dateSum.put(date, count);
            }


            if (monthSum > 100000) {
                System.out.print(Integer.toString(monthSum) + "\t" + currentArticle);
                for (int j = 1; j < 32; j++) {
                    if (dateSum.containsKey(j)) {
                        System.out.print("\t7/" + Integer.toString(j) + ":" + dateSum.get(j));
                    } else {
                        System.out.print("\t7/" + Integer.toString(j) + ":0");
                    }
                }
                System.out.print("\n");
            }
            /* clear the data structures*/
            dateSum.clear();
        }

    }

}
