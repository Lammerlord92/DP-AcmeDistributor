package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Invoice;
@Component
@Transactional
public class InvoiceToStringConverter implements Converter<Invoice, String>{
	@Override
	public String convert(Invoice source) {
		String result;

		if (source == null)
			result = null;
		else
			result = String.valueOf(source.getId());

		return result;
	}
}
