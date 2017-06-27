package mohit.mtp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Objects;

public class MTPReceiver {
	
	
	private DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;
	private OnMTPReceiveListioner listioner;
	private MTPPacket mtpPacket;
	private int seqnum=0;
	private ByteArrayOutputStream arrayOutputStream;
	private boolean printHeader=false;
	
	private int packet_length=MTPPacket.HEADER_SIZE+MTPSender.payload_size;
	private byte[] packet=new byte[packet_length];
	
	public MTPReceiver(OnMTPReceiveListioner listioner,boolean printHeader) {
		Objects.requireNonNull(listioner);
		this.listioner=listioner;
		this.printHeader=printHeader;
	}
	public void setup() throws SocketException{
		
		datagramSocket=new DatagramSocket(MTPSender.SENDERPORT);
		datagramPacket=new DatagramPacket(packet,packet_length);
		mtpPacket=new MTPPacket();
		arrayOutputStream=new ByteArrayOutputStream();
	}
	
	public void StartReceiving() throws IOException{
		
		int subseqnum=1;
		while(true){
			
			datagramSocket.receive(datagramPacket);
			mtpPacket.Depacketalize(datagramPacket.getData(),datagramPacket.getLength());
			
			if(printHeader){mtpPacket.printHeader();}
			
			listioner.OnReceing(mtpPacket.getPayload());
			
			if ( mtpPacket.getStartbit() == 1 && mtpPacket.getEndbit() == 0 ){
				
				seqnum=mtpPacket.getSeqnum();
				subseqnum=1;
				arrayOutputStream.reset();
				arrayOutputStream.write(mtpPacket.getPayload());
			
			}
			else if( mtpPacket.getStartbit() == 1 && mtpPacket.getEndbit() == 1 ){
				
				seqnum=mtpPacket.getSeqnum();
				subseqnum=1;
				arrayOutputStream.reset();
			
				arrayOutputStream.write(mtpPacket.getPayload());
				
				listioner.OnCoDataReceived(arrayOutputStream.toByteArray());
				
				arrayOutputStream.reset();
			
			}
			else if ( mtpPacket.getStartbit() == 0 && mtpPacket.getEndbit() == 0 ){
				
				if (mtpPacket.getSeqnum() == seqnum){
					subseqnum++;
					arrayOutputStream.write(mtpPacket.getPayload());
				
				}
				else{
					continue;
				}
			
			}
			else if ( mtpPacket.getStartbit() == 0 && mtpPacket.getEndbit() == 1 ){
				
				subseqnum++;
				if ( mtpPacket.getSeqnum() == seqnum && subseqnum==mtpPacket.getNpd()){
					
					arrayOutputStream.write(mtpPacket.getPayload());
					
					listioner.OnCoDataReceived(arrayOutputStream.toByteArray());
					
					arrayOutputStream.reset();
				
				}
				else{
					continue;
				}
			}
		
			//reset
			datagramPacket.setLength(packet_length);
		}
		
	}
	
	public void close(){
		datagramSocket.close();
		listioner.onClose();
	}
	
}
