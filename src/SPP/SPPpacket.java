package SPP;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class SPPpacket {
	private int seqnr;
	private short checksum;
	private byte flags;
	private byte[] data;
	private int acknr;
	public byte[] getByteStream(){
		ByteBuffer b = ByteBuffer.allocate(11 /*length of our header*/ + data.length);
		b.putInt(seqnr);
		b.putInt(0);
		short checksum = calculateChecksum();
		b.putShort(checksum);
		b.put(flags);
		b.put(data);
		return b.array();
	}
	public SPPpacket(byte[] byteData)
	{
		ByteBuffer instream = ByteBuffer.wrap(byteData);
		seqnr = instream.getInt();
		acknr = instream.getInt();
		checksum = instream.getShort();
		flags = instream.get();
		data = new byte[instream.remaining()]; 
		System.out.println("Moving " + (instream.remaining()) + " bytes to buffer out of: " + byteData.length);
		instream.get(data, 0, instream.remaining());
	}
	public SPPpacket()
	{
		//Default constructor
	}
	public void setFlags(byte flags){
		this.flags = flags;	
	}
	public short getChecksum(){
		return calculateChecksum();
	}
	public void setAck(){
		flags = (byte)(flags | (1 << 1));
	}
	public boolean isAck(){
		return (flags & (1 << 1))!=0 ;
	}
	public void setSyn(){
			flags = (byte)(flags | 1);
	}
	public boolean isSyn(){
		return (flags & 1)!=0;
	}
	public void setRst(){
		flags = (byte)(flags | (1 << 2));
	}
	public boolean isRst(){
		return (flags & (1 << 2))!=0;
	}
	public void setFin(){
		flags = (byte)(flags | (1 << 3));
	}
	public boolean isFin(){
		return (flags & (1 << 3))!=0;
	}
	public int getAcknr() {
		return acknr;
	}
	public void setAcknr(int acknr) {
		this.acknr = acknr;
	}
	public int getSeqnr() {
		return seqnr;
	}
	public void setSeqnr(int seqnr) {
		this.seqnr = seqnr;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public short calculateChecksum()
	{
		short checksum = 0;
		checksum+=seqnr;
		checksum+=flags;
		for (byte c : data) {
			checksum += 0 | c; // Converts the byte c into an int (0 is two bytes and the OR operator flips only the first byte)
		}
		System.out.println("Calculated checksum: " + checksum);
		return checksum; 
	}
}
