package br.com.mallah.investimentos.sheet.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;

import br.com.mallah.investimentos.utils.ReflectionUtils;

public interface Convertable <T> {
	
	static Map<Class<?>, Convertable<?>> converters = new HashMap<>();
	
	T convert(Cell cell);
	
	public static <T> Optional<Convertable> getConverter(Class returnType) {
		
		Convertable<T> convertable = (Convertable<T>) converters.get(returnType);
		
		if (convertable != null)
			return Optional.of(convertable);
		
		Optional<Class<? extends Convertable>> convertableClassOptional = ReflectionUtils.getSubclasses(Convertable.class).stream()
			.filter(c -> {
				try {
					return c.getMethod("convert", Cell.class).getReturnType().equals(returnType);
				} catch (NoSuchMethodException | SecurityException e) {
					return false;
				}
			})
			.findFirst();
		
		Optional<Convertable> converterOptional = (Optional<Convertable>) convertableClassOptional
				.map(c -> ReflectionUtils.getNewInstance(c).orElse(null))
				.filter(c -> c != null);
		
		if (converterOptional.isPresent()) {
			converters.put(returnType, converterOptional.get());
		}
		
		return converterOptional;
	}

}
