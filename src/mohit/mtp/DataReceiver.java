package mohit.mtp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DataReceiver implements OnMTPReceiveListioner {

	public static void main(String[] args) throws IOException, InterruptedException {
		MTPReceiver r=new MTPReceiver(new DataReceiver(),true);
		r.setup();
		r.StartReceiving();
		
	}

	@Override
	public void onClose() {
		
	}

	@Override
	public void OnReceing(byte[] data) {
		
	}

	@Override
	public void OnCoDataReceived(byte[] data) throws IOException {
		
		ByteArrayInputStream in=new ByteArrayInputStream(data);
		BufferedImage image=ImageIO.read(in);
		
    if (image==null) {
			System.err.println("null");
			return;
		}
    
		ImageIO.write(image,"PNG", new File("path"));
    
	}


}
