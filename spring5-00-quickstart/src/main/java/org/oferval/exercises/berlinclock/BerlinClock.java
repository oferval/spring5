package org.oferval.exercises.berlinclock;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Based on this kata:
 * @see <a href="https://technologyconversations.com/2014/02/25/java-8-tutorial-through-katas-berlin-clock-easy">
 *      https://technologyconversations.com/2014/02/25/java-8-tutorial-through-katas-berlin-clock-easy
 *      </a>
 */
public class BerlinClock {

  String[] berlinClock = new String[] {"Y", "OOOO", "OOOO", "OOOOOOOOOOO", "OOOO"};


  public String getSeconds(int i) {

    convertToBerlinTime("00:00:" + i);
    return berlinClock[0];
  }


  public String getTopHours(int i) {

    convertToBerlinTime(i + ":00:00");
    return berlinClock[1];
  }


  public String getBottomHours(int i) {

    convertToBerlinTime(i + ":00:00");
    return berlinClock[2];
  }


  public String getTopMinutes(int i) {

    convertToBerlinTime("00:" + i + ":00");
    return berlinClock[3];
  }


  public String getBottomMinutes(int i) {

    convertToBerlinTime("00:" + i + ":00");
    return berlinClock[4];

  }


  public String[] convertToBerlinTime(String time) {

    StringTokenizer stringTokenizer = new StringTokenizer(time,":");
    int hours =  Integer.parseInt( stringTokenizer.nextToken() );
    int minutes = Integer.parseInt( stringTokenizer.nextToken() );
    int seconds = Integer.parseInt( stringTokenizer.nextToken() );

    setHours(hours);
    setMinutes(minutes);
    setSeconds(seconds);

    return berlinClock;
  }


  private void setHours (int hours) {

    int quotient = hours / 5;
    int remainder = hours % 5;
    AtomicInteger cont = new AtomicInteger(0);

    berlinClock[1] = Arrays.stream(berlinClock[1].split(""))
        .map( item -> cont.addAndGet(1) <= quotient ? "R":"O" )
        .reduce("", String::concat);

    cont.set(0);

    berlinClock[2] = Arrays.stream(berlinClock[2].split(""))
        .map( item -> cont.addAndGet(1) <= remainder ? "R":"O" )
        .reduce("", String::concat);
  }


  private void setMinutes(int minutes) {

    int quotient = minutes / 5;
    int remainder = minutes % 5;
    AtomicInteger cont = new AtomicInteger(0);

    berlinClock[3] = Arrays.stream(berlinClock[3].split(""))
        .map( item -> cont.addAndGet(1) <= quotient ? createTopMinute(cont) :"O" )
        .reduce("", String::concat);

    cont.set(0);

    berlinClock[4] = Arrays.stream(berlinClock[4].split(""))
        .map( item -> cont.addAndGet(1) <= remainder ? "Y":"O" )
        .reduce("", String::concat);

  }


  private String createTopMinute(AtomicInteger cont) {

    return (cont.intValue() % 3 == 0) ? "R" : "Y" ;
  }


  private void setSeconds (int secs){

    int remainder = secs % 2;

    if (remainder==0){
      berlinClock[0] = "Y";

    }else{
      berlinClock[0] = "O";
    }
  }

}
