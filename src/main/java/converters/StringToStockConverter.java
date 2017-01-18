package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.StockRepository;

import domain.Stock;

@Component
@Transactional
public class StringToStockConverter implements Converter<String, Stock>{
	@Autowired
	private StockRepository stockRepository;
	
	@Override
	public Stock convert(String text) {
		Stock result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=stockRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
