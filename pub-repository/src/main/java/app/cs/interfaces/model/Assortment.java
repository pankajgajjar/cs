package app.cs.interfaces.model;

import java.io.Serializable;
import java.util.List;

import com.cs.data.api.core.GenericDomain;

public class Assortment implements GenericDomain, Serializable {

	private List<Product> products;
	
	private String ID;

	public List<Product> getProducts() {
		return products;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String getObjectKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
