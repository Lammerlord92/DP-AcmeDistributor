package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ProducerRepository;

import domain.Producer;

@Component
@Transactional
public class StringToProducerConverter implements Converter<String, Producer>{
	@Autowired
	private ProducerRepository producerRepository;
	
	@Override
	public Producer convert(String text) {
		Producer result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=producerRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
