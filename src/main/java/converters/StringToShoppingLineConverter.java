package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ShoppingLineRepository;
import domain.ShoppingLine;

@Component
@Transactional
public class StringToShoppingLineConverter implements Converter<String, ShoppingLine>{
	@Autowired
	private ShoppingLineRepository shoppingLineRepository;
	
	@Override
	public ShoppingLine convert(String text) {
		ShoppingLine result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=shoppingLineRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
