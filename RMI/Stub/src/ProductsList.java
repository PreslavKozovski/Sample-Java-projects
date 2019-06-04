import java.io.Serializable;

public class ProductsList implements Serializable {
	private static final long serialVersionUID = 1L;
	public String productsListName;
	public String productsArray[];
	public int productsCount;
	
	ProductsList(String name, String[] products) {
		this.productsListName = name;
		this.productsArray = products;
		this.productsCount = products.length;		
	}
}
