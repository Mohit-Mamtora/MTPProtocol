package mohit.mtp;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import com.objectplanet.image.PngEncoder;




public class DataSender implements OnMTPSendListioner {
	
	 public static void main(String...args) throws IOException, InterruptedException, AWTException{
		
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		
		MTPSender mtpSender=new MTPSender(InetAddress.getByName("169.254.71.123"),new DataSender());
		mtpSender.setup();	
		
		Robot t=new Robot();
		Rectangle rectangle=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		
		
		int i=0;
		long startTime=System.nanoTime();
    
    // This pngEncoder encode png image very fast
		PngEncoder pngEncoder=new PngEncoder();
			
		while(i<1000000){
			
			
//			ImageIO.setUseCache(false);
			
//			ImageIO.write(image, "JPEG",out);
		
			pngEncoder.encode(t.createScreenCapture(rectangle), out);
						
			byte[] data=out.toByteArray();
			
			System.out.println(GetExplacedTimeFromNanosec.toString(System.nanoTime()-startTime)+" size :"+(data.length/1024));
			
      // send byte throught mtpSender to mtpReceiver
			mtpSender.send(data);
      
			out.reset();
			i++;
		}
		
	 }

	@Override
	public void OnDataSend() {
  //		System.out.println("Data Send");
	}

}

