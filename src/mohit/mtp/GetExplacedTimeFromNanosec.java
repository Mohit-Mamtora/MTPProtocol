package mohit.mtp;

public class GetExplacedTimeFromNanosec {

    public static String toString(long nanoSecs) {
    
      int minutes    = ( int ) ( nanoSecs / 60000000000.0 );
      int seconds    = ( int ) ( nanoSecs / 1000000000.0 )  - ( minutes * 60 );
      int millisecs  = ( int ) ( (( nanoSecs / 1000000000.0 ) - ( seconds + minutes * 60 )) * 1000);


      if ( minutes == 0 && seconds == 0 )
         return millisecs + "ms";
   
      else if ( minutes == 0 && millisecs == 0 )
         return seconds + "s";
      
      else if ( seconds == 0 && millisecs == 0 )
         return minutes + "min";
      
      else if ( minutes == 0 )
         return seconds + "s " + millisecs + "ms";
      
      else if ( seconds == 0 )
         return minutes + "min " + millisecs + "ms";
      
      else if ( millisecs == 0 )
         return minutes + "min " + seconds + "s";

      return minutes + "min " + seconds + "s " + millisecs + "ms";
      
   }
   
}
