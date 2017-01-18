package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Warehouse;
@Component
@Transactional
public class WarehouseToStringConverter implements Converter<Warehouse, String>{
	@Override
	public String convert(Warehouse source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
