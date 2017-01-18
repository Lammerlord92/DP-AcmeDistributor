package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.EntryRegisterRepository;

import domain.EntryRegister;

@Component
@Transactional
public class StringToEntryRegisterConverter implements Converter<String, EntryRegister>{
	@Autowired
	private EntryRegisterRepository entryRegisterRepository;
	
	@Override
	public EntryRegister convert(String text) {
		EntryRegister result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=entryRegisterRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
