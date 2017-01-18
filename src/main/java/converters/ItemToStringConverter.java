package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Item;

@Component
@Transactional
public class ItemToStringConverter implements Converter<Item, String>{
	@Override
	public String convert(Item source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
