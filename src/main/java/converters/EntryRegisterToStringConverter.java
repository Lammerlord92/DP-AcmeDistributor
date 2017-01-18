package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.EntryRegister;
@Component
@Transactional
public class EntryRegisterToStringConverter implements Converter<EntryRegister, String>{
	@Override
	public String convert(EntryRegister source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
