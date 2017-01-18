package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import repositories.HistoryPriceRepository;
import domain.HistoryPrice;

@Component
@Transactional
public class StringToHistoryPriceConverter implements Converter<String, HistoryPrice>{
	@Autowired
	private HistoryPriceRepository historyPriceRepository;
	
	@Override
	public HistoryPrice convert(String text) {
		HistoryPrice result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=historyPriceRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
