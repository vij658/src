package zipcode;

public class ZipCode {
	
	int [] zipCodeList;
	String finalOutput;
	
	public ZipCode(int[] zipCodeList) {
		this.zipCodeList= zipCodeList;
	}
	
	public int[] getZipCodeList() {
		return zipCodeList;
	}
	public void setZipCodeList(int[] zipCodeList) {
		this.zipCodeList = zipCodeList;
	}
	public String getFinalOutput() {
		return finalOutput;
	}
	public void setFinalOutput(String finalOutput) {
		this.finalOutput = finalOutput;
	}
   
}
