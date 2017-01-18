package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.DistributorRepository;

import domain.Distributor;

@Component
@Transactional
public class StringToDistributorConverter implements Converter<String, Distributor>{
	@Autowired
	private DistributorRepository distributorRepository;
	
	@Override
	public Distributor convert(String text) {
		Distributor result;
		int id;
		try{
			if(StringUtils.isEmpty(text))
				result=null;
			else{
				id=Integer.valueOf(text);
				result=distributorRepository.findOne(id);
			}
		}catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		
		return result;
	}
}
