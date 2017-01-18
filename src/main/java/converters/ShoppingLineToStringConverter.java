package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ShoppingLine;

@Component
@Transactional
public class ShoppingLineToStringConverter implements Converter<ShoppingLine, String>{
	@Override
	public String convert(ShoppingLine source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
