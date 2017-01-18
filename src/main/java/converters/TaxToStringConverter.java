package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Tax;

@Component
@Transactional
public class TaxToStringConverter implements Converter<Tax, String>{
	@Override
	public String convert(Tax source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
