package errors;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	private String batchId;
	private String vendorId;
	private String productId;
	private String labId;
	private String state;
	private String testedAt;
	private String expiresAt;
	private String thc;
	private String thca;
	private String cbd;
	private String cbda;
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getLabId() {
		return labId;
	}
	public void setLabId(String labId) {
		this.labId = labId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTestedAt() {
		return testedAt;
	}
	public void setTestedAt(String testedAt) {
		this.testedAt = testedAt;
	}
	public String getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}
	public String getThc() {
		return thc;
	}
	public void setThc(String thc) {
		this.thc = thc;
	}
	public String getThca() {
		return thca;
	}
	public void setThca(String thca) {
		this.thca = thca;
	}
	public String getCbd() {
		return cbd;
	}
	public void setCbd(String cbd) {
		this.cbd = cbd;
	}
	public String getCbda() {
		return cbda;
	}
	public void setCbda(String cbda) {
		this.cbda = cbda;
	}
}
