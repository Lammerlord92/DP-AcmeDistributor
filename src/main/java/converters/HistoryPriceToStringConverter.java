package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.HistoryPrice;

@Component
@Transactional
public class HistoryPriceToStringConverter implements Converter<HistoryPrice, String>{
	@Override
	public String convert(HistoryPrice source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
