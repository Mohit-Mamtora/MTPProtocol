
  # MTPProtocol
  
  Real-Time Data Transfer ( influence by RTP Protcol) .
    
  This Protocol may help you when you wnat to send somedata as real-time through network. Most of time this can be used full in multi-meadia transportation. MTP used udp protocol for sending data. The port number used by MTP is 3459. MTP Protocol easily to used you don't have write more code.
  
  ## HOW IT;S WORK
   
   * MTP is divide data in 60kb packet after that add MTP Header to payload and send this packet to MTPReceiver
   * MTPReceiver Receive every Packet and That also Decide data is completely receive or not. If sometime a packet will lose so MTPReceiver terminate that sequence.
   * MTPPacket class is packetalize and depacketalize the payloader with MTP HEADER.  
   
   ## MTP HEADER Structur

         0                   1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |s|e|                      subseqnum                            |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                       sequence number                         |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                           offset                              |
         +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                           length                              |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        |                            npd                                |
        +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+
        
  * subseqnum = sub sequence number of main sequence.
  * npd = number of packet is divided for a sequence.
  * s = startbit
  * e = endbit
  
  You can print each packet header when it,s receive . Give true value when intialize MTPReceiver instance. 
  
  ```java 
  
  MTPReceiver r=new MTPReceiver(new DataReceiver(),true);
		
    // setup UDP Sockets 
    	r.setup();
    
    // start receving UDP Pakcets
	r.StartReceiving();
    
  ```
  ## How To use
  
  * MTPReceiver Initlizing
  
  ```java
  
  public class DataReceiver implements OnMTPReceiveListioner  {

    public static void main(String[] args) throws IOException, InterruptedException {
      MTPReceiver r=new MTPReceiver(new DataReceiver(),true);
      r.setup();
      
      new Thread(new Runnable(){
          
          @Override
          public void Run(){
          
            r.StartReceiving();    
          
          }
          
      }).start();
      
    }

    //closed 
    @Override
    public void onClose() {
      
    }

    @Override
    public void OnReceing(byte[] data) {
    
      // do some with packet of data
    
    }

    @Override
    public void OnCoDataReceived(byte[] data) throws IOException {
      
      // do some with your data
      
    }
}

  
  ```
  * MTPSender initlizing
  
  ```java
  
  public class DataSender implements OnMTPSendListioner {
	
	 public static void main(String...args) throws IOException, InterruptedException, AWTException{
		
		MTPSender mtpSender=new MTPSender(InetAddress.getByName("IP"),new DataSender());
		
		// Initlize UDP connection
		mtpSender.setup();	
		byte[] data=new byte[0x500f];
		
		// send data through MTP Trasport
		mtpSender.send(data);
			
	 }

	@Override
	public void OnDataSend() {
		//System.out.println("Data Send");
	}
}
  
  ```
## Try demo

	* DataReceiver.java
	* DataSender.java
