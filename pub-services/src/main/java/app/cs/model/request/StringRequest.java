package app.cs.model.request;

public class StringRequest implements RequestModel {
	private String stringRequest;

	public String getStringRequest() {
		return stringRequest;
	}

	public void setStringRequest(String stringRequest) {
		this.stringRequest = stringRequest;
	}

	public StringRequest(String stringRequest) {
		super();
		this.stringRequest = stringRequest;
	}

}