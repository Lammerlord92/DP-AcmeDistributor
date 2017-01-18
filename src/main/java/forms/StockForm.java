package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import domain.Warehouse;

@Embeddable
@Access(AccessType.PROPERTY)
public class StockForm {
	
	// Attributes ----------------------------------------------------------
	
	private Integer itemId;
	private Integer stockId;
	private Integer quantity;
	private Warehouse warehouse;
	
	// Getters&Setters ----------------------------------------------------

	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	public Integer getStockId() {
		return stockId;
	}
	public void setStockId(Integer stockId) {
		this.stockId = stockId;
	}
	
	@Min(1)
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Valid
	public Warehouse getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
}