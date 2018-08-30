public class Data {

	private String batch_id;
	private String vendor_id;
	private String product_id;
	private String lab_id;
	private String state;
	private String tested_at;
	private String expires_at;
	private String thc;
	private String thca;
	private String cbd;
	private String cbda;

    public void setType(String type) {}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getLab_id() {
		return lab_id;
	}

	public void setLab_id(String lab_id) {
		this.lab_id = lab_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTested_at() {
		return tested_at;
	}

	public void setTested_at(String tested_at) {
		this.tested_at = tested_at;
	}

	public String getThc() {
		return thc;
	}

	public String getExpires_at() {
		return expires_at;
	}

	public void setExpires_at(String expires_at) {
		this.expires_at = expires_at;
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

	public String toCsvString() {
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", getBatch_id().trim(),
				getVendor_id().trim(), getProduct_id().trim(), getLab_id().trim(),
				getState().trim(), getTested_at().trim(), getExpires_at().trim(),
				getThc().trim(), getThca().trim(), getCbd().trim(),
				getCbda().trim());
	}
}
