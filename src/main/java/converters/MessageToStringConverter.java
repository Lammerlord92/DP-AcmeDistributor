package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Message;

@Component
@Transactional
public class MessageToStringConverter implements Converter<Message, String>{
	@Override
	public String convert(Message source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
