
public class SPPpackage {
	private int seqnr;
	private short checksum;
	private byte flags;
	private byte[] data;
	private int acknr;
	
	public void generateChecksum(){
		
	}
	public void setFlags(byte flags){
		this.flags = flags;	
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
}
