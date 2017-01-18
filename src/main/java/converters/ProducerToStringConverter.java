package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Producer;
@Component
@Transactional
public class ProducerToStringConverter implements Converter<Producer, String>{
	@Override
	public String convert(Producer source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
