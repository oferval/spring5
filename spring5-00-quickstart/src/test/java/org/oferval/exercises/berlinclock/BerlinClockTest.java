package org.oferval.exercises.berlinclock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BerlinClockTest {

  BerlinClock berlinClock = new BerlinClock();

  // Yellow lamp should blink on/off every two seconds
  @Test
  public void testYellowLampShouldBlinkOnOffEveryTwoSeconds() {
    Assertions.assertEquals("Y", berlinClock.getSeconds(0));
    Assertions.assertEquals("O", berlinClock.getSeconds(1));
    Assertions.assertEquals("O", berlinClock.getSeconds(59));
  }

  // Top hours should have 4 lamps
  @Test
  public void testTopHoursShouldHave4Lamps() {
    Assertions.assertEquals(4, berlinClock.getTopHours(7).length());
  }

  // Top hours should light a red lamp for every 5 hours
  @Test
  public void testTopHoursShouldLightRedLampForEvery5Hours() {
    Assertions.assertEquals("OOOO", berlinClock.getTopHours(0));
    Assertions.assertEquals("RROO", berlinClock.getTopHours(13));
    Assertions.assertEquals("RRRR", berlinClock.getTopHours(23));
    Assertions.assertEquals("RRRR", berlinClock.getTopHours(24));
  }

  // Bottom hours should have 4 lamps
  @Test
  public void testBottomHoursShouldHave4Lamps() {
    Assertions.assertEquals(4, berlinClock.getBottomHours(5).length());
  }

  // Bottom hours should light a red lamp for every hour left from top hours
  @Test
  public void testBottomHoursShouldLightRedLampForEveryHourLeftFromTopHours() {
    Assertions.assertEquals("OOOO", berlinClock.getBottomHours(0));
    Assertions.assertEquals("RRRO", berlinClock.getBottomHours(13));
    Assertions.assertEquals("RRRO", berlinClock.getBottomHours(23));
    Assertions.assertEquals("RRRR", berlinClock.getBottomHours(24));
  }

  // Top minutes should have 11 lamps
  @Test
  public void testTopMinutesShouldHave11Lamps() {
    Assertions.assertEquals(11, berlinClock.getTopMinutes(34).length());
  }

  // Top minutes should have 3rd, 6th and 9th lamps in red to indicate first quarter, half and last quarter
  @Test
  public void testTopMinutesShouldHave3rd6thAnd9thLampsInRedToIndicateFirstQuarterHalfAndLastQuarter() {
    String minutes32 = berlinClock.getTopMinutes(32);
    Assertions.assertEquals("R", minutes32.substring(2, 3));
    Assertions.assertEquals("R", minutes32.substring(5, 6));
    Assertions.assertEquals("O", minutes32.substring(8, 9));
  }

  // Top minutes should light a yellow lamp for every 5 minutes unless it's first quarter, half or last quarter
  @Test
  public void testTopMinutesShouldLightYellowLampForEvery5MinutesUnlessItIsFirstQuarterHalfOrLastQuarter() {
    Assertions.assertEquals("OOOOOOOOOOO", berlinClock.getTopMinutes(0));
    Assertions.assertEquals("YYROOOOOOOO", berlinClock.getTopMinutes(17));
    Assertions.assertEquals("YYRYYRYYRYY", berlinClock.getTopMinutes(59));
  }

  // Bottom minutes should have 4 lamps
  @Test
  public void testBottomMinutesShouldHave4Lamps() {
    Assertions.assertEquals(4, berlinClock.getBottomMinutes(0).length());
  }

  // Bottom minutes should light a yellow lamp for every minute left from top minutes
  @Test
  public void testBottomMinutesShouldLightYellowLampForEveryMinuteLeftFromTopMinutes() {
    Assertions.assertEquals("OOOO", berlinClock.getBottomMinutes(0));
    Assertions.assertEquals("YYOO", berlinClock.getBottomMinutes(17));
    Assertions.assertEquals("YYYY", berlinClock.getBottomMinutes(59));
  }

  // Berlin Clock should result in array with 5 elements
  @Test
  public void testBerlinClockShouldResultInArrayWith5Elements()  {
    Assertions.assertEquals(5, berlinClock.convertToBerlinTime("13:17:01").length);
  }

  // Berlin Clock it should "result in correct seconds, hours and minutes" in {
  @Test
  public void testBerlinClockShouldResultInCorrectSecondsHoursAndMinutes() {
    String[] berlinTime = berlinClock.convertToBerlinTime("16:37:16");
    String[] expected = new String[] {"Y", "RRRO", "ROOO", "YYRYYRYOOOO", "YYOO"};
    Assertions.assertEquals(expected.length, berlinTime.length);
    for (int index = 0; index < expected.length; index++) {
      Assertions.assertEquals(expected[index], berlinTime[index]);
    }
  }

}