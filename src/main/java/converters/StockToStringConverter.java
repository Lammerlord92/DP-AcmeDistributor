package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Stock;
@Component
@Transactional
public class StockToStringConverter implements Converter<Stock, String>{
	@Override
	public String convert(Stock source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
