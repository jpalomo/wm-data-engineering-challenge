package com.palomo.wm.data.engineering.challenge.api;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class represents a lab result which in turn represents a single row of
 * the input.
 * 
 */
public class LabResult {

	private String batchId;
	private String vendorId;
	private String productId;
	private String labId;
	private String state;
	private String testedAt;
	private String expiresAt;
	private float thc;
	private float thca;
	private float cbd;
	private float cbda;

	// JSON alias was used to handle the input with variances in their field names
	@JsonAlias({ "batch_id", "batchId" })
	public void setBatchId(String batchId) {
		this.batchId = batchId.trim().toUpperCase();
	}

	@JsonAlias({ "vendor_id", "vendorId" })
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId.trim().toUpperCase();
	}

	@JsonAlias({ "product_id", "productId" })
	public void setProductId(String productId) {
		this.productId = productId.trim().toUpperCase();
	}

	@JsonAlias({ "lab_id", "labId" })
	public void setLabId(String labId) {
		this.labId = labId.trim().toUpperCase();
	}

	/**
	 * Sets the state but first converts it to its abbreviated form if not already
	 * abbreviated.
	 * 
	 * @param state
	 *            the state value to convert
	 */
	public void setState(String state) {
		this.state = normalizeState(state);
	}

	/**
	 * This method sets the state but
	 * 
	 * @param state
	 */
	private String normalizeState(String state) {
		String normalizedState = state;
		if (Objects.nonNull(state)) {
			normalizedState = state.trim().replace(".", "").toUpperCase();
			normalizedState = getStateAbb(normalizedState);
			if (normalizedState.length() > 2) {
				String[] tokened = normalizedState.split(" ");
				if (tokened.length > 1) {
					normalizedState = tokened[0].substring(0, 1) + tokened[1].substring(0, 1);
				} else {
					normalizedState = normalizedState.substring(0, 2);
				}
			}
		}
		return normalizedState;
	}

	@JsonAlias({ "tested_at", "testedAt" })
	public void setTestedAt(String testedAt) {
		this.testedAt = testedAt;
	}

	@JsonAlias({ "expires_at", "expiresAt" })
	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}

	public void setThc(String thc) {
		this.thc = new Float(thc);
	}

	public void setThca(String thca) {
		this.thca = new Float(thca);
	}

	public void setCbd(String cbd) {
		this.cbd = new Float(cbd);
	}

	public void setCbda(String cbda) {
		this.cbda = new Float(cbda);
	}

	/**
	 * Simple method used to sum the values of the cannabinoids
	 * @return
	 */
	private float getPotency() {
		return thc + thca + cbd + cbda;
	}

	public String toCsvString() {
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", batchId, vendorId, productId, labId, state,
				testedAt, expiresAt, thc, thca, cbd, cbda, getPotency());
	}

	@JsonIgnore
	private String getStateAbb(String state) {
		if (state.length() > 2) {
			if (state.toLowerCase().startsWith("ariz")) {
				return "AZ";
			}
			if (state.toLowerCase().startsWith("kans")) {
				return "KS";
			}
			if (state.toLowerCase().startsWith("misso")) {
				return "MO";
			}
			if (state.toLowerCase().startsWith("virg")) {
				return "VA";
			}
			if (state.toLowerCase().startsWith("fl")) {
				return "FL";
			}
			if (state.toLowerCase().startsWith("nev")) {
				return "NV";
			}
			if (state.toLowerCase().startsWith("ten")) {
				return "TN";
			}
			if (state.toLowerCase().startsWith("mai")) {
				return "ME";
			}
			if (state.toLowerCase().startsWith("ala")) {
				return "AK";
			}
			if (state.toLowerCase().startsWith("penn")) {
				return "PA";
			}
			if (state.toLowerCase().startsWith("lous")) {
				return "LA";
			}
			if (state.toLowerCase().startsWith("tex")) {
				return "TX";
			}
			if (state.toLowerCase().startsWith("geo")) {
				return "GA";
			}
			if (state.toLowerCase().startsWith("miss")) {
				return "MS";
			}
			if (state.toLowerCase().startsWith("conn")) {
				return "CN";
			}
			if (state.toLowerCase().startsWith("ken")) {
				return "KT";
			}
			if (state.toLowerCase().startsWith("verm")) {
				return "VT";
			}
			if (state.toLowerCase().startsWith("haw")) {
				return "HI";
			}
			if (state.toLowerCase().startsWith("min")) {
				return "MN";
			}
			if (state.toLowerCase().startsWith("mary")) {
				return "MD";
			}
			if (state.toLowerCase().startsWith("io")) {
				return "IA";
			}
			if (state.toLowerCase().startsWith("mont")) {
				return "MT";
			}
		}
		return state;
	}
}
