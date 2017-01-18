package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.WarehouseRepository;

import domain.Warehouse;

@Component
@Transactional
public class StringToWarehouseConverter implements Converter<String, Warehouse>{
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Override
	public Warehouse convert(String text) {
		Warehouse result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=warehouseRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
