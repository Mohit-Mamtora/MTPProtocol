package mohit.mtp;

import java.io.IOException;

public interface OnMTPReceiveListioner {
  
  //occure when UDP Receiver going shutdown,.
	public void onClose();
  
  //occure when packet is receive, but set in mind "This packets are not complete data just part of your data"
  //and give those data as byte
  public void OnReceing(byte[] data);
  
  //occure after complete data is receive 
  public void OnCoDataReceived(byte[] data) throws IOException;
  
}

