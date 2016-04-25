
public class SPPpackage {
	private int seqnr;
	private short checksum;
	private short flags;
	private byte[] data;
	private int acknr;
	
	public void generateChecksum(){
		
	}
	public void setAck(boolean value){
		
	}
	public boolean isAck(){
		
	}
	public void setSyn(boolean value){
		
	}
	public boolean isSyn(){
		
	}
	public void setRst(boolean value){
		
	}
	public boolean isRst(){
		
	}
	public void setFin(boolean value){
		
	}
	public boolean isFin(){
		
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
