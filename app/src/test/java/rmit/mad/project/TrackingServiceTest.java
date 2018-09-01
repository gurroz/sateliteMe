package rmit.mad.project;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import rmit.mad.project.service.TrackingService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

// Support code by Caspar for MAD assignment s2 2018
// Simple example to test TrackingService (for demonstration only)
// Usage: add class in appropriate package (also see TrackingService.java)
//       TrackingServiceTest.test(this); // pass a valid Context (NOTE: Activity extends Context)

//@RunWith(MockitoJUnitRunner.class)
public class TrackingServiceTest
{
//   private static final String LOG_TAG = TrackingService.class.getName();

//    @Mock
    Context mMockContext;

   // call this method to run simple hard coded test (note you will need to handle the parsing Exception)
//   @Test
   public void test()
   {
      TrackingService trackingService = TrackingService.getSingletonInstance(mMockContext);
//      Log.i(LOG_TAG, "Parsed File Contents:");
      trackingService.logAll();

      try
      {
         // 5 mins either side of 05/07/2018 1:05:00 PM
         // PRE: make sure tracking_data.txt contains valid matches
         // PRE: make sure device locale matches provided DateFormat (you can change either device settings or String param)
         DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
         String searchDate = "05/07/2018 1:05:00 PM";
         int searchWindow = 5; // +/- 5 mins
         Date date = dateFormat.parse(searchDate);
         List<TrackingService.TrackingInfo> matched = trackingService
                 .getTrackingInfoForTimeRange(date, searchWindow, 0);
//         Log.i(LOG_TAG, String.format("Matched Query: %s, +/-%d mins", searchDate, searchWindow));
         trackingService.log(matched);

          assertThat(matched.size(), is(20));
      }
      catch (ParseException e)
      {
         e.printStackTrace();
      }
   }
}
