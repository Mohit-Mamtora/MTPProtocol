package mohit.mtp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;


/*
 * this class create header and add to the payloader
 * 
 */
public class MTPPacket {
	
	
	public static final int HEADER_SIZE=18;
	private final byte[] HEADER=new byte[HEADER_SIZE];
	
	private int startbit;
	private int endbit;
	private int subsqnum;
	private int seqnum;
	private int offset;
	private int length;
	private int npd;
	private ByteArrayOutputStream outputStream;
	
	public MTPPacket() {
		outputStream=new ByteArrayOutputStream();
	}
	
	// Depacketalize
	
	public MTPPacket(byte[]packet,int length){
		
		
		outputStream=new ByteArrayOutputStream(length);
		
		Objects.requireNonNull(packet);
		
		this.startbit=(packet[0]>>>1&1);
		this.endbit=( packet[0] & 1 );
		this.npd=((packet[17] & 0xFF) + ((packet[16] & 0xFF) << 8) + ((packet[15] & 0xFF) << 16) + ((packet[14] & 0xFF) << 24));
		this.subsqnum=packet[1];
		this.length=((packet[13] & 0xFF) + ((packet[12] & 0xFF) << 8) + ((packet[11] & 0xFF) << 16) + ((packet[10] & 0xFF) << 24));
		this.seqnum=((packet[5] & 0xFF) + ((packet[4] & 0xFF) << 8) + ((packet[3] & 0xFF) << 16) + ((packet[2] & 0xFF) << 24));
		this.offset=(packet[9] & 0xFF) + ((packet[8] & 0xFF) << 8) + ((packet[7] & 0xFF) << 16) + ((packet[6] & 0xFF) << 24);
		
		outputStream.write(packet,18,packet.length-HEADER_SIZE);
		
	}
	
	
	//create MTPPacket and it's return length of packet 
	public void CreatePacket(ByteBuffer buf,byte[] data,int startbit,int endbit,
								int subsqnum,int seqnum,int offset,int length,int npd) throws IOException
	{
		Objects.requireNonNull(data);
		Objects.requireNonNull(buf);
		
		HEADER[0]=(byte)(((startbit) << 1) | endbit);
		HEADER[1]=(byte)subsqnum;
		HEADER[2]=(byte)( ( seqnum >> 24 ) );
		HEADER[3]=(byte)( ( seqnum >> 16 ) );
		HEADER[4]=(byte)( ( seqnum >> 8 ) );
		HEADER[5]=(byte)( ( seqnum & 0xFF ) );
		HEADER[6]=(byte)( ( offset >> 24 ) );
		HEADER[7]=(byte)( ( offset >> 16 ) );
		HEADER[8]=(byte)( ( offset >> 8 ) );
		HEADER[9]=(byte)( ( offset & 0xFF ) );
		HEADER[10]=(byte)( ( length >> 24 ) );
		HEADER[11]=(byte)( ( length >> 16 ) );
		HEADER[12]=(byte)( ( length >> 8 ) );
		HEADER[13]=(byte)( ( length & 0xFF ) );
		HEADER[14]=(byte)( ( npd >> 24 ) );
		HEADER[15]=(byte)( ( npd >> 16 ) );
		HEADER[16]=(byte)( ( npd >> 8 ) );
		HEADER[17]=(byte)( ( npd & 0xFF ) );
		
		buf.put(HEADER).put(data, offset, length);
	}
	
	//give MTPPacket and extract header and payloader
	public void Depacketalize(byte[] packet,int byte_length){
		
		Objects.requireNonNull(packet);
	
		this.startbit=(packet[0]>>>1&1);
		this.endbit=( packet[0] & 1 );
		this.npd=((packet[17] & 0xFF) + ((packet[16] & 0xFF) << 8) + ((packet[15] & 0xFF) << 16) + ((packet[14] & 0xFF) << 24));
		this.subsqnum=packet[1];
		this.length=((packet[13] & 0xFF) + ((packet[12] & 0xFF) << 8) + ((packet[11] & 0xFF) << 16) + ((packet[10] & 0xFF) << 24));
		this.seqnum=((packet[5] & 0xFF) + ((packet[4] & 0xFF) << 8) + ((packet[3] & 0xFF) << 16) + ((packet[2] & 0xFF) << 24));
		this.offset=(packet[9] & 0xFF) + ((packet[8] & 0xFF) << 8) + ((packet[7] & 0xFF) << 16) + ((packet[6] & 0xFF) << 24);
		
		outputStream.reset();
		outputStream.write(packet,18,length);
	}

	public int getStartbit() {
		return startbit;
	}

	public int getEndbit() {
		return endbit;
	}

	public int getSubsqnum() {
		return subsqnum;
	}

	public int getSeqnum() {
		return seqnum;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}
	
	public int getNpd() {
		return npd;
	}

	public byte[] getPayload() {
		return outputStream.toByteArray();
	}
	
	public int getPayloadSize() {
		return getLength();
	}
	
	public void printHeader(){
		
		System.out.print("STARTBIT :"+getStartbit());
		System.out.print("  ENDBIT :"+getEndbit());
		System.out.print("  SUBSEQNUM :"+getSubsqnum());
		System.out.print("  SEQNUM :"+getSeqnum());
		System.out.print("  OFFSET :"+getOffset());
		System.out.print("  LENGTH :"+getLength());
		System.out.print("  NPD :"+getNpd());
		System.out.print("  PAYLOAD SIZE :"+getPayloadSize()+"\n");
	
	}
}
