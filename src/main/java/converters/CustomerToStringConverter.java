package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Customer;

@Component
@Transactional
public class CustomerToStringConverter implements Converter<Customer, String>{
	@Override
	public String convert(Customer source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
